const Publication = require("../models/Publication");
const User = require("../models/User");
const fs = require("fs");
const path = require("path");

const test = (req, res) => {
    return res.status(200).send({ message: "Mensaje enviado des de controller" });
}

const save = async (req, res) => {

    let userIdentity = null;
    let params = req.body;
    let newPublication = null;
    let savedPublication = null;

    if (!params.text) {
        return res.status(500).send({
            status: "error",
            message: "Falta añadir el texto en la petición"
        });
    }

    try {
        userIdentity = await User.findById(req.user.id).exec();

        if (!userIdentity) {
            return res.status(404).send({
                status: "error",
                message: "Error en la cerca de l'usuari autenticat"
            });
        }

        params.user = userIdentity._id;

        newPublication = new Publication(params);

        savedPublication = await newPublication.save();
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error saving publication",
        })
    }

    return res.status(200).send({
        state: "success",
        savedPublication
    });
}

const detail = async (req, res) => {

    const publicationId = req.params.id;
    let publication = null;

    try {
        publication = await Publication.findById(publicationId);

        if (!publication) {
            return res.status(404).send({
                status: "error",
                message: "No existe la publicación"
            });
        }
    } catch (error) {
        console.log(error);
        return res.status(500).send({
            status: "error",
            message: "Error fent detail de publication"
        });
    }

    return res.status(200).json({
        status: "success",
        publication
    });
}

const remove = async (req, res) => {

    const publicationId = req.params.id;
    let publication = null;

    try {
        publication = await Publication.deleteOne({ user: req.user.id, _id: publicationId });
    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error removing publication"
        });
    }

    return res.status(200).send({
        status: "success",
        publication
    });

}

const user = async (req, res) => {

    const userId = req.params.id;
    let page = 1;
    const itemsPerPage = 3;
    let publications = null;

    if (req.params.page) page = req.params.page;

    try {

        publications = await Publication.paginate(
            { user: userId },
            {
                page,
                limit: itemsPerPage,
                sort: ("-created_at"),
                populate: [
                    { path: "user", select: "-password -role -__v" }
                ]
            }
        );

        if (publications.totalDocs < 1) {
            return res.status(404).send({
                status: "error",
                message: "Publications not found"
            });
        }

    } catch (error) {
        console.error(error);
        return res.status(500).send({
            status: "error",
            message: "Error listing user publications"
        });
    }

    return res.status(200).send({
        status: "success",
        publications
    });

}

const upload = async (req, res) => {

    const userIdentity = req.user;
    const publicationId = req.params.id;

    if (!req.file) {
        return res.status(500).send({
            status: "success",
            message: "Falta proporcionar la imagen"
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
            message: "Extensión no válida"
        });

    }

    let publicationUpdated = null;

    try {
        publicationUpdated = await Publication.findOneAndUpdate({ user: userIdentity.id, _id: publicationId}, { file: req.file.filename }, { new: true });
    } catch (error) {
        console.log("Error: " + error);
        return res.status(500).send({
            status: "error",
            message: "Error uploading image"
        });
    }

    if (!publicationUpdated) {
        return res.status(404).send({
            status: "error",
            message: "Publication not found"
        });
    }

    return res.status(200).send({
        status: "success",
        publication: publicationUpdated,
    });
}

const media = async (req, res) => {

    let publicationId = req.params.id;
    let publication = null;

    try {
        publication = await Publication.findById(publicationId).exec();
    } catch (error) {
        console.log(error)
        return res.status(500).send({
            status: "Error",
            message: "Error searching publication"
        });
    }

    if (!publication) {
        return res.status(404).send({
            status: "error",
            message: "Publication not found"
        });
    }

    const filePath = "./uploads/publications/" + publication.file;
    console.log("filePath: " + filePath);

    fs.stat(filePath, (error, exists) => {
        if (!exists) {
            return res.status(404).send({
                status: "error",
                message: "No existe la imagen",
            });
        }

        return res.sendFile(path.resolve(filePath));
    });


}

module.exports = {
    test,
    save,
    detail,
    remove,
    user,
    upload,
    media
}