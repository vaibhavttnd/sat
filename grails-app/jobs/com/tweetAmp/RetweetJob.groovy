package com.tweetAmp

import twitter4j.Twitter


class RetweetJob {

    def twitterService
    static triggers = {
        simple repeatInterval: randomTimeGenerator() * 60 * 1000 //300000l // execute job once in 10-20 minutes
    }

    def execute() {
        log.info("RetweetJob Triggered -----------------------------------@ ${new Date()}")
        Twitter twitter = twitterService.twitter
        def tweetsRetweeted1= TweetsRetweeted.createCriteria().get {
            projections {
                 min "reTweetTime"
            }
        } as Long
        println ">>>>>>>>>>>>>>>....min time>>>>>>>>>>>>>>>>>>>>>>>  "+tweetsRetweeted1
        TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByStatus(RetweetStatus.PENDING)
        if (tweetsRetweeted) {
            log.info("**********************RETWEETING*************************** tweet id " + tweetsRetweeted?.id)
            TwitterUser twitterUser = tweetsRetweeted?.twitterUser
            User user = User.findByTwitterUser(twitterUser)
            twitterService.retweetWithSpecificUser(user, twitter, tweetsRetweeted?.reTweetId)
        }
    }

    static int randomTimeGenerator(){
        new Random().nextInt(11) + 10
    }
}
