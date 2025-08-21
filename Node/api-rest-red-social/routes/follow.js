const express = require("express");
const followController = require ("../controllers/follow")
const router = express.Router();

router.get("/prueba-follow", followController.test);

module.exports = router;