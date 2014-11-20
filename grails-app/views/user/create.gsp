<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
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

<g:form class="form-horizontal" action="save">
    <legend><g:message code="default.create.label" args="[entityName]"/></legend>
    <g:if test="${userInstance?.id}">
        <g:hiddenField name="id" value="${userInstance?.id}"/>
        <g:hiddenField name="version" value="${userInstance?.version}"/>
    </g:if>
    <g:render template="form"/>
    <div class="control-group">
        <div class="controls">
            <g:if test="${userInstance?.id}">
                <g:actionSubmit class="btn btn-info" action="save"
                                value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                <g:actionSubmit class="btn btn-danger" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                formnovalidate=""
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </g:if><g:else>
                <g:submitButton name="create" class="btn btn-info"
                                value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            </g:else>
        </div>
    </div>
</g:form>
</body>
</html>
