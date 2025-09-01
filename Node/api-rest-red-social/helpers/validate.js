const validator = require("validator");

const validate = (params) => {
    let name = validator.isLength(params.name, { min: 3, max: undefined }) &&
        validator.isAlpha(params.name, "es-ES");

    let surname = validator.isLength(params.surname, { min: 3, max: undefined }) &&
        validator.isAlpha(params.surname, "es-ES");

    let nick = validator.isLength(params.nick, { min: 3, max: undefined });

    let email = validator.isLength(params.email, { min: 3, max: undefined }) &&
        validator.isEmail(params.email);

    if (!name || !surname || !nick || !email) {
        throw new Error("No se ha superado la validación");
    }
}

module.exports = {
    validate
}