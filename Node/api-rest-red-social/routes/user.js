const express = require("express");
const userController = require ("../controllers/user");
const multer = require("multer");
const router = express.Router();
const auth = require("../middlewares/auth");

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, './uploads/avatars');
    },
    filename: (req, file, cb) => {
        cb(null, "avatar-" + Date.now() + "-" + file.originalname);
    }
})

const uploads = multer({storage});

router.get("/test", auth.auth, userController.pruebaUser);
router.post("/register", userController.register);
router.post("/login", userController.login);
router.get("/profile/:id", auth.auth, userController.profile);
router.get("/list/:page", auth.auth, userController.list);
router.get("/list", auth.auth, userController.list);
router.put("/update", auth.auth, userController.update);
router.post("/upload", [auth.auth, uploads.single("file0")], userController.upload);
router.get("/avatar", auth.auth, userController.avatar);

module.exports = router;