package com.tweetAmp

import grails.plugin.springsecurity.annotation.Secured
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class DashBoardController {

    def springSecurityService
    def grailsApplication
    def userService

    def home(){
        User currentUser = springSecurityService.currentUser as User
        TwitterCredentials accessToken = TwitterCredentials.findByUser(currentUser)
        def updates = userService.getUserTweets(accessToken)
        render view: "/dashBoard",model: [statusUpdates: updates, accessToken: accessToken]
    }

    def reTweet(long id){

        def consumerKey = grailsApplication.config.twitter4j.'default'.OAuthConsumerKey?:''
        def consumerSecret = grailsApplication.config.twitter4j.'default'.OAuthConsumerSecret?:''

        List<TwitterCredentials> twitterCredentials = TwitterCredentials.findAll()
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret)

        twitterCredentials.each {i->
            AccessToken accessToken = new AccessToken(i.accessToken, i.accessTokenSecret)
            twitter.setOAuthAccessToken(accessToken)
            try {
                TweetsRetweeted tweetsRetweeted = TweetsRetweeted.findByTwitterCredentialAndReTweetId(i,id)
                if(!tweetsRetweeted) {
                    twitter.retweetStatus(id)
                    new TweetsRetweeted(reTweetId:id,twitterCredential:i).save(flush: true);
                }
            }
            catch(Exception e){
                println "Error in retweeting status"
                e.printStackTrace()
            }
        }

        redirect action :"home"
    }

   def signInTwitter(){

       def consumerKey = grailsApplication.config.twitter4j.'default'.OAuthConsumerKey?:''
       def consumerSecret = grailsApplication.config.twitter4j.'default'.OAuthConsumerSecret?:''
       def callbackUrl = grailsApplication.config.grails.twitterCallbackUrl?:""

       log.info("===callbackUrl=====${callbackUrl}=========")

       Twitter twitter = new TwitterFactory().getInstance();
       twitter.setOAuthConsumer(consumerKey, consumerSecret)
       RequestToken requestToken = null
       try {
           requestToken = twitter.getOAuthRequestToken("${callbackUrl}/dashBoard/callback");
       }
       catch(TwitterException e){
           flash.message ="Error in signing into Twitter"
           e.printStackTrace()
       }
       session.twitter = twitter
       session.requestToken = requestToken

       response.sendRedirect(requestToken.getAuthenticationURL());
   }

    def callback(){

        Twitter twitter = session.twitter
        RequestToken requestToken = session.requestToken
        String verifier = request.getParameter("oauth_verifier");
        AccessToken accessToken
        try{
            accessToken  = twitter.getOAuthAccessToken(requestToken, verifier);
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

        redirect action :"home"
    }

    def revokeApp(){

        userService.revokeApp()
        redirect action :"home"
    }

}
