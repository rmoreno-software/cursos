const express = require("express");
const userController = require ("../controllers/user")
const router = express.Router();
const auth = require("../middlewares/auth");

router.get("/test", auth.auth, userController.pruebaUser);
router.post("/register", userController.register);
router.post("/login", userController.login);
router.get("/profile/:id", auth.auth, userController.profile);
router.get("/list/:page", auth.auth, userController.list);
router.get("/list", auth.auth, userController.list);

module.exports = router;