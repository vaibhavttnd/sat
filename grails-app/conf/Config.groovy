// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = "com.intelligrape.tweetAmp" // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      form         : 'application/x-www-form-urlencoded',
                      html         : ['text/html', 'application/xhtml+xml'],
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://localhost:${System.getProperty('server.port', '8080')}"
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
        grails.serverURL = "http://tweetamp.qa3.intelligrape.net"
    }
}

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    //  debug 'org.springframework.security'

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
}

grails.google.api.url = "https://www.googleapis.com/oauth2/v1/userinfo"
def baseURL = grails.serverURL ?: ""
grails.twitterCallbackUrl = grails.serverURL ?: ""

oauth {
    providers {
        google {
            api = org.grails.plugin.springsecurity.oauth.GoogleApi20
            key = '283780058333-u1n59cltp31lf204tog9kgsl0j01880d.apps.googleusercontent.com'
            secret = 'Q8_QR7kgRR-Dq-qRb_4utM2b'
            successUri = '/springSecurityOAuth/onSuccess'
            //failureUri = '/oauth/google/error'
            failureUri = '/'
            callback = "${baseURL}/oauth/google/callback"
            scope = 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email'
        }
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.tweetAmp.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.tweetAmp.UserRole'
grails.plugin.springsecurity.authority.className = 'com.tweetAmp.Role'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/dashBoard/index'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        '/'                      : ['permitAll'],
        '/assets/**'             : ['permitAll'],
        '/**/js/**'              : ['permitAll'],
        '/**/fonts/**'           : ['permitAll'],
        '/fonts/**'              : ['permitAll'],
        '/**/css/**'             : ['permitAll'],
        '/**/images/**'          : ['permitAll'],
        '/**/favicon.ico'        : ['permitAll'],
        '/login/**'              : ['permitAll'],
        '/logout/**'             : ['permitAll'],
        '/oauth/**'              : ['permitAll'],
        '/dashBoard/**'          : ['ROLE_USER', 'ROLE_ADMIN'],
        '/user/**'               : ['ROLE_USER', 'ROLE_ADMIN'],
        '/springSecurityOAuth/**': ['permitAll'],
        '/twitter4j/**'          : ['permitAll'],
        '/console/**'            : ['ROLE_ADMIN'],
        '/category/**'           : ['ROLE_ADMIN'],
        '/plugins/console*/**'   : ['ROLE_ADMIN']
]

// Added by the Spring Security OAuth plugin:
grails.plugin.springsecurity.oauth.active = true
grails.plugin.springsecurity.oauth.domainClass = "com.tweetAmp.GoogleUser"
grails.plugin.springsecurity.oauth.registration.askToLinkOrCreateAccountUri = "/springSecurityOAuth/askToLinkOrCreateAccount"
grails.plugin.springsecurity.logout.postOnly = false

//twiiter settings
twitter4j {
    enableTwitter4jController = false  // To avoid intruders to use controller all together.
    'default' {
        debugEnabled = false
        OAuthConsumerKey = '5JyqR5PajwOc0EK9tHlKMRx2g'
        OAuthConsumerSecret = 'cAshX3H3CtGplMoqjbb8h576DvAXhyXomr57KwltI8RV7cv5g3'
    }
}

//grails.allowedDomain="intelligrape.com"