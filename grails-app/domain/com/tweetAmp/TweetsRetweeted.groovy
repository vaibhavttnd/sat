package com.tweetAmp

/*
This domain stores the data about the tweets that are retweeted by the user via tweetAmp
 */

class TweetsRetweeted {

    long reTweetId
    RetweetStatus status = RetweetStatus.PENDING

    static belongsTo = [twitterCredential: TwitterCredential]
}
