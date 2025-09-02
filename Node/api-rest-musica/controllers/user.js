const validate = require("../helpers/validate");
const User = require("../models/User");
const bcrypt = require("bcrypt");
const jwt = require("../helpers/jwt");
const fs = require("fs");
const path = require("path");

const test = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "Test user controller"
    });
}

const register = async (req, res) => {
    let params = req.body;

    if (!params.name || !params.nick || !params.email || !params.password) {
        return res.status(500).send({
            status: "error",
            message: "There are params required"
        });
    }

    try {
        validate(params);
        const users = await User.find({
            $or: [
                { email: params.email.toLowerCase() },
                { nick: params.nick.toLowerCase() }
            ]
        }).exec();

        if (users.length > 0)
            return res.status(500).send({
                status: "error",
                message: "User already exists"
            });

        let pwd = await bcrypt.hash(params.password, 10);
        params.password = pwd;

        let userToSave = new User(params);
        let userStored = await userToSave.save();

        userStored = userStored.toObject();
        delete userStored.password;
        delete userStored.role;

        return res.status(200).send({
            status: "success",
            user: userStored
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error registering user"
        });
    }
}

const login = async (req, res) => {
    let params = req.body;

    if (!params || !params.email || !params.password) {
        return res.status(400).send({
            status: "error",
            message: "Missing data to be sent"
        });
    }

    try {
        const user = await User.findOne({ email: params.email })
            .select("+password +role")
            .exec();

        if (!user) {
            return res.status(404).send({
                status: "error",
                message: "User not found"
            });
        }

        const pwd = bcrypt.compareSync(params.password, user.password);
        if (!pwd) {
            return res.status(400).send({
                status: "error",
                message: "Incorrect password"
            });
        }

        let identityUser = user.toObject();
        delete identityUser.password;
        delete identityUser.role;

        const token = jwt.createToken(user);

        return res.status(200).send({
            status: "success",
            user: identityUser,
            token
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error during login"
        });
    }
}

const profile = async (req, res) => {
    const id = req.params.id;

    try {
        const user = await User.findById(id);
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
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error getting user profile"
        });
    }
}

const update = async (req, res) => {
    let userIdentity = req.user;
    let userBody = req.body;

    try {
        if (userBody.email) {
            let users = await User.find({
                $or: [
                    { email: userBody.email.toLowerCase() },
                ]
            });

            if (users.length > 0) {
                return res.status(500).send({
                    status: "error",
                    message: "Email already in use"
                });
            }
        }

        if (userBody.nick) {
            let users = await User.find({
                $or: [
                    { nick: userBody.nick.toLowerCase() },
                ]
            });

            if (users.length > 0) {
                return res.status(500).send({
                    status: "error",
                    message: "Nick already in use"
                });
            }
        }

        if (userBody.password) {
            let pwd = await bcrypt.hash(userBody.password, 10);
            userBody.password = pwd;
        } else {
            delete userBody.password;
        }

        let userUpdated = await User.findByIdAndUpdate({ _id: userIdentity.id }, userBody, { new: true });
        return res.status(200).send({
            status: "success",
            user: userUpdated
        });
    } catch (error) {
        console.log(error);
        return res.status(500).send({
            status: "error",
            message: "Error updating user"
        });
    }
}

const upload = async (req, res) => {

    try {
        if (!req.file) {
            return res.status(500).send({
                status: "error",
                message: "Request must include a file"
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
                message: "Extension is not valid"
            });
        }

        let updatedUser = await User.findByIdAndUpdate({ _id: req.user.id }, { image: req.file.filename }, { new: true });

        return res.status(200).send({
            status: "success",
            user: updatedUser
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error uploading avatar"
        });
    }
}

const avatar = async (req, res) => {
    try {
        const file = req.params.file;
        const filePath = `./uploads/avatars/${file}`;

        fs.stat(filePath, (error, exists) => {
            if (error || !exists) {
                return res.status(404).send({
                    status: "error",
                    message: "File not found"
                });
            }

            return res.sendFile(path.resolve(filePath));
        });

    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error getting avatar"
        });
    }
}

module.exports = {
    test,
    register,
    login,
    profile,
    update,
    upload,
    avatar
}