const validate = require("../helpers/validate");
const User = require("../models/User");
const bcrypt = require("bcrypt");
const jwt = require("../helpers/jwt");

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
        if(!pwd) {
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
        if(!user){
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

module.exports = {
    test,
    register,
    login,
    profile
}