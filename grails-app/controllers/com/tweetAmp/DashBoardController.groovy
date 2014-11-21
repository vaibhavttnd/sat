package com.tweetAmp

import com.tweetAmp.dto.TwitterCredentialDTO
import twitter4j.Status
import twitter4j.StatusJSONImpl
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class DashBoardController {

    def springSecurityService
    def grailsApplication
    def userService
    def twitterService

    def index() {
        User currentUser = springSecurityService.currentUser as User
        TwitterCredential accessToken = currentUser.twitterCredential
        List<Status> statuses = userService.getUserTweets(accessToken)
        [statusUpdates: statuses, accessToken: accessToken]
    }

    //will be used later - to be shifted to a service
    def reTweet(long id) {
        List<Long> categoryIDs = params.categories ? params.list('categories')*.toLong() : []
        Set<User> users = (Category.findAllByIdInList(categoryIDs)*.users.unique()).first()
        Long retweetStatusID = params.long('statusId')
        println "@@@@@@@@@@@@@@@@@@@@@@@        " + users.getClass()
        Twitter twitter = twitterService.twitter
        List<TwitterCredential> twitterCredentials = []
        users?.each { User user ->
//            twitterCredentials.add(user.twitterCredential)

            println "******************* " + params.statusId
            println "******************* " + retweetStatusID
            twitterService.retweet_new(user, twitter, retweetStatusID)
            println "#####################################" + user.id
        }
//        List<TwitterCredentialDTO> twitterCredentialDTOs = twitterService.getTwitterCredentials()
//        twitterCredentials.each { TwitterCredentialDTO dto ->
//            twitterService.retweet(dto, twitter, id)
//        }
        flash.success = "Status retweeted successfully"
        redirect action: "index"
    }

    def reTweetForm() {
        render template: 'retweetform', model: params
    }

    def signInTwitter() {
        def callbackUrl = grailsApplication.config.grails.twitterCallbackUrl ?: ""
        Twitter twitter = twitterService.twitter

        RequestToken requestToken = null
        try {
            requestToken = twitter.getOAuthRequestToken("${callbackUrl}/dashBoard/callback");
        }
        catch (TwitterException e) {
            flash.error = "Error in signing into Twitter : ${e.message}"
        }
        session.twitter = twitter
        session.requestToken = requestToken

        response.sendRedirect(requestToken.getAuthenticationURL());
    }

    def callback(String oauth_verifier) {
        Twitter twitter = session.twitter
        RequestToken requestToken = session.requestToken
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
            TwitterCredential twitterCredentials = userService.saveTwitterCredentials(accessToken)
            if (twitterCredentials?.hasErrors()) {
                flash.error = "Unable to connect to twitter"
            } else {
                flash.success = "Twitter account connected successfully"
            }
        } catch (TwitterException te) {
            flash.error = "Unable to get the access token. ${te.message}"
        }
        redirect action: "index"
    }

    def revokeApp() {
        userService.revokeApp()
        flash.success = "Twitter account removed successfully"
        redirect action: "index"
    }
}