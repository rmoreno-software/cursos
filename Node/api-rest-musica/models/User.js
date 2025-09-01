const mongoose = require("mongoose");
const mongoosePaginate = require("mongoose-paginate-v2"); // 👈 esto devuelve una función

const UserSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    surname: String,
    nick: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true,
        select: false
    },
    role: {
        type: String,
        default: "role_user",
        select: false
    },
    image: {
        type: String,
        default: "default.png"
    },
    created_at: {
        type: Date,
        default: Date.now()
    }
});

UserSchema.plugin(mongoosePaginate);

module.exports = mongoose.model("User", UserSchema, "users");