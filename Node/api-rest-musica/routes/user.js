const express = require("express");
const userController = require("../controllers/user");
const check = require("../middlewares/auth");

const router = express.Router();

router.get("/test", userController.test);
router.post("/register", userController.register);
router.post("/login", userController.login);
router.get("/profile/:id", check.auth, userController.profile);

module.exports = router;