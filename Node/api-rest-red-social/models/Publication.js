const mongoose = require("mongoose");
const mongoosePaginate = require("mongoose-paginate-v2"); // 👈 esto devuelve una función

const PublicationSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.ObjectId,
        ref: "User",
        required: true
    },
    text: {
        type: String,
        required: true
    },
    file: String,
    created_at: {
        type: Date,
        default: Date.now()
    }
});

PublicationSchema.plugin(mongoosePaginate);

module.exports = mongoose.model("Publication", PublicationSchema, "publications");