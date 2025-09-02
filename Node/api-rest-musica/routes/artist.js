const express = require("express");
const check = require("../middlewares/auth");
const artistController = require("../controllers/artist");
const multer = require("multer");

const router = express.Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, "./uploads/artists/")
    },
    filename: (req, file, cb) => {
        cb(null, `avatar-${Date.now()}-${file.originalname}`);
    }
});

const uploads = multer({storage});

router.get("/test", artistController.test);
router.post("/save", check.auth, artistController.save);
router.get("/one/:id", check.auth, artistController.one);
router.get("/list/", check.auth, artistController.list);
router.get("/list/:page", check.auth, artistController.list);
router.put("/update/:id", check.auth, artistController.update);
router.delete("/delete/:id", check.auth, artistController.remove);
router.post("/upload/:id", [check.auth, uploads.single("file0")], artistController.upload);
router.get("/image/:file", check.auth, artistController.image);

module.exports = router;