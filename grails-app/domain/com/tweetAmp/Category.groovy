package com.tweetAmp

class Category {

    String name
    String desc

    static hasMany = [users: User]

    static constraints = {
        name nullable: false, blank: false
        desc nullable: true
    }

    static mapping = {
        users cascade: 'all-delete-orphan'
    }
}
