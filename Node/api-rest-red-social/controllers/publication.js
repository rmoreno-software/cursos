const test = (req, res) => {
    return res.status(200).send({message: "Mensaje enviado des de controller"});
}

module.exports = {
    test
}