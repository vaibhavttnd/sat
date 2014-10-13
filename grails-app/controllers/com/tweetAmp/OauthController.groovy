package com.tweetAmp

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.scribe.model.Token
import org.scribe.model.Verifier
import uk.co.desirableobjects.oauth.scribe.OauthProvider
import uk.co.desirableobjects.oauth.scribe.OauthService
import uk.co.desirableobjects.oauth.scribe.SupportedOauthVersion
import uk.co.desirableobjects.oauth.scribe.exception.MissingRequestTokenException
import uk.co.desirableobjects.oauth.scribe.holder.RedirectHolder

class OauthController {

    private static final Token EMPTY_TOKEN = new Token('', '')

    OauthService oauthService
    def grailsApplication

    def callback() {

        String providerName = params.provider
        OauthProvider provider = oauthService.findProviderConfiguration(providerName)

        Verifier verifier = extractVerifier(provider, params)

        if (!verifier) {
            redirect(uri: provider.failureUri)
            return
        }

        Token requestToken = provider.oauthVersion == SupportedOauthVersion.TWO ?
                new Token(params?.code, "") :
                (Token) session[oauthService.findSessionKeyForRequestToken(providerName)]

        if (!requestToken) {
            throw new MissingRequestTokenException(providerName)
        }

        Token accessToken

        try {
            accessToken = oauthService.getAccessToken(providerName, requestToken, verifier)
        } catch(OAuthException){
            log.error("Cannot authenticate with oauth")
            return redirect(uri: provider.failureUri)
        }

        session[oauthService.findSessionKeyForAccessToken(providerName)] = accessToken
        session.removeAttribute(oauthService.findSessionKeyForRequestToken(providerName))

        return redirect(uri: provider.successUri)

    }

    private Verifier extractVerifier(OauthProvider provider, GrailsParameterMap params) {

        String verifierKey = determineVerifierKey(provider)

        if (!params[verifierKey]) {
            log.error("Cannot authenticate with oauth: Could not find oauth verifier in ${params}.")
            return null
        }

        String verification = params[verifierKey]
        return new Verifier(verification)

    }

    private String determineVerifierKey(OauthProvider provider) {

        return SupportedOauthVersion.TWO == provider.oauthVersion ? 'code' : 'oauth_verifier'

    }

    def authenticate() {

        String providerName = params.provider
        OauthProvider provider = oauthService.findProviderConfiguration(providerName)

        Token requestToken = EMPTY_TOKEN
        if (provider.oauthVersion == SupportedOauthVersion.ONE) {
            requestToken = provider.service.requestToken
        }

        session[oauthService.findSessionKeyForRequestToken(providerName)] = requestToken
        String url = oauthService.getAuthorizationUrl(providerName, requestToken)

        /* This code restricts the access to tweetAmp, uncomment it if you wish to restrict it for particular domains &
        correspondingly make entries in the Config.groovy
        url += "&hd="+grailsApplication.config.grails.allowedDomain*/

        RedirectHolder.setUri(params.redirectUrl)
        return redirect(url: url)

    }
}
