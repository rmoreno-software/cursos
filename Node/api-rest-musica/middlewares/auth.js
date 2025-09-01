const jwt = require("jwt-simple");
const moment = require("moment");
const { secretKey } = require("../helpers/jwt");

exports.auth = (req, res, next) => {
    if (!req.headers.authorization) {
        return res.status(403).send({
            status: "error",
            message: "The request does not have the authentication header"
        });
    }

    let token = req.headers.authorization.replace(/['"]+/g, "");

    try {
        let payload = jwt.decode(token, secretKey);

        if (payload.exp <= moment().unix()) {
            return res.status(404).send({
                status: "error",
                message: "Token expired"
            });
        }

        req.user = payload;

    } catch (error) {
        console.error(error);
        return res.status(404).send({
            status: "error",
            message: "Error during authorization token"
        });
    }

    next();
}