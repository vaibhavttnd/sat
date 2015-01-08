package com.tweetAmp

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth.OAuthLoginException
import grails.plugin.springsecurity.oauth.OAuthToken
import grails.plugin.springsecurity.userdetails.GrailsUser
import org.apache.commons.lang.RandomStringUtils
import org.scribe.model.Token
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.ModelAndView

class SpringSecurityOAuthController {

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'

    def oauthService
    def springSecurityService
    def springSecurityOAuthService
    def authenticationManager
    def rememberMeServices
    def userService

    /**
     * This can be used as a callback for a successful OAuth authentication
     * attempt.
     * It logs the associated user in if he or she has an internal
     * Spring Security account and redirects to <tt>targetUri</tt> (provided as a URL
     * parameter or in the session). Otherwise it redirects to a URL for
     * linking OAuth identities to Spring Security accounts. The application must implement
     * the page and provide the associated URL via the <tt>oauth.registration.askToLinkOrCreateAccountUri</tt>
     * configuration setting.
     *
     * "/oauth/$provider/success"(controller: "springSecurityOAuth", action: "onSuccess")
     */
    def onSuccess(String provider) {
        // Validate the 'provider' URL. Any errors here are either misconfiguration
        // or web crawlers (or malicious users).
        provider = 'twitter'

        if (!provider) {
            log.warn "The Spring Security OAuth callback URL must include the 'provider' URL parameter"
            throw new OAuthLoginException("The Spring Security OAuth callback URL must include the 'provider' URL parameter")
        }

        def sessionKey = oauthService.findSessionKeyForAccessToken(provider)
        if (!session[sessionKey]) {
            log.warn "No OAuth token in the session for provider '${provider}'"
            throw new OAuthLoginException("Authentication error for provider '${provider}'")
        }
        // Create the relevant authentication token and attempt to log in.
        OAuthToken oAuthToken = springSecurityOAuthService.createAuthToken(provider, session[sessionKey])

        if (oAuthToken.principal instanceof GrailsUser) {
            authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
        } else {
            // This OAuth account hasn't been registered against an internal
            // account yet. Give the oAuthID the opportunity to create a new
            // internal account or link to an existing one.
            session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken

            def redirectUrl = springSecurityOAuthService.getAskToLinkOrCreateAccountUri()
            println redirectUrl
            if (!redirectUrl) {
                log.warn "grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri configuration option must be set"
                throw new OAuthLoginException('Internal error')
            }

            println "Redirecting to askToLinkOrCreateAccountUri: ${redirectUrl}"
            redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
        }
    }

    def onFailure(String provider) {
        // TODO: put it in i18n messages file
        //flash.message = "book.delete.message"
        //flash.args = ["The Stand"]
        flash.default = "Error authenticating with ${provider}"
        log.warn "Error authenticating with external provider ${provider}"
        authenticateAndRedirect(null, getDefaultTargetUrl())
    }

    def askToLinkOrCreateAccount() {

        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]
        assert oAuthToken, "There is no auth token in the session!"

        if (!oAuthToken) {
            log.warn "askToLinkOrCreateAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }

        User userInstance = null
        Token twitterAccessToken = (Token) session[oauthService.findSessionKeyForAccessToken('twitter')]
        Map<String, String> twitterDetails = userService.tokenizeParameters(twitterAccessToken.rawResponse)
        if (springSecurityService.loggedIn) {
            userInstance = springSecurityService.getCurrentUser() as User
        } else {
            userInstance = User.findByUsername(twitterDetails.screen_name)
            if (!userInstance) {
                userInstance = userService.registerNewUser(oAuthToken, twitterAccessToken)
            }
        }

        if (!userInstance.twitterUser.accessToken || !userInstance.twitterUser.accessTokenSecret) {
            userService.saveTwitterUser(userInstance.twitterUser, twitterDetails)
            userInstance.username=twitterDetails.screen_name
        }

        if (userInstance.validate() && userInstance.save()) {
            oAuthToken = springSecurityOAuthService.updateOAuthToken(oAuthToken, userInstance)
            authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
            return
        }

      // return new ModelAndView("/springSecurityOAuth/askToLinkOrCreateAccount")
    }

