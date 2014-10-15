<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="container top-padding-10">
    <div class="outer-left no-left-margin" style="padding:20px 0 0 40px;">
        <div class="content">
            <table class="table table-striped table-hover table-condensed table-bordered">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Twitter ScreenName</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${userInstanceList}" status="i" var="userInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td>${userInstance.name}</td>
                        <td>${userInstance.email}</td>
                        <td><t:showScreenNames val="${userInstance.twitterCredentials?.screenName ?: "NA"}"/></td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
