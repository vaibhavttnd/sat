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
            out << user.username
        }
    }

    def tweet = { attrs ->
        Status status = attrs.status
        boolean validUser = attrs.userAuthenticated ?: ''
        out << render(template: "/dashBoard/tweet", model: [status: status, validUser: validUser])
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
