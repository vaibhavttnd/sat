<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Tweet Amp"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="https://abs.twimg.com/a/1412623444/images/oauth_application.png" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  		<asset:stylesheet src="bootstrap.css"/>
        <asset:stylesheet src="bootstrap-responsive.css"/>
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="bootstrap.js"/>
        <g:layoutHead/>
	</head>
	<body>

    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <g:link class="brand" controller="dashBoard"
                        action="home">TweetAmp</g:link>



        <sec:ifLoggedIn>
            <div class="nav-collapse collapse navbar-responsive-collapse">
            <ul class="nav pull-right">
                <ul class="nav pull-right">
                    <li><t:userImg/></li>
                    <li class="divider-vertical" style="margin: 0"></li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <label><t:userName/><b class="caret"></b></label>
                        </a>

                        <ul class="dropdown-menu">
                              <sec:ifAnyGranted roles="ROLE_ADMIN">
                                  <li><g:link controller="user">Users</g:link></li>
                              </sec:ifAnyGranted>
                              <li><g:link controller="j_spring_security_logout">Logout</g:link></li>
                        </ul>

                    </li>

                </ul>
            </ul>
            </div>
        </sec:ifLoggedIn>
            </div>
    </div>

                <g:layoutBody/>
	</body>
</html>
