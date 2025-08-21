const connection = require("./database/connection");
const express = require("express");
const cors = require("cors");
const userRouter = require("./routes/user");
const publicationRouter = require("./routes/publication");
const followRouter = require("./routes/follow");

console.log("API NODE PARA RED SOCIAL ARRANCADA");
// Connexió a base de dades
connection();

// Crear server Node
const app = express();
const port = 3900;

// Configurar cors
app.use(cors());

// Convertir dades del body a objectes js
app.use(express.json());
app.use(express.urlencoded({extended: true}));

// Carregar conf rutes
app.use("/api/user", userRouter);
app.use("/api/publication", publicationRouter);
app.use("/api/follow", followRouter);

// Posar servidor a escoltar peticions http
app.listen(port, () => {
    console.log("Servidor de node corriendo en el puerto: " + port);
});