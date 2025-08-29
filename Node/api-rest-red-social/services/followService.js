const Follow = require("../models/Follow");

const followUserIds = async (identityUserId) => {
    let following = null;
    let followers = null;

    try {
        following = await Follow.find({ user: identityUserId })
            .select({ "followed": 1, "_id": 0 })
            .exec();

        followers = await Follow.find({ followed: identityUserId })
            .select({ "user": 1, "_id": 0 })
            .exec();

        let following_clean = [];

        following.forEach(follow => {
            following_clean.push(follow.followed)
        });

        let followers_clean = [];

        followers.forEach(follow => {
            followers_clean.push(follow.user)
        });

        return {
            following: following_clean,
            followers: followers_clean
        }
    } catch (error) {
        console.error(error);
        throw error;
    }
}

const followThisUser = async (identityUserId, profileUserId) => {

    let following = null;
    let follower = null;

    try {
        following = await Follow.findOne({ user: identityUserId, followed: profileUserId });

        follower = await Follow.findOne({ followed: identityUserId, user: profileUserId });

        return {
            following,
            follower
        }
    } catch (error) {
        throw new Exception("Exception at followThisUser");
    }

}

module.exports = {
    followUserIds,
    followThisUser
}