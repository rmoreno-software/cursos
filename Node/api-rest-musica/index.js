const connection = require("./database/connection");
const express = require("express");
const cors = require("cors");
const albumRoutes = require("./routes/album");
const artistRoutes = require("./routes/artist");
const songRoutes = require("./routes/song");
const userRoutes = require("./routes/user");

console.log("API REST with Node for the music app started!!");

connection();

const app = express();
const port = 3910;

app.use(cors());

app.use(express.json());
app.use(express.urlencoded({extended: true}));

app.use("/api/album", albumRoutes);
app.use("/api/artist", artistRoutes);
app.use("/api/song", songRoutes);
app.use("/api/user", userRoutes);

app.listen(port, () => {
    console.log(`Node server is listening on port: ${port}`);
});

