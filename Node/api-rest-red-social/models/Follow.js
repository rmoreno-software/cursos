const mongoose = require("mongoose");
const mongoosePaginate = require("mongoose-paginate-v2"); // 👈 esto devuelve una función

const FollowSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.ObjectId,
        ref: "User"
    },
    followed: {
        type: mongoose.Schema.ObjectId,
        ref: "User"
    },
    created_at: {
        type: Date,
        default: Date.now()
    }
});

FollowSchema.plugin(mongoosePaginate);

module.exports = mongoose.model("Follow", FollowSchema, "follows");