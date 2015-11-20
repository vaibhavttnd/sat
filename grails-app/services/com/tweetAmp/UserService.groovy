package com.tweetAmp

import grails.plugin.springsecurity.oauth.OAuthToken
import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.scribe.model.Token
import twitter4j.Paging
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.OAuth2Token
import twitter4j.conf.ConfigurationBuilder

@Transactional
class UserService {

    public static final String TWITTER_USER_PROFILE_URL = "https://api.twitter.com/oauth/authenticate"
    public static final String INTELLI_GRAPE = "TOTHENEW"
    def oauthService
    def springSecurityService
    def grailsApplication

    def registerNewUser(OAuthToken oAuthToken, Token twitterAccessToken) {
        String password = RandomStringUtils.randomAlphabetic(10)
        Map<String, String> twitterDetails = tokenizeParameters(twitterAccessToken.rawResponse)
        TwitterUser twitterUser = saveTwitterUser(new TwitterUser(), twitterDetails)
        User newUser = new User(twitterUser: twitterUser, username: twitterDetails.screen_name, password: password, enabled: true)

        if (newUser.save(flush: true, failOnError: true)){
            addRoleForUser(newUser, Role.findByAuthority("ROLE_USER"))

            //Apply for all categories
            applyForAllCategories(newUser)
        }
        return newUser
    }

    def saveTwitterUser(TwitterUser twitterUser, def twitterDetails) {
        twitterUser.accessToken = twitterDetails.oauth_token
        twitterUser.accessTokenSecret = twitterDetails.oauth_token_secret
        twitterUser.provider = grailsApplication.config.oauthProvider.name
        twitterUser.screenName = twitterDetails.screen_name
        twitterUser.save(flush: true)

        return twitterUser
    }

    def OAuth2Token getOAuth2Token() {

        def twitterConfig = grailsApplication.config.twitter4j
        String consumerKey = twitterConfig.'default'.OAuthConsumerKey ?: ''
        String consumerSecret = twitterConfig.'default'.OAuthConsumerSecret ?: ''

        OAuth2Token token
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret);

        try {
            token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token();
        }
        catch (Exception e) {
            println "Could not get OAuth2 token ${e.message}"
        }

        return token;
    }

    List<Status> getUserTweets(TwitterUser twitterUser) {
        List<Status> statuses = []
        if (twitterUser.accessToken) {
            def twitterConfig = grailsApplication.config.twitter4j
            String consumerKey = twitterConfig.'default'.OAuthConsumerKey ?: ''
            String consumerSecret = twitterConfig.'default'.OAuthConsumerSecret ?: ''

            ConfigurationBuilder cb = new ConfigurationBuilder();

            if (!twitterUser) {
                OAuth2Token token = getOAuth2Token();
                cb.setApplicationOnlyAuthEnabled(true);
                cb.setOAuth2TokenType(token.getTokenType());
                cb.setOAuth2AccessToken(token.getAccessToken());
            }

            cb.setOAuthConsumerKey(consumerKey);
            cb.setOAuthConsumerSecret(consumerSecret);
            Twitter twitter = new TwitterFactory(cb.build()).getInstance();


            AccessToken accessToken = new AccessToken(twitterUser.accessToken, twitterUser.accessTokenSecret)
            twitter.setOAuthAccessToken(accessToken)

            try {
                statuses = twitter.getUserTimeline(INTELLI_GRAPE, new Paging(1, 50))
            }
            catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    println "Error with application authorization for TweetAmp ${te.message}"
                }
            }
        }

        return statuses;
    }

    TwitterUser saveTwitterUser(AccessToken accessToken) {
        User currentUser = springSecurityService.currentUser as User
        TwitterUser twitterUser = new TwitterUser(accessToken: accessToken.token,
                accessTokenSecret: accessToken.tokenSecret, screenName: accessToken.getScreenName(),
                twitterUserId: accessToken.getUserId(), user: currentUser)
        twitterUser.save(flush: true)
        if (!twitterUser.hasErrors()) {
            currentUser.twitterUser = twitterUser
            currentUser.save(flush: true)
        }
        return twitterUser
    }

    void revokeApp() {
        User currentUser = springSecurityService.currentUser as User
        TwitterUser twitterUser = currentUser.twitterUser
        println('In service >>' + twitterUser.properties)
        twitterUser.accessToken = null
        twitterUser.accessTokenSecret = null
        twitterUser.save(flush: true)
        println("---removed----" + twitterUser.properties)
    }

    def updateRoleForExistingUser(User user, Role role) {
        if (user.authorities.first().id != role.id) {
            removeExistingRole(user)
            addRoleForUser(user, role)
        }
    }

    def removeExistingRole(User user) {
        UserRole.executeUpdate("delete UserRole userRole where userRole.user.id=:userId", [userId: user.id])
    }

    def addRoleForUser(User user, Role role) {
        new UserRole(user: user, role: role).save(flush: true)
    }

    Map<String, String> tokenizeParameters(String parameterList) {
        List<String> parameterAndValue = parameterList.tokenize('&')
        println parameterAndValue
        Map<String, String> map = [:]
        parameterAndValue.each {
            List<String> parameterAndItsValue = it.tokenize('=')
            map.put(parameterAndItsValue.get(0), parameterAndItsValue.get(1))
        }
        map
    }

    def applyForAllCategories(User user){
        List<Category> categories=Category.list()
        categories.each {Category category->
            user.addToCategories(category)
            category.save(flush: true, failOnError: true)
        }
    }
}
