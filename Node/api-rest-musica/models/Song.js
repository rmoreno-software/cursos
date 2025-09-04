const mongoose = require("mongoose");
const mongoosePaginate = require("mongoose-paginate-v2"); // 👈 esto devuelve una función

const SongSchema = new mongoose.Schema({
    album: {
        type: mongoose.Schema.ObjectId,
        ref: "Album"
    },
    track: {
        type: Number,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    duration: {
        type: String,
        required: true
    },
    file: {
        type: String,
        default: "default.mp3"
    },
    created_at: {
        type: Date,
        default: Date.now()
    }
});

SongSchema.plugin(mongoosePaginate);

module.exports = mongoose.model("Song", SongSchema, "songs");