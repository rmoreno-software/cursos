const Album = require("../models/Album");
const Song = require("../models/Song");
const fs = require("fs");
const path = require("path");

const test = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "Test album controller"
    });
}

const save = async (req, res) => {
    try {
        let params = req.body;
        let album = new Album(params);
        await album.save();
        return res.status(200).send({
            status: "success",
            album
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error saving album"
        });
    }
}

const one = async (req, res) => {
    try {
        let albumId = req.params.id;
        const album = await Album.findById(albumId).populate({path: "artist"}).exec();
        if (!album) {
            return res.status(404).send({
                status: "error",
                message: "Album not found"
            });
        }
        return res.status(200).send({
            status: "success",
            album
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error getting one album"
        });
    }
}

const list = async (req, res) => {
    try {
        let artistId = req.params.artistId;

        let albums = await Album.find({artist: artistId}).populate("artist").exec();

        if (!albums || albums.length < 1) {
            return res.status(404).send({
                status: "error",
                message: "Albums not found"
            });
        }
        return res.status(200).send({
            status: "success",
            albums
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error getting artist albums"
        });
    }
}

const update = async (req, res) => {
    try {
        let albumId = req.params.albumId;
        let body = req.body;

        let album = await Album.findByIdAndUpdate(albumId, body, {new: true});

        if (!album) {
            return res.status(404).send({
                status: "error",
                message: "Album not found"
            });
        }

        return res.status(200).send({
            status: "success",
            album
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error updating album"
        });
    }
}

const upload = async (req, res) => {

    try {
        const albumId = req.params.id;
        if (!req.file) {
            return res.status(500).send({
                status: "error",
                message: "Request must include a file"
            });
        }

        let image = req.file.originalname;

        const imageSplit = image.split("\.");
        const extension = imageSplit[1];

        if (extension != "png" && extension != "jpg" && extension != "jpeg" && extension != "gif" && extension != "jfif") {
            const filePath = req.file.path;
            const fileDeleted = fs.unlinkSync(filePath);
            return res.status(500).send({
                status: "error",
                message: "Extension is not valid"
            });
        }

        let updatedAlbum = await Album.findByIdAndUpdate(albumId, { image: req.file.filename }, { new: true });

        return res.status(200).send({
            status: "success",
            artist: updatedAlbum
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error uploading album image"
        });
    }
}

const image = async (req, res) => {
    try {
        const file = req.params.file;
        const filePath = `./uploads/albums/${file}`;

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
        console.error(error)
        return res.status(500).send({
            status: "error",
            message: "Error getting image album"
        });
    }
}

const remove = async (req, res) => {
    try {
        const albumId = req.params.id;

        const albumToRemove = await Album.findById(albumId);
        const songsToRemove = await Song.find({album: albumId}).populate({
            path: "album",
            populate: "artist"
        });        

        if (!albumToRemove) {
            return res.status(404).send({
                status: "error",
                message: "Album not found"
            });
        }

        if (songsToRemove && songsToRemove.length > 0) {
            songsToRemove.forEach(async (song) => {
                await Song.findByIdAndDelete(song._id);
            });
        }

        await Album.findByIdAndDelete(albumId);

        return res.status(200).send({
            status: "success",
            albumRemoved: albumToRemove,
            songsRemoved: songsToRemove,
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error deleting artist"
        });
    }
}

module.exports = {
    test,
    save,
    one,
    list,
    update,
    upload,
    image,
    remove
}