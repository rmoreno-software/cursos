
const jwt = require("jwt-simple");
const moment = require("moment");
const libjwt = require("../services/jwt");

const secretKey = libjwt.secretKey;

// Middleware d'autenticació
exports.auth = (req, res, next) => {
    // Comprobem si rebem la capçalera
    if (!req.headers.authorization) {
        return res.status(403).send({
            status: "error",
            message: "La petició no te el header d'autenticació"
        });
    }

    // Netejar el token
    let token = req.headers.authorization.replace(/['"]+/g, '');

    // Decodificar token
    try {
        let payload = jwt.decode(token, secretKey);

        // Comprovar expiració del token
        if (payload.exp <= moment().unix()) {
            return res.status(404).send({
                status: "error",
                message: "Token expirado",
            });
        }
        
        // Agregar dades de l'usuari a request
        req.user = payload;

    } catch (error) {
        return res.status(404).send({
            status: "error",
            message: "Token invàlid",
        });
    }    

    // Pasar a executar l'acció
    next();
}