const jwt = require("jwt-simple");
const moment = require("moment");

const secretKey = "SECRET_KEY_off_MY_project_off_API_MuSical_5478531596";

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
    }

    return jwt.encode(payload, secretKey);
}

module.exports = {
    secretKey,
    createToken
}
