const Song = require("../models/Song");
const fs = require("fs");
const path = require("path");

const test = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "Test song controller"
    });
}

const save = async (req, res) => {
    try {
        let body = req.body;
        let song = new Song(body);
        await song.save();
        return res.status(200).send({
            status: "success",
            song
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error saving song"
        });
    }
}

const upload = async (req, res) => {

    try {
        const songId = req.params.id;
        if (!req.file) {
            return res.status(500).send({
                status: "error",
                message: "Request must include a file"
            });
        }

        let file = req.file.originalname;

        const fileSplit = file.split("\.");
        const extension = fileSplit[1];

        if (extension != "mp3") {
            const filePath = req.file.path;
            const fileDeleted = fs.unlinkSync(filePath);
            return res.status(500).send({
                status: "error",
                message: "Extension is not valid"
            });
        }

        let updatedSong = await Song.findByIdAndUpdate(songId, { file: req.file.filename }, { new: true });

        return res.status(200).send({
            status: "success",
            song: updatedSong
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error uploading song file"
        });
    }
}

const file = async (req, res) => {
    try {
        const file = req.params.file;
        const filePath = `./uploads/songs/${file}`;

        fs.stat(filePath, (error, exists) => {
            if (error || !exists) {
                return res.status(404).send({
                    status: "error",
                    message: "File not found"
                });
            }

            return res.sendFile(path.resolve(filePath));
        });

    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error getting file song"
        });
    }
}

const one = async (req, res) => {
    try {
        let songId = req.params.songId;
        let song = await Song.findById(songId).populate("album").exec();
        if (!song) {
            return res.status(404).send({
                status: "error",
                message: "Song not found"
            });
        }
        return res.status(200).send({
            status: "success",
            song
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error getting one song"
        });
    }
}

const list = async (req, res) => {
    try {
        let albumId = req.params.albumId;
        let songs = await Song.find({album: albumId})
                        .sort("track")
                        .populate({
                            path: "album",
                            populate: {
                                path: "artist",
                                model: "Artist"
                            }
                        })
                        .exec();

        if (!songs || songs.length < 1) {
            return res.status(404).send({
                status: "error",
                message: "Songs not found"
            });
        }
        return res.status(200).send({
            status: "success",
            songs
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error getting song list"
        });
    }
}

const update = async (req, res) => {
    try {
        let songId = req.params.songId;
        let body = req.body;

        let song = await Song.findByIdAndUpdate(songId, body, {new: true});

        if (!song) {
            return res.status(404).send({
                status: "error",
                message: "Song not found"
            });
        }
        return res.status(200).send({
            status: "success",
            song
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error updating song"
        });
    }
}

const remove = async (req, res) => {
    try {
        let songId = req.params.songId;

        let song = await Song.findByIdAndDelete(songId);

        if (!song) {
            return res.status(404).send({
                status: "error",
                message: "Song not found"
            });
        }

        return res.status(200).send({
            status: "success",
            song
        });

    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error removing song"
        });
    }
}

module.exports = {
    test,
    save,
    upload,
    file,
    one,
    list,
    update,
    remove
}