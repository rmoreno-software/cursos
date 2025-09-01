const mongoose = require("mongoose");

const connection = async() => {
    try {
        await mongoose.connect("mongodb://localhost:27017/app_musica");

        console.log("Connectat correctament a la base de dades: app_musica");
    } catch (error) {
        console.error(error);
        throw new Error("No se ha podido establecer la connexión a la base de datos.")
    }
}

module.exports = connection;