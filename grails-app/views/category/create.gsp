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

<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        <g:message code="default.create.label" args="[entityName]"/>
        <ul class="navbar-top-links pull-right">
            <li>
                <g:link action="list" class="text-white"><i class="icon-list"></i>&nbsp;<g:message code="default.list.label"
                                                                                                   args="[entityName]"/></g:link>
            </li>
        </ul>
    </div>
    <div class="container">
        <g:form class="form-horizontal col-sm-8 margin-t10 margin-b10">
            <g:if test="${categoryInstance?.id}">
                <g:hiddenField name="id" value="${categoryInstance?.id}"/>
                <g:hiddenField name="version" value="${categoryInstance?.version}"/>
            </g:if>
            <g:render template="form"/>
            <div class="form-group margin-t10">
                <g:if test="${categoryInstance?.id}">
                    <g:actionSubmit class="btn btn-info" action="save" value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                    <g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate=""
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </g:if><g:else>
                    <g:submitButton name="create" class="btn btn-info" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
                </g:else>
            </div>
        </g:form>
    </div>
</div>
</body>
</html>
