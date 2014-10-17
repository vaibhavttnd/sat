package com.tweetAmp

class TwitterCredential {

    String accessToken
    String accessTokenSecret
    String screenName
    long twitterUserId

    static belongsTo = [user: User]
    static hasMany = [retweets: TweetsRetweeted]

    static constraints = {
        retweets nullable: true
    }

}
