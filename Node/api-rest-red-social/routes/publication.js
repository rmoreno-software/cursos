const express = require("express");
const publicationController = require ("../controllers/publication")
const router = express.Router();

router.get("/prueba-publication", publicationController.test);

module.exports = router;