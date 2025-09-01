const mongoose = require("mongoose");

const connection = async() => {
    try {
        await mongoose.connect("mongodb://localhost:27017/app_musica");

        console.log("Connected successfully to database: app_musica");
    } catch (error) {
        console.error(error);
        throw new Error("The connection to the database could not be established.")
    }
}

module.exports = connection;