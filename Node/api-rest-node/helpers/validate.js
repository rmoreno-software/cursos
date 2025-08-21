const validator = require("validator");

const validateArticle = (params) => {
    let validate_title = !validator.isEmpty(params.title) &&
                validator.isLength(params.title, {min: 5, max: undefined});
    let validate_content = !validator.isEmpty(params.content);

    if(!validate_title || !validate_content) {
        console.log("VALIDATE - ERROR")
        throw new Error("No se ha enviado la información");
    }
}

module.exports = {
    validateArticle
}