package com.tweetAmp

import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class DashBoardController {

    def springSecurityService
    def grailsApplication
    def userService
    def asyncTwitterService
    def twitterService

    def index() {
        User currentUser = springSecurityService.currentUser as User
        TwitterUser accessToken = currentUser.twitterUser
        List<Status> statuses = userService.getUserTweets(accessToken)
        [statusUpdates: statuses, accessToken: accessToken]
    }

    def reTweet(long id) {
        List<Long> categoryIDs = params.categories ? params.list('categories')*.toLong() : []
        if (!categoryIDs) {
            flash.error = "No User Selected"
        } else {
            Set<User> users = (Category.findAllByIdInList(categoryIDs)*.users.unique()).first()
            Twitter twitter = twitterService.twitter
            asyncTwitterService.createNewObjects(users*.id.toSet(), params.long('statusId'))
//                Holders.grailsApplication.mainContext.getBean('twitterService').createNewObjects(users, params.long('statusId'))
//            twitterService.createNewObjects(users, params.long('statusId'))
//            users?.each { User user ->
//                asyncTwitterService.retweetWithSpecificUser(user, twitter, params.long('statusId'))
//            }
            flash.success = "Status retweeted successfully"
        }
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
            TwitterUser twitterUser = userService.saveTwitterUser(accessToken)
            if (twitterUser?.hasErrors()) {
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
        redirect controller: 'logout'
    }
}