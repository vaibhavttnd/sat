package com.tweetAmp

import twitter4j.Twitter


class RetweetJob {

    def twitterService
    static triggers = {
        simple repeatInterval: 1 * 60 * 1000 //300000l // execute job once in 1 minute
    }

    def execute() {
        log.info("RetweetJob----------------------------------- ${new Date()}")
        Twitter twitter = twitterService.twitter
        TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByStatus(RetweetStatus.PENDING)
        if (tweetsRetweeted) {
            log.info("**********************RETWEETING*************************** tweet id " + tweetsRetweeted?.id)
            TwitterUser twitterUser = tweetsRetweeted?.twitterUser
            User user = User.findByTwitterUser(twitterUser)
            twitterService.retweetWithSpecificUser(user, twitter, tweetsRetweeted?.reTweetId)
        }
    }
}
