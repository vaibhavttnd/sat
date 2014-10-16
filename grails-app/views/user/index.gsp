<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

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
</body>
</html>
