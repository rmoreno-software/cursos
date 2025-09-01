const bcrypt = require("bcrypt");
const fs = require("fs");
const path = require("path");
const User = require("../models/User");
const jwt = require("../services/jwt");
const followService = require("../services/followService");
const validate = require("../helpers/validate");

const pruebaUser = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "test",
        user: req.user
    });
}

const register = async (req, res) => {

    const params = req.body;

    if (!params.name || !params.email || !params.password || !params.nick) {
        console.log(`USERREGISTER - Validació incorrecta`);
        return res.status(400).json({
            status: "error",
            message: "Faltan datos por enviar",
        });
    }

    

    let findedUsers = null;
    try {
        validate.validate(params);
        findedUsers = await User.find({
            $or: [
                { email: params.email.toLowerCase() },
                { nick: params.nick.toLowerCase() },
            ]
        }).exec();

    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error searching user"
        });
    }

    if (!findedUsers || findedUsers.length > 0) {
        return res.status(400).json({
            status: "error",
            message: "El usuario ya existe",
        });
    }

    let hash = await bcrypt.hash(params.password, 10);

    params.password = hash
    let user_to_save = new User(params);

    let saved_user = null;

    try {
        saved_user = await user_to_save.save();
    } catch (error) {
        return res.status(400).json({
            status: "error",
            message: "Error al guardar el nuevo usuario",
        });
    }

    if (!saved_user) {
        return res.status(500).json({
            status: "error",
            message: "Error al registrar el usuario",
        });
    }

    return res.status(200).json({
        status: "success",
        message: "Usuario registrado correctamente",
        user: saved_user
    });

}

const login = async (req, res) => {

    let params = req.body;

    if (!params.email || !params.password) {
        return res.status(400).json({
            status: "error",
            message: "Faltan parámetros requeridos"
        });
    }

    let user = null;

    try {
        user = await User.findOne({ email: params.email })
            .exec();
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error searching user"
        });
    }

    if (!user) {
        return res.status(404).json({
            status: "error",
            message: "User not found"
        });
    }

    const correctPwd = bcrypt.compareSync(params.password, user.password);

    if (!correctPwd) {
        return res.status(400).json({
            status: "error",
            message: "Contraseña incorrecta"
        });
    }

    // Devolver token
    const token = jwt.createToken(user);

    return res.status(200).send({
        status: "success",
        message: "Identificación correcta",
        user: {
            id: user._id,
            name: user.name,
            surname: user.surname,
            nick: user.nick,
            email: user.email,
            created_at: user.created_at,
            token
        }
    });

}

const profile = async (req, res) => {

    let id = req.params.id;
    let user = null;
    let followInfo = null;

    try {
        user = await User.findById(id)
            .select({ password: 0, role: 0 })
            .exec();

        if (!user) {
            return res.status(404).send({
                status: "error",
                message: "User not found"
            });
        }

        followInfo = await followService.followThisUser(req.user.id, id);
    } catch (error) {
        console.log(error);
        return res.status(500).send({
            status: "error",
            message: "Error searching user"
        });
    }

    return res.status(200).send({
        status: "success",
        user,
        following: followInfo.following,
        follower: followInfo.follower
    });
}

const list = async (req, res) => {

    let page = 1;

    if (req.params.page) {
        page = req.params.page;
        page = parseInt(page);
    }

    let itemsPerPage = 3;

    let users = null;

    let followInfo = null;

    try {
        users = await User.paginate(
            {},
            {
                page,
                limit: itemsPerPage,
                sort: { _id: 1 },
                select: "-password -email -role -__v"
            });

        followInfo = await followService.followUserIds(req.user.id);

    } catch (error) {
        console.error(`Users - List: ${error}`);
        return res.status(500).send({
            status: "error",
            message: "Error en la consulta"
        });
    }


    if (users.docs.length <= 0) {
        return res.status(404).send({
            status: "error",
            message: "Users not found"
        });
    }

    return res.status(200).send({
        status: "success",
        users,
        user_following: followInfo.following,
        user_followers: followInfo.followers
    });

}

const update = async (req, res) => {

    const userIdentity = req.user.id;
    const updates = req.body;

    let user = null;

    try {
        user = await User.findById(userIdentity);
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error buscando usuario"
        })
    }

    if (!user) {
        return res.status(404).send({
            status: "error",
            message: "User not found"
        });
    }

    const allowedFields = ["name", "surname", "nick", "email", "password"];

    for (let key of Object.keys(updates)) {
        if (allowedFields.includes(key)) {
            if (key === "password") {
                let hash = await bcrypt.hash(updates.password, 10);
                user.password = hash;
            } else {
                user[key] = updates[key]
            }
        }
    }

    try {
        await user.save();
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error saving user"
        });
    }

    return res.status(200).send({
        status: "success",
        user: user
    });
}

const upload = async (req, res) => {

    const userIdentity = req.user;

    if (!req.file) {
        return res.status(500).send({
            status: "success",
            message: "Falta proporcionar la imagen"
        });
    }

    let image = req.file.originalname;

    const imageSplit = image.split("\.");
    const extension = imageSplit[1];

    if (extension != "png" && extension != "jpg" && extension != "jpeg" && extension != "gif") {

        const filePath = req.file.path;
        const fileDeleted = fs.unlinkSync(filePath);

        return res.status(500).send({
            status: "error",
            message: "Extensión no válida"
        });

    }

    let userUpdated = null;

    try {
        userUpdated = await User.findOneAndUpdate({ _id: userIdentity.id }, { image: req.file.filename }, { new: true });
    } catch (error) {
        console.log("Error: " + error);
        return res.status(500).send({
            status: "error",
            message: "Error uploading image"
        });
    }

    if (!userUpdated) {
        return res.status(404).send({
            status: "error",
            message: "User not found"
        });
    }

    return res.status(200).send({
        status: "success",
        user: userUpdated,
    });
}

const avatar = async (req, res) => {

    const userIdentity = req.user;

    let user = null;

    try {
        user = await User.findById(userIdentity.id).exec();
    } catch (error) {
        console.log(error)
        return res.status(500).send({
            status: "Error",
            message: "Error searching user"
        });
    }

    if (!user) {
        return res.status(404).send({
            status: "error",
            message: "User not found"
        });
    }

    const filePath = "./uploads/avatars/" + user.image;
    console.log("filePath: " + filePath);

    fs.stat(filePath, (error, exists) => {
        if (!exists) {
            return res.status(404).send({
                status: "error",
                message: "No existe la imagen",
            });
        }

        return res.sendFile(path.resolve(filePath));
    });


}

module.exports = {
    register,
    login,
    pruebaUser,
    profile,
    list,
    update,
    upload,
    avatar
}