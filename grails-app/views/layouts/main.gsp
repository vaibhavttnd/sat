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
    <g:javascript library="jquery" plugin="jquery"/>
    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="sb-admin-2.js"/>
    <asset:javascript src="bootstrap.js"/>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>
</head>

<body>
<div id="wrapper">

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="/">
                <img src="http://abs.twimg.com/a/1412623444/images/oauth_application.png" style="width: 30px;height: 30px"/>
                Tweet Amp
            </a>
        </div>
        <sec:ifLoggedIn>
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-user"></i>  <t:userName/>  <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <g:link controller="user"><i class="fa fa-fw fa-user"></i> Users</g:link>
                        </li>

                        <li>
                            <g:link controller="logout"><i class="fa fa-fw fa-power-off"></i> Log Out</g:link>
                        </li>
                    </ul>
                </li>
            </ul>
        </sec:ifLoggedIn>
    </nav>

    <div class="container-fluid">
        <div class="col-lg-12">
            <div class="row" style="padding-top: 75px">
                <g:if test="${flash.success}">
                    <div class="alert alert-success alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        ${flash.success}
                    </div>
                </g:if>
                <g:if test="${flash.error}">
                    <div class="alert alert-danger alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        ${flash.error}
                    </div>
                </g:if>

                <g:layoutBody/>
            </div>

        </div>

    </div>

    <div class="footer" style="position: fixed; bottom: 0px; width: 100%;">
        <div class="container">
            <p class="text-muted"><span class="pull-right"><span>@Powered by <a href="http://www.intelligrape.com" target="_blank">
                <asset:image src="intelligrape.png"/>
            </a></span></span>

            </p>

            <p>© <a href="https://www.livsports.in" target="_blank">2014 Intelligrape Software Pvt. Ltd. . All Rights Reserved.</a></p>
        </div>
    </div>
</div>
</body>
</html>
