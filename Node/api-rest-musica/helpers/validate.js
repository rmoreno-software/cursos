const validator = require("validator");

const validate = (params) => {

    let result = false;

    let name = !validator.isEmpty(params.name) &&
        validator.isLength(params.name, { min: 3, max: undefined }) &&
        validator.isAlpha(params.name, "es-ES");
    let nick = !validator.isEmpty(params.nick) &&
        validator.isLength(params.nick, { min: 2, max: 20 });
    let email = !validator.isEmpty(params.email) &&
        validator.isEmail(params.email);
    let password = !validator.isEmpty(params.password);

    if (params.surname) {
        let surname = !validator.isEmpty(params.surname) &&
            validator.isLength(params.surname, { min: 3, max: undefined }) &&
            validator.isAlpha(params.surname, "es-ES");

        if (!surname) {
            throw new Error("Validation failed");
        }
    }

    if (!name || !nick || !email || !password) {
        throw new Error("Validation failed");
    } else {
        result = true;
        console.log("Validation passed");
    }

    return result;
}

module.exports = validate;