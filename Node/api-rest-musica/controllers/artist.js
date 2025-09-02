const Artist = require("../models/Artist");
const fs = require("fs");
const path = require("path");

const test = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "Test artist controller"
    });
}

const save = async (req, res) => {
    try {
        let params = req.body;

        let artist = new Artist(params);
        let artistStored = await artist.save();
        return res.status(200).send({
            status: "success",
            artistStored
        });

    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error saving artist"
        });
    }
}

const one = async (req, res) => {
    try {
        const artistId = req.params.id;

        let artist = await Artist.findById(artistId);

        if (!artist) {
            return res.status(404).send({
                status: "error",
                message: "Artist not found"
            });
        }

        return res.status(200).send({
           status: "success",
           artist 
        });
    } catch (error) {
        console.log(error);
        return res.status(500).send({
            status: "error",
            message: "Error getting one artist"
        });
    }
}

const list = async (req, res) => {
    try{
        let page = 1;
        const itemsPerPage = 3;
        if (req.params.page) page = req.params.page;

        let artists = await Artist.paginate(
            {},
            {
                page,
                limit: itemsPerPage,
                sort: ("name")
            }
        );

        return res.status(200).send({
            status: "success",
            artists
        });
    } catch(error) {
        return res.status(500).send({
            status: "error",
            message: "Error getting artists list"
        })
    }
}

const update = async (req, res) => {
    try {
        const id = req.params.id;
        const data = req.body;

        let artist = await Artist.findByIdAndUpdate(id, data, {new: true});

        if (!artist) {
            return res.status(404).send({
                status: "error",
                message: "Artist not found"
            });
        }

        return res.status(200).send({
            status: "success",
            artist
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error updating artist"
        });
    }
}

const remove = async (req, res) => {
    try {
        const artistId = req.params.id;

        const artist = await Artist.findByIdAndDelete(artistId);

        if (!artist) {
            return res.status(404).send({
                status: "error",
                message: "Artist not found"
            });
        }

        return res.status(200).send({
            status: "success",
            artistDeleted: artist
        });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error deleting artist"
        });
    }
}

const upload = async (req, res) => {

    try {
        const artistId = req.params.id;
        if (!req.file) {
            return res.status(500).send({
                status: "error",
                message: "Request must include a file"
            });
        }

        let image = req.file.originalname;

        const imageSplit = image.split("\.");
        const extension = imageSplit[1];

        if (extension != "png" && extension != "jpg" && extension != "jpeg" && extension != "gif") {
            const filePath = req.file.path;
            const fileDeleted = fs.unlinkSync(filePath);
            return res.status(500).send({
                status: "error",
                message: "Extension is not valid"
            });
        }

        let updatedArtist = await Artist.findByIdAndUpdate(artistId, { image: req.file.filename }, { new: true });

        return res.status(200).send({
            status: "success",
            artist: updatedArtist
        });
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error uploading artist image"
        });
    }
}

const image = async (req, res) => {
    try {
        const file = req.params.file;
        const filePath = `./uploads/artists/${file}`;

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
        return res.status(500).send({
            status: "error",
            message: "Error getting image artist"
        });
    }
}

module.exports = {
    test,
    save,
    one,
    list,
    update,
    remove,
    upload,
    image
}