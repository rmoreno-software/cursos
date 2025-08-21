const { connection } = require("./database/connection")
const express = require("express")
const cors = require("cors");

// Inicialitzar app
console.log("Node app started");

// Connectar a la base de dades
connection();

// Crear servidor Node
const app = express();
const port = 3900;

// Configurar cors
app.use(cors());

// Convertir body a objecte js
app.use(express.json()); // recibir datos con content-type app/json
app.use(express.urlencoded({extended:true})); // recibir datos con content-type form-urlencoded

// Crear rutes
const article_rutes = require("./rutes/article");
app.use("/api", article_rutes);

// Crear servidor i escoltar peticions http
app.listen(port, () => {
    console.log("Server running and listening port " + port);
})