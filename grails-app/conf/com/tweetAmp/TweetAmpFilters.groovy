package com.tweetAmp

class TweetAmpFilters {

    def filters = {
        all(controller: 'assets', action: '*', invert: true) {
            before = {
                println "Logs : ${params}"
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
