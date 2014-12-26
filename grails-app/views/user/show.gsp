<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        Show User
        <ul class="navbar-top-links pull-right">
            <li>
                <g:link action="list" class="text-white"><i class="icon-list"></i>&nbsp;<g:message
                        code="default.list.label"
                        args="[entityName]"/></g:link>
            </li>
        </ul>
    </div>

    <g:render template="showUser"  model="[userInstance: userInstance]"/>

    <g:form class="margin-20">
        <g:hiddenField name="id" value="${userInstance?.id}"/>
        <g:link class="btn btn-info" action="edit"
                id="${userInstance?.id}"><g:message code="default.button.edit.label" default="Edit"/>&nbsp;<i
                class="icon-edit icon-white"></i></g:link>
        <g:actionSubmit class="btn btn-danger" action="delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </g:form>

</div>
</body>
</html>
