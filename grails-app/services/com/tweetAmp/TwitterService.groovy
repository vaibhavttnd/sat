package com.tweetAmp

import com.tweetAmp.dto.TwitterCredentialDTO
import grails.transaction.Transactional
import twitter4j.Twitter
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
        TwitterCredentials.createCriteria().list {
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
        TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByTwitterCredentialAndReTweetId(TwitterCredentials.load(dto.id), id)
        try {
            if (!tweetsRetweeted) {
                twitter.retweetStatus(id)
                tweetsRetweeted = new TweetsRetweeted(reTweetId: id, twitterCredential: TwitterCredentials.load(dto.id)).save(flush: true);
            }
        }
        catch (Exception e) {
            println "Error in retweeting status ${e.message}"
        }
        return tweetsRetweeted
    }

}
