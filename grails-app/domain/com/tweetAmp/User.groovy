package com.tweetAmp

class User {

	transient springSecurityService

	String username
	String password
    String email
    String picture
    String name
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false
        email blank: false, unique: true
		password blank: false
    }

	static mapping = {
		password column: '`password`'
	}

    static hasMany = [googleUsers: GoogleUser,twitterCredentials:TwitterCredentials]

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
