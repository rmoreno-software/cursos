const jwt = require("jwt-simple");
const moment = require("moment");

const secretKey = "CLAVE_SECRETA_DEL_CURSO_del_proyecto_DE_La_rED_SoCIAl_45874521";

const createToken = (user) => {
    const payload = {
        id: user._id,
        name: user.name,
        surname: user.surname,
        nick: user.nick,
        email: user.email,
        role: user.role,
        image: user.image,
        iat: moment().unix(),
        exp: moment().add(30, "days").unix() 
    };

    return jwt.encode(payload, secretKey);
}

module.exports = {
    secretKey,
    createToken
}
