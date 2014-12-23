package com.tweetAmp

class User {

    transient springSecurityService

    String username
    String password
    String email

    TwitterUser twitterUser

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static transients = ['springSecurityService']

    static hasMany = [categories: Category]

    static belongsTo = [Category]

    static constraints = {
        username blank: false, nullable: true
        email blank: false, unique: true, nullable: true
        password blank: false
        twitterUser nullable: false
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role }
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }


    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }
}
