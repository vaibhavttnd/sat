package com.tweetAmp

class TwitterUser implements Serializable{

    String accessToken
    String accessTokenSecret
    String screenName
    String provider

    long twitterUserId

    static belongsTo = [user: User]
    static hasMany = [retweets: TweetsRetweeted]

    static constraints = {
        retweets nullable: true
    }

}
