<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Users</title>
</head>

<body>
<table class="table table-striped table-hover table-condensed table-bordered">
    <thead>
    <tr>
        <th>Name" default="Created By" /></th>
        <th><g:message code="Email" default="Created By" /></th>
        <th><g:message code="Picture" default="Created By" /></th>
        <th><g:message code="Twitter-UserName" default="Created By" /></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${userInstanceList}" status="i" var="userInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
            <td>${userInstance.name}</td>
            <td>${userInstance.email}</td>
            <td>${userInstance.picture}</td>
            <td>${userInstance.name}</td>
        </tr>
    </g:each>
    </tbody>
</table>
</body>
</html>