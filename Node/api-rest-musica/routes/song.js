const express = require("express");
const check = require("../middlewares/auth");
const songController = require("../controllers/song");
const multer = require("multer");

const router = express.Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, "./uploads/songs/")
    },
    filename: (req, file, cb) => {
        cb(null, `song-${Date.now()}-${file.originalname}`);
    }
});

const uploads = multer({storage});

router.get("/test", songController.test);
router.post("/save", check.auth,  songController.save);
router.post("/upload/:id", [check.auth, uploads.single("file0")], songController.upload);
router.get("/file/:file", check.auth, songController.file);
router.get("/one/:songId", check.auth, songController.one);
router.get("/list/:albumId", check.auth, songController.list);
router.put("/update/:songId", check.auth, songController.update);
router.delete("/remove/:songId", check.auth, songController.remove);

module.exports = router;