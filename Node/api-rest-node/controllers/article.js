const {validateArticle} = require("../helpers/validate");
const Article = require("../models/Article");
const fs = require("fs");
const path = require("path");

const test = (req, res) => {
    return res.status(200).json({
        msg: "Soy una acción de prueba en mi controlador de artículos"
    });
}

const save = async (req, res) => {

    // Recollir els paràmetres per post
    let param = req.body;
    
    // Validar dades
    try {
        validateArticle(param);
    } catch(error) {
        return res.status(400).json({
            status: "error",
            msg: "Faltan datos por enviar"
        });
    }

    // Crear objecte a guardar
    const article = new Article(param);

    // Guardar article a la base de dades
    const savedArticle = await article.save();

    return res.status(200).json({
        status: "success",
        article: savedArticle,
        msg: "Article saved"
    });

}

const list = async (req, res) => {
    let articles = await Article.find({})
            .sort({date: -1})
            .exec();

    if (!articles || articles.length === 0) {
        return res.status(404).json({
            status: "not found",
            msg: "Articles not found"
        });
    }

    return res.status(200).json({
        status: "success",
        param_url: req.params.lasts,
        count: articles.length,
        articles
    });
}

const listLasts = async (req, res) => {
    let articles = await Article.find({})
                                .limit(3)
                                .sort({date: -1})
                                .exec();

    if (!articles || articles.length === 0) {
        return res.status(404).json({
            status: "not found",
            msg: "Articles not found"
        });
    }

    return res.status(200).json({
        status: "success",
        param_url: req.params.lasts,
        count: articles.length,
        articles
    });
}

const one = async (req, res) => {
    let id = req.params.id;

    let article = await Article.findById(id).exec();

    if (!article) {
        return res.status(404).json({
            status: "not found",
            msg: "Article not found"
        });
    }

    return res.status(200).json({
        status: "success",
        article
    });
}

const deleteArticle = async (req, res) => {
    
    let id = req.params.id;

    let article = await Article.findOneAndDelete({_id: id}).exec();

    if (!article) {
        return res.status(400).json({
            status: "not found",
            msg: "Article not found"
        });
    }

    return res.status(200).json({
        status: "success",
        msg: "Article deleted",
        article
    });
}

const update = async (req, res) => {

    let id = req.params.id;
    let param = req.body;

    try {
        validateArticle(param);
    } catch(error) {
        return res.status(400).json({
            status: "error",
            msg: "Faltan datos por enviar"
        });
    }
    
    let article = await Article.findOneAndUpdate({_id: id}, param, {new: true});

    if (!article) {
        return res.status(400).json({
            status: "error",
            msg: "Error updating article"
        });
    }

    return res.status(200).json({
        status: "success",
        msg: "Article updated",
        article
    });
}

const upload = async (req, res) => {

    if (!req.file) {
        return res.status(400).json({
            status: "error",
            msg: "Invalid request"
        });
    }

    let fileName = req.file.originalname;

    let splitFile = fileName.split("\.");

    let extensionFile = splitFile[1];

    if (extensionFile != "png" && extensionFile != "jpg"
        && extensionFile != "jpeg" && extensionFile != "gif") {

            fs.unlink(req.file.path, (error) => {
                return res.status(400).json({
                    status: "error",
                    msg: "Format not valid"
                });
            });
    } else {
        
        let id = req.params.id;
        
        let article = await Article.findOneAndUpdate({_id: id}, {image: req.file.filename}, {new: true});

        if (!article) {
            return res.status(400).json({
                status: "error",
                msg: "Error updating article"
            });
        }

        return res.status(200).json({
            status: "success",
            msg: "Article updated",
            article
        });
    }
}

const viewImage = (req, res) => {
    let file = req.params.file;
    let file_path = "./images/articles/"+file;

    if (fs.existsSync(file_path)) {
        return res.sendFile(path.resolve(file_path));
    } else {
        return res.status(404).json({
            status: "error",
            msg: "Error getting image"
        });
    }

}

const search = async (req, res) => {

    let search = req.params.search;

    let findedArticles = await Article.find({ "$or": [
        {"title": { "$regex": search, "$options": "i"}},
        {"content": { "$regex": search, "$options": "i"}},
    ]})
    .sort({date: -1})
    .exec();

    if (findedArticles.length <= 0) {
        return res.status(404).json({
            status: "error",
            msg: "Articles not found"
        });
    } else {
        return res.status(200).json({
            status: "success",
            articles: findedArticles
        });
    }
}

module.exports = {
    test,
    save,
    list,
    listLasts,
    one,
    deleteArticle,
    update,
    upload,
    viewImage,
    search
}