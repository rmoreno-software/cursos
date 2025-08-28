const Follow = require("../models/Follow");
const User = require("../models/User");
const followService = require("../services/followService");

const test = (req, res) => {
    return res.status(200).send({ message: "Mensaje enviado des de controller" });
}
const save = async (req, res) => {

    const userIdentity = req.user;

    const idFollowed = req.body.followed;

    let follower, followed, follow = null;

    try {
        follower = await User.findById(userIdentity.id);
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error searching follower"
        });
    }

    if (!follower) {
        return res.status(404).send({
            status: "error",
            message: "User not found"
        });
    }

    try {
        followed = await User.findById(idFollowed);
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error searching followed"
        });
    }

    if (!followed) {
        return res.status(404).send({
            status: "error",
            message: "User followed not found"
        });
    }

    try {
        follow = await Follow.findOne({ user: follower._id, followed: followed._id }).exec();
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error searching follow"
        });
    }

    if (follow) {
        return res.status(500).send({
            status: "error",
            message: "Follow already exists"
        });
    }

    let followToSave = new Follow({
        user: follower._id,
        followed: followed._id
    });

    let followSaved = null;

    try {
        followSaved = await followToSave.save();
    } catch (error) {
        return res.status(500).send({
            status: "error",
            message: "Error creating follow"
        });
    }

    if (!followSaved) {
        return res.status(500).send({
            status: "error",
            message: "Error creating follow"
        });
    }

    return res.status(200).send({
        status: "success",
        followSaved
    });

}

const unfollow = async (req, res) => {

    const userIdentity = req.user;
    const idFollowed = req.params.idFollowed;

    let follow = null;

    try {
        follow = await Follow.deleteOne(
            { user: userIdentity.id, followed: idFollowed });
    } catch (error) {
        console.log(error);
        return res.status(500).send({
            status: "error",
            message: "Error removing follor"
        });
    }
    return res.status(200).send({
        status: "success",
        follow
    });
}

const following = async (req, res) => {

    let userId = req.user.id;

    if (req.params.id) userId = req.params.id;

    let page = 1;

    if (req.params.page) page = req.params.page;

    const itemsPerPage = 3;

    let following = null;
    let followingFromService = null;

    try {
        following = await Follow.paginate(
            { user: userId },
            {
                page,
                limit: itemsPerPage,
                sort: { _id: 1 },
                populate: [
                    { path: "user", select: "-password -role -__v" },
                    { path: "followed", select: "-password -role -__v" }
                ]
            }
        );

        followingFromService = await followService.followUserIds(req.user.id);

    } catch (error) {
        console.log(error);
        return res.status(500).send({
            status: "error",
            message: "Error in following endpoint"
        });
    }

    return res.status(200).send({
        status: "success",
        following,
        user_following: followingFromService.following,
        user_followers: followingFromService.followers
    });
};

const followers = (req, res) => {
    return res.status(200).send({
        status: "success"
    });
};

module.exports = {
    test,
    save,
    unfollow,
    following,
    followers
}