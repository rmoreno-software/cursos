const bcrypt = require("bcrypt");
const User = require("../models/User");
const jwt = require("../services/jwt");

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

    try {
        user = await User.findById(id)
            .select({ password: 0, role: 0 })
            .exec();
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error searching user"
        });
    }

    if (!user) {
        return res.status(404).send({
            status: "error",
            message: "User not found"
        });
    }

    return res.status(200).send({
        status: "success",
        user
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

    try {
        users = await User.paginate({}, { page, limit: itemsPerPage ,sort: { _id: 1 } });
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
        users
    });





}

module.exports = {
    register,
    login,
    pruebaUser,
    profile,
    list
}