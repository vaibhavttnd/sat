package com.tweetAmp

import twitter4j.Status

import java.text.SimpleDateFormat

class TweetAmpTagsTagLib {
    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "t"
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");

    /** Dependency injection for springSecurityService. */
    def springSecurityService


    def userName = { attrs ->
        if (springSecurityService.isLoggedIn()) {
            User user = springSecurityService.currentUser as User
            out << user.name
        }
    }

    def tweet = { attrs ->
        Status status = attrs.statusUpdate
        boolean reTweeted = status.isRetweeted()
        boolean validUser = attrs.userAuthenticated ?: ''
        Date createdAt = status.getCreatedAt()
        Status retweetStatus = status.getRetweetedStatus();
        twitter4j.User curRTUser = retweetStatus?.getUser();

        out << "<div style = \"text-align:right\">"
        out << "<span style=\"padding-right:5px;\">"
        if (status.isRetweet())
            out << status.getUser().name + " retweeted : @" + curRTUser?.screenName + " - " + dateFormat.format(createdAt)
        else
            out << "@" + status.getUser().screenName + " - " + dateFormat.format(createdAt)
        out << "</span>"

        if (!reTweeted && validUser)
            out << "<a href=\"/dashBoard/reTweet/${status.getId()}\" ><img style = \"width: 20px; height: 15px;\" src=\"../assets/reTweet.jpeg\" title=\"Retweet Status\" /></a>"
        out << "</div>"

        out << "<div class=\"tweetClass\" >"
        out << status.getText()
        out << "</div>"
    }

    def userImg = { attrs ->
        if (springSecurityService.isLoggedIn()) {
            User user = springSecurityService.currentUser as User
            if (user) {
                out << "<img src='${user.picture}' class='img-responsive img-circle' width='50' height='20'/>"
            }
        }
    }

    def showScreenNames = { attrs ->
        if ("NA".equals(attrs.val))
            out << "NA"
        else {
            List screenNames = attrs.val
            screenNames.each { i ->
                out << i + "\t"
            }
        }
    }

}
