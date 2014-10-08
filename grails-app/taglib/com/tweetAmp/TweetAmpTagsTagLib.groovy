package com.tweetAmp

class TweetAmpTagsTagLib {
    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "t"

    /** Dependency injection for springSecurityService. */
    def springSecurityService


    def userName = {attrs ->
        if (springSecurityService.isLoggedIn()) {
            User user = springSecurityService.currentUser as User
            out << user.name
        }
    }

    def tweet = {attrs ->
        boolean reTweeted =  attrs.isRetweetedByMe
        boolean validUser = attrs.userAuthenticated?:''

        out << "<div>"
        out << attrs.val
        out << "</div>"
        if(!reTweeted && validUser){
            out << "<div style = \"text-align:right\">"
            out << "<a href=\"/dashBoard/reTweet/${attrs.id}\"><img style = \"width: 20px; height: 15px;\" src=\"../assets/reTweet.jpeg\" title=\"Retweet Status\" /></a>"
            out << "</div>"
        }
    }

    def userImg={attrs ->
        if (springSecurityService.isLoggedIn()) {
            User user = springSecurityService.currentUser as User
            out << "<img src=\"${user.picture}\" class=\"imgCustom\" />"
        }
    }



}
