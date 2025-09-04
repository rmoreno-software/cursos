const express = require("express");
const check = require("../middlewares/auth");
const albumController = require("../controllers/album");
const multer = require("multer");

const router = express.Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, "./uploads/albums/")
    },
    filename: (req, file, cb) => {
        cb(null, `album-${Date.now()}-${file.originalname}`);
    }
});

const uploads = multer({storage});

router.get("/test", albumController.test);
router.post("/save", check.auth, albumController.save);
router.get("/one/:id", check.auth, albumController.one);
router.get("/list/:artistId", check.auth, albumController.list);
router.put("/update/:albumId", check.auth, albumController.update);
router.post("/upload/:id", [check.auth, uploads.single("file0")], albumController.upload);
router.get("/image/:file", check.auth, albumController.image);
router.delete("/remove/:id", check.auth, albumController.remove);

module.exports = router;