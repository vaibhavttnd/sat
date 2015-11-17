<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title>Edit Profile</title>
    <asset:javascript src="/plugins/chosen/chosen.jquery.js"/>
    <asset:stylesheet src="/plugins/chosen/chosen.css"/>
</head>

<body>
<g:hasErrors bean="${userInstance}">
    <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <ul class="unstyled">
            <g:eachError bean="${userInstance}" var="error">
                <li><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </div>
</g:hasErrors>

<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        Edit Profile
    </div>

    <div class="container">
        <g:form class="form-horizontal col-sm-8 margin-t10 margin-b10" controller="user" action="updateProfile">
            <g:hiddenField name="id" value="${userInstance?.id}"/>
            <g:hiddenField name="version" value="${userInstance?.version}"/>
            <g:render template="form"/>
            <div class="form-group margin-t10">
                <g:submitButton name="submit" class="btn btn-info"
                                value="${message(code: 'default.button.update.label', default: 'Update')}"/>
            </div>
        </g:form>
    </div>
</div>
</body>
</html>
