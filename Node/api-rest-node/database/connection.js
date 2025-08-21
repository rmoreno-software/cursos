const mongoose = require("mongoose");

const connection = async() => {

    try {
        await mongoose.connect("mongodb://localhost:27017/mi_blog");

        console.log("Correctly connected to mi_blog database");
    } catch(error) {
        console.log(error);
        throw new Error("Error while trying to connect to database");
    }

}

module.exports = {
    connection
}