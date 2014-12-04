package com.tweetAmp

import grails.async.DelegateAsync

class AsyncTwitterService {
    @DelegateAsync TwitterService twitterService
}
