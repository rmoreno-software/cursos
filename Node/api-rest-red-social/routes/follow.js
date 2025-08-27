const express = require("express");
const followController = require ("../controllers/follow")
const router = express.Router();
const auth = require("../middlewares/auth");

router.get("/prueba-follow", followController.test);
router.post("/save", auth.auth, followController.save);
router.delete("/unfollow/:idFollowed", auth.auth, followController.unfollow);
router.get("/following/:id/:page", auth.auth, followController.following);
router.get("/following/:id", auth.auth, followController.following);
router.get("/followers", auth.auth, followController.followers);

module.exports = router;