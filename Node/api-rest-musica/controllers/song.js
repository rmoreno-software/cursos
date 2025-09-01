const test = (req, res) => {
    return res.status(200).send({
        status: "success",
        message: "Test song controller"
    });
}

module.exports = {
    test
}