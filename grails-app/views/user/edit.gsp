<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
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
        <g:message code="default.edit.label" args="[entityName]"/>
        <ul class="navbar-top-links pull-right">
            <li>
                <g:link action="list" class="text-white"><i class="icon-list"></i>&nbsp;<g:message
                        code="default.list.label"
                        args="[entityName]"/></g:link>
            </li>
        </ul>
    </div>

    <div class="container">
        <g:form class="form-horizontal col-sm-8 margin-t10 margin-b10">
            <g:hiddenField name="id" value="${userInstance?.id}"/>
            <g:hiddenField name="version" value="${userInstance?.version}"/>
            <g:render template="form"/>
            <div class="form-group margin-t10">
                <g:actionSubmit class="btn btn-info" action="update"
                                value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                <g:actionSubmit class="btn btn-danger" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                formnovalidate=""
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </div>
        </g:form>
    </div>
</div>
</body>
</html>
