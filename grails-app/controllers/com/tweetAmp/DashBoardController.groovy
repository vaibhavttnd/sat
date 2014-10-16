package com.tweetAmp

import com.tweetAmp.dto.TwitterCredentialDTO
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class DashBoardController {

    def springSecurityService
    def grailsApplication
    def userService
    def twitterService

    def index() {
        User currentUser = springSecurityService.currentUser as User
        TwitterCredentials accessToken = TwitterCredentials.findByUser(currentUser)
        List<Status> statuses = userService.getUserTweets(accessToken)
        [statusUpdates: statuses, accessToken: accessToken]
    }

    def reTweet(long id) {
        Twitter twitter = twitterService.twitter
        List<TwitterCredentialDTO> twitterCredentials = twitterService.getTwitterCredentials()
        twitterCredentials.each { TwitterCredentialDTO dto ->
            twitterService.retweet(dto, twitter, id)
        }

        redirect action: "index"
    }

    def signInTwitter() {
        def callbackUrl = grailsApplication.config.grails.twitterCallbackUrl ?: ""
        Twitter twitter = twitterService.twitter

        RequestToken requestToken = null
        try {
            requestToken = twitter.getOAuthRequestToken("${callbackUrl}/dashBoard/callback");
        }
        catch (TwitterException e) {
            flash.message = "Error in signing into Twitter : ${e.message}"
        }
        session.twitter = twitter
        session.requestToken = requestToken

        response.sendRedirect(requestToken.getAuthenticationURL());
    }

    def callback() {

        Twitter twitter = session.twitter
        RequestToken requestToken = session.requestToken
        String verifier = request.getParameter("oauth_verifier");
        AccessToken accessToken
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            userService.saveTwitterCredentials(accessToken)
        }
        catch (TwitterException te) {
            if (401 == te.getStatusCode()) {
                flash.message = "Unable to get the access token. ${te.message}"
                redirect(action: 'home')
                te.printStackTrace()
            }
        }
        session.twitter = null
        session.requestToken = null

        redirect action: "index"
    }

    def revokeApp() {
        userService.revokeApp()
        redirect action: "index"
    }
}