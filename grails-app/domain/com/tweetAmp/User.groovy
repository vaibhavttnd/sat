package com.tweetAmp

class User {

    transient springSecurityService

    String username
    String password
    String email
    String picture
    String name
    TwitterCredential twitterCredential
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static transients = ['springSecurityService']

    static hasMany = [googleUsers: GoogleUser, categories: Category]

    static belongsTo = [Category]

    static constraints = {
        username blank: false
        email blank: false, unique: true
        password blank: false
        twitterCredential nullable: true
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
