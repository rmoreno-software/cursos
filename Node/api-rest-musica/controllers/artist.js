const test = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "Test artist controller"
    });
}

module.exports = {
    test
}