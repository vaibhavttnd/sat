package com.tweetAmp

import twitter4j.Twitter


class RetweetJob {

	def twitterService
	static triggers = {
		simple repeatInterval: 3 * 60 * 1000 //300000l // execute job once in 3 minutes
	}

	def execute() {
		log.info("RetweetJob Triggered -----------------------------------@ ${new Date()}")
		Twitter twitter = twitterService.twitter

		def results = TweetsRetweeted.executeQuery("select tweets from TweetsRetweeted as tweets where status=:status order by reTweetTime asc", [status: RetweetStatus.PENDING, max: 1])
		println ">>>>>>>>>>>>>>>....min time>>>>>>>>>>>>>>>>>>>>>>>  " + results
            TweetsRetweeted tweetsRetweeted = results.first()
            if (tweetsRetweeted && tweetsRetweeted.reTweetTime<=(new Date()).getTime()) {
                log.info("**********************RETWEETING*************************** tweet id " + tweetsRetweeted?.id)
                TwitterUser twitterUser = tweetsRetweeted?.twitterUser
                User user = User.findByTwitterUser(twitterUser)
                twitterService.retweetWithSpecificUser(user, twitter, tweetsRetweeted?.reTweetId)
            }
	}

}