    /**
     * Associates an OAuthID with an existing account. Needs the user's password to ensure
     * that the user owns that account, and authenticates to verify before linking.
     */
    @SuppressWarnings('BooleanMethodReturnsNull')
    def linkAccount(OAuthLinkAccountCommand command) {
        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]
        if (!oAuthToken) {
            log.warn "linkAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }
        if (request.post) {
            if (!authenticationIsValid(command.username, command.password)) {
                log.info "Authentication error for use ${command.username}"
                command.errors.rejectValue("username", "OAuthLinkAccountCommand.authentication.error")
                render view: 'askToLinkOrCreateAccount', model: [linkAccountCommand: command]
                return
            }
            def commandValid = command.validate()
            def User = springSecurityOAuthService.lookupUserClass()
            boolean linked = commandValid && User.withTransaction { status ->
                //def user = User.findByUsernameAndPassword(command.username, springSecurityService.encodePassword(command.password))
                def user = User.findByUsername(command.username)
                if (user) {
                    user.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)
                    if (user.validate() && user.save()) {
                        oAuthToken = springSecurityOAuthService.updateOAuthToken(oAuthToken, user)
                        return true
                    } else {
                        return false
                    }
                } else {
                    command.errors.rejectValue("username", "OAuthLinkAccountCommand.username.not.exists")
                }
                status.setRollbackOnly()
                return false
            }
            if (linked) {
                authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                return
            }
        }
        render view: 'askToLinkOrCreateAccount', model: [linkAccountCommand: command]
        return
    }

    private boolean authenticationIsValid(String username, String password) {
        boolean valid = true
        try {
            authenticationManager.authenticate new UsernamePasswordAuthenticationToken(username, password)
        } catch (AuthenticationException e) {
            valid = false
        }
        return valid
    }

    def createAccount(OAuthCreateAccountCommand command) {
        OAuthToken oAuthToken = session[SPRING_SECURITY_OAUTH_TOKEN]
        if (!oAuthToken) {
            log.warn "createAccount: OAuthToken not found in session"
            throw new OAuthLoginException('Authentication error')
        }
        if (request.post) {
            if (!springSecurityService.loggedIn) {
                def config = SpringSecurityUtils.securityConfig
                def commandValid = command.validate()
                def User = springSecurityOAuthService.lookupUserClass()
                boolean created = commandValid && User.withTransaction { status ->
                    def user = springSecurityOAuthService.lookupUserClass().newInstance()
                    //User user = new User(username: command.username, password: command.password1, enabled: true)
                    user.username = command.username
                    user.password = command.password1
                    user.enabled = true
                    user.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)
                    // updateUser(user, oAuthToken)
                    if (!user.validate() || !user.save()) {
                        status.setRollbackOnly()
                        false
                    }
                    def UserRole = springSecurityOAuthService.lookupUserRoleClass()
                    def Role = springSecurityOAuthService.lookupRoleClass()
                    def roles = springSecurityOAuthService.getRoleNames()
                    for (roleName in roles) {
                        UserRole.create user, Role.findByAuthority(roleName)
                    }
                    oAuthToken = springSecurityOAuthService.updateOAuthToken(oAuthToken, user)
                    true
                }
                if (created) {
                    authenticateAndRedirect(oAuthToken, getDefaultTargetUrl())
                    return
                }
            }
        }
        render view: 'askToLinkOrCreateAccount', model: [createAccountCommand: command]
    }

    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl ?: defaultUrlOnNull)]
        }
        return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
    }

    protected void authenticateAndRedirect(OAuthToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken
        String rememberMeParameterName = getRememberMeParameterName()
        if ((oAuthToken) && rememberMeParameterName && params[rememberMeParameterName]) {
            rememberMeServices.loginSuccess(request, response, SecurityContextHolder.context.authentication)
        }
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

    private String getRememberMeParameterName() {
        def conf = SpringSecurityUtils.securityConfig
        return conf.rememberMe.parameter
    }

}

class OAuthCreateAccountCommand {

    def springSecurityOAuthService

    String username
    String password1
    String password2

    static constraints = {
        username blank: false, minSize: 3, validator: { String username, command ->
            if (command.springSecurityOAuthService.usernameTaken(username)) {
                return 'OAuthCreateAccountCommand.username.error.unique'
            }
        }
        password1 blank: false, minSize: 8, maxSize: 64, validator: { password1, command ->
            if (command.username && command.username == password1) {
                return 'OAuthCreateAccountCommand.password.error.username'
            }

            if (password1 && password1.length() >= 8 && password1.length() <= 64 &&
                    (!password1.matches('^.*\\p{Alpha}.*$') ||
                            !password1.matches('^.*\\p{Digit}.*$') ||
                            !password1.matches('^.*[!@#$%^&].*$'))) {
                return 'OAuthCreateAccountCommand.password.error.strength'
            }
        }
        password2 nullable: true, blank: true, validator: { password2, command ->
            if (command.password1 != password2) {
                return 'OAuthCreateAccountCommand.password.error.mismatch'
            }
        }
    }
}

class OAuthLinkAccountCommand {

    String username
    String password

    static constraints = {
        username blank: false
        password blank: false
    }

}

