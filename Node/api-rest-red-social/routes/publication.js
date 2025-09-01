const express = require("express");
const publicationController = require ("../controllers/publication")
const router = express.Router();
const auth = require("../middlewares/auth");
const multer = require("multer");

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, './uploads/publications');
    },
    filename: (req, file, cb) => {
        cb(null, "pub-" + Date.now() + "-" + file.originalname);
    }
});

const uploads = multer({storage});

router.get("/prueba-publication", publicationController.test);
router.post("/save", auth.auth, publicationController.save);
router.get("/detail/:id", auth.auth, publicationController.detail);
router.delete("/remove/:id", auth.auth, publicationController.remove);
router.get("/user/:id", auth.auth, publicationController.user);
router.get("/user/:id/:page", auth.auth, publicationController.user);
router.post("/upload/:id", [auth.auth, uploads.single("file0")], publicationController.upload);
router.get("/media/:id", auth.auth, publicationController.media);
router.get("/feed/:page", auth.auth, publicationController.feed);
router.get("/feed", auth.auth, publicationController.feed);

module.exports = router;