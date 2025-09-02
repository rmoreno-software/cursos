const express = require("express");
const userController = require("../controllers/user");
const check = require("../middlewares/auth");
const multer = require("multer");

const router = express.Router();

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, "./uploads/avatars/")
    },
    filename: (req, file, cb) => {
        cb(null, `avatar-${Date.now()}-${file.originalname}`);
    }
});

const uploads = multer({storage});

router.get("/test", userController.test);
router.post("/register", userController.register);
router.post("/login", userController.login);
router.get("/profile/:id", check.auth, userController.profile);
router.put("/update", check.auth, userController.update);
router.post("/upload", [check.auth, uploads.single("file0")], userController.upload);
router.get("/avatar/:file", check.auth, userController.avatar);

module.exports = router;