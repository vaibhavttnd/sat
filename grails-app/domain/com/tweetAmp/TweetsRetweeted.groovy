package com.tweetAmp

/*
This domain stores the data about the tweets that are retweeted by the user via tweetAmp
 */

class TweetsRetweeted {

    long reTweetId
    RetweetStatus status = RetweetStatus.PENDING
    long reTweetTime

    static belongsTo = [twitterUser: TwitterUser]

}
