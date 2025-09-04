const mongoose = require("mongoose");
const mongoosePaginate = require("mongoose-paginate-v2"); // 👈 esto devuelve una función

const AlbumSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    description: String,
    artist: {
        type: mongoose.Schema.ObjectId,
        ref: "Artist"
    },
    year: {
        type: Number,
        required: true
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

AlbumSchema.plugin(mongoosePaginate);

module.exports = mongoose.model("Album", AlbumSchema, "albums");