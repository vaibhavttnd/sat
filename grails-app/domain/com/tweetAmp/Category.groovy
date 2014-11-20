package com.tweetAmp

class Category {

    String name
    String description

    static hasMany = [users: User]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true
    }

    static mapping = {
        users cascade: 'all-delete-orphan'
    }
}
