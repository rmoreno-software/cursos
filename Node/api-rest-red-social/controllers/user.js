const User = require("../models/User")

const register = (req, res) => {

    const params = req.body;
    
    return res.status(200).json({
        message: "Accion de registro de usuarios",
        params: params
    });

}

module.exports = {
    register
}