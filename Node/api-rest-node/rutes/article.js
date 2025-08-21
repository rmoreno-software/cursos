const express = require("express");
const multer = require("multer");
const ArticleController = require("../controllers/article");

const router = express.Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, './images/articles');
    },
    filename: (req, file, cb) => {
        cb(null, "article" + Date.now() + file.originalname);
    }
})

const uploads = multer({storage: storage});


// Rutes de proba
router.get("/test-route", ArticleController.test);

// Rutes útil
router.post("/create", ArticleController.save)
router.get("/articles", ArticleController.list)
router.get("/articles/:lasts", ArticleController.listLasts)
router.get("/article/:id", ArticleController.one)
router.delete("/article/:id", ArticleController.deleteArticle)
router.put("/article/:id", ArticleController.update)
router.post("/article/uploadImage/:id", [uploads.single("file")], ArticleController.upload);
router.get("/image/:file", ArticleController.viewImage)
router.get("/articles/search/:search", ArticleController.search)

module.exports = router;