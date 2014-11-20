<%@ page import="com.tweetAmp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<g:hasErrors bean="${categoryInstance}">
    <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <ul class="unstyled">
            <g:eachError bean="${categoryInstance}" var="error">
                <li><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </div>
</g:hasErrors>

<g:form class="form-horizontal" action="save" >
<legend><g:message code="default.create.label" args="[entityName]"/></legend>
<g:if test="${categoryInstance?.id}">
    <g:hiddenField name="id" value="${categoryInstance?.id}"/>
    <g:hiddenField name="version" value="${categoryInstance?.version}"/>
</g:if>
<g:render template="form"/>
<div class="control-group">
    <div class="controls">
        <g:if test="${categoryInstance?.id}">
            <g:actionSubmit class="btn btn-info" action="save" value="${message(code: 'default.button.update.label', default: 'Update')}"/>
            <g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate=""
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </g:if><g:else>
            <g:submitButton name="create" class="btn btn-info" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </g:else>
    </div>
</div>
</g:form>
</body>
</html>
