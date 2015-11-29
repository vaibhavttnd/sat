package com.tweetAmp

import twitter4j.Twitter


class RetweetJob {

    def twitterService
    def grailsApplication
    static triggers = {
        simple repeatInterval: (grailsApplication.config.retweet?.jobInterval?:1) * 60 * 1000
    }

    def execute() {
        log.info("RetweetJob Triggered -----------------------------------@ ${new Date()}")
        List<TweetsRetweeted> reTweets = TweetsRetweeted.createCriteria().list {
            eq("status", RetweetStatus.PENDING)
            lte("reTweetTime", new Date().getTime())
        }
        Date skipTime = new Date()
        skipTime.minutes -= (grailsApplication.config.retweet?.skipJobInterval?:2) // skip tweets <skipTime> ago and reschedule them
        List<TweetsRetweeted> skipReTweets = reTweets.findAll { it.reTweetTime < skipTime.getTime() }
        reTweets -= skipReTweets
        twitterService.retweet(reTweets)
        twitterService.scheduleTweets(skipReTweets)
    }

}
