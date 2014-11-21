<%@ page import="com.tweetAmp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        Show Category
        <ul class="navbar-top-links pull-right">
            <li>
                <g:link action="create" class="text-white"><i class="icon-plus-sign"></i>&nbsp;<g:message
                        code="default.new.label"
                        args="[entityName]"/></g:link>
            </li>
            <li>
                <g:link action="list" class="text-white"><i class="icon-list"></i>&nbsp;<g:message
                        code="default.list.label"
                        args="[entityName]"/></g:link>
            </li>
        </ul>
    </div>
    <table class="table table-striped table-hover table-bordered margin-t10">

        <tr>
            <g:if test="${categoryInstance?.name || false}">
                <td><strong><g:message code="category.name.label" default="Name"/></strong></td>

                <td><g:fieldValue bean="${categoryInstance}" field="name"/></td>

            </g:if>
        </tr>

        <tr>
            <g:if test="${categoryInstance?.description || false}">
                <td><strong><g:message code="category.description.label" default="Description"/></strong></td>

                <td><g:fieldValue bean="${categoryInstance}" field="description"/></td>

            </g:if>
        </tr>

        <tr>
            <g:if test="${categoryInstance?.users || false}">
                <td><strong><g:message code="category.users.label" default="Users"/></strong></td>

                <td>
                    <ul class="unstyled">
                        <g:each in="${categoryInstance.users}" var="u">
                            <li><g:link controller="user" action="show" id="${u.id}">${u?.name}</g:link></li>
                        </g:each>
                    </ul>
                </td>

            </g:if>
        </tr>

    </table>
    <g:form class="margin-20">
        <g:hiddenField name="id" value="${categoryInstance?.id}"/>
        <g:link class="btn btn-info" action="create"
                id="${categoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit"/>&nbsp;<i
                class="icon-edit icon-white"></i></g:link>
        <g:actionSubmit class="btn btn-danger" action="delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </g:form>
</div>
</body>
</html>
