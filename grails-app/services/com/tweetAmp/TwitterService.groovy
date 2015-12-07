package com.tweetAmp

import grails.transaction.Transactional
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

@Transactional
class TwitterService {

	def grailsApplication

	Twitter getTwitter() {
		def twitterConfig = grailsApplication.config.twitter4j
		String consumerKey = twitterConfig.'default'.OAuthConsumerKey ?: ''
		String consumerSecret = twitterConfig.'default'.OAuthConsumerSecret ?: ''

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret)
		return twitter
	}

	List<TwitterCredentialDTO> getTwitterCredentials() {
		TwitterUser.createCriteria().list {
			projections {
				groupProperty('screenName')
				property('id')
				property('accessToken')
				property('accessTokenSecret')
			}
		}.collect {
			new TwitterCredentialDTO(id: it[1], screenName: it[0], accessToken: it[2], accessTokenSecret: it[3])
		}

	}

	TweetsRetweeted retweet(TwitterCredentialDTO dto, Twitter twitter, Long id) {
		AccessToken accessToken = new AccessToken(dto.accessToken, dto.accessTokenSecret)
		twitter.setOAuthAccessToken(accessToken)
		TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByTwitterUserAndReTweetId(TwitterUser.load(dto.id), id)
		try {
			if (!tweetsRetweeted) {
				twitter.retweetStatus(id)
				tweetsRetweeted = new TweetsRetweeted(reTweetId: id, twitterUser: TwitterUser.load(dto.id)).save(flush: true);
			}
		}
		catch (Exception e) {
			println "Error in retweeting status ${e.message}"
		}
		return tweetsRetweeted
	}

    def retweet(List<TweetsRetweeted> reTweets) {
        reTweets.each { TweetsRetweeted tweetsRetweeted ->
            log.info("**********************RETWEETING*************************** tweet id " + tweetsRetweeted?.id)
            TwitterUser twitterUser = tweetsRetweeted?.twitterUser
            User user = User.findByTwitterUser(twitterUser)
            retweetWithSpecificUser(user, twitter, tweetsRetweeted?.reTweetId)
        }
    }

	TweetsRetweeted retweetWithSpecificUser(User user, Twitter twitter, Long tweetId) {
		println "retweeting*************************************************" + user?.email
		TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByTwitterUserAndReTweetId(user.twitterUser, tweetId)
		if (user?.twitterUser?.accessToken) {
			AccessToken accessToken = new AccessToken(user?.twitterUser?.accessToken, user?.twitterUser?.accessTokenSecret)
			twitter.setOAuthAccessToken(accessToken)
			if (tweetsRetweeted) {
				println "found******************************"
					try {
						if (tweetsRetweeted.status == RetweetStatus.PENDING) {
							println "tweet id        " + tweetId
							twitter.retweetStatus(tweetId)
							tweetsRetweeted.status = RetweetStatus.DONE
							tweetsRetweeted.save(flush: true);
						}
					}
					catch (TwitterException e) {
						tweetsRetweeted.status = RetweetStatus.REJECTED
						tweetsRetweeted.save(flush: true);
						log.info("Error in retweeting status: \n${e.message}\n" + e.errorMessage)
						log.info("Status Code*************************************************\n" + e.getStatusCode())
					}
			} else {
				println "not found******************************************"
			}
		} else {
			tweetsRetweeted.status= RetweetStatus.REJECTED
			tweetsRetweeted.save(flush: true);
		}
		return tweetsRetweeted
	}

    void createNewObjects(Set<Long> userIds, Long tweetId) {
        try {
            Set<User> users = User.getAll(userIds.toList())
            long now=(new Date()).getTime();
            users.each { User user ->
                TwitterUser credential = user.twitterUser
                TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByTwitterUserAndReTweetId(user.twitterUser, tweetId)
                if (credential && !tweetsRetweeted) {
                    now+=(randomTimeGenerator()*60000)
                    println  "............................................................\ncreateing new one at "+now
                    tweetsRetweeted = new TweetsRetweeted(status: RetweetStatus.PENDING)
                    tweetsRetweeted.reTweetTime=now
                    tweetsRetweeted.reTweetId = tweetId
                    tweetsRetweeted.twitterUser = credential
                    tweetsRetweeted.save(failOnError: true,flush: true)
                    println "............................................................ "+tweetsRetweeted.reTweetTime
                    credential.addToRetweets(tweetsRetweeted)
                    println "............................................................ "+tweetsRetweeted.reTweetTime
                            credential.save(failOnError: true, flush: true)
                }
            }
            println "*********************************Objects created*********************************************"
        }
        catch (Exception e) {
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" + e
            e.printStackTrace(System.out)
        }
    }

    int randomTimeGenerator(){
        new Random().nextInt(11) + 10
    }

    def scheduleTweets(List<TweetsRetweeted> tweetsRetweetedList) {
        long time = new Date().getTime()
        tweetsRetweetedList.each { TweetsRetweeted retweet ->
            time += randomTimeGenerator() * 6000
            retweet.reTweetTime = time
            retweet.save(flush: true)
        }
    }

}
