const express = require("express");
const albumController = require("../controllers/album");

const router = express.Router();

router.get("/test", albumController.test);

module.exports = router;