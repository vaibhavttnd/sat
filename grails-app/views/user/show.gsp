<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="navbar">
    <div class="navbar-inner-crud">
        <a class="brand" href="#"><g:message code="default.show.label" args="[entityName]"/></a>
        <ul class="nav pull-right">
            <li><g:link action="create"><i class="icon-plus-sign"></i>&nbsp;<g:message code="default.new.label"
                                                                                       args="[entityName]"/></g:link>
            </li>
            <li><g:link action="list"><i class="icon-list"></i>&nbsp;<g:message code="default.list.label"
                                                                                args="[entityName]"/></g:link></li>
        </ul>
    </div>
</div>
<table class="table table-bordered table-crud">

    <tr>
        <g:if test="${userInstance?.username || false}">
            <td><strong><g:message code="user.username.label" default="Username"/></strong></td>

            <td><g:fieldValue bean="${userInstance}" field="username"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.email || false}">
            <td><strong><g:message code="user.email.label" default="Email"/></strong></td>

            <td><g:fieldValue bean="${userInstance}" field="email"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.password || false}">
            <td><strong><g:message code="user.password.label" default="Password"/></strong></td>

            <td><g:fieldValue bean="${userInstance}" field="password"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.twitterCredential || false}">
            <td><strong><g:message code="user.twitterCredential.label" default="Twitter Credential"/></strong></td>

            <td><g:link controller="twitterCredential" action="show"
                        id="${userInstance?.twitterCredential?.id}">${userInstance?.twitterCredential?.
                        encodeAsHTML()}</g:link></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.accountExpired || true}">
            <td><strong><g:message code="user.accountExpired.label" default="Account Expired"/></strong></td>

            <td><g:formatBoolean boolean="${userInstance?.accountExpired}"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.accountLocked || true}">
            <td><strong><g:message code="user.accountLocked.label" default="Account Locked"/></strong></td>

            <td><g:formatBoolean boolean="${userInstance?.accountLocked}"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.categories || false}">
            <td><strong><g:message code="user.categories.label" default="Categories"/></strong></td>

            <td><ul class="unstyled"><g:each in="${userInstance.categories}" var="c">
                <li><g:link controller="category" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
            </g:each></ul></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.enabled || true}">
            <td><strong><g:message code="user.enabled.label" default="Enabled"/></strong></td>

            <td><g:formatBoolean boolean="${userInstance?.enabled}"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.googleUsers || false}">
            <td><strong><g:message code="user.googleUsers.label" default="Google Users"/></strong></td>

            <td><ul class="unstyled"><g:each in="${userInstance.googleUsers}" var="g">
                <li><g:link controller="googleUser" action="show" id="${g.id}">${g?.encodeAsHTML()}</g:link></li>
            </g:each></ul></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.name || false}">
            <td><strong><g:message code="user.name.label" default="Name"/></strong></td>

            <td><g:fieldValue bean="${userInstance}" field="name"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.passwordExpired || true}">
            <td><strong><g:message code="user.passwordExpired.label" default="Password Expired"/></strong></td>

            <td><g:formatBoolean boolean="${userInstance?.passwordExpired}"/></td>

        </g:if>
    </tr>

    <tr>
        <g:if test="${userInstance?.picture || false}">
            <td><strong><g:message code="user.picture.label" default="Picture"/></strong></td>

            <td><g:fieldValue bean="${userInstance}" field="picture"/></td>

        </g:if>
    </tr>

</table>
<g:form>
    <g:hiddenField name="id" value="${userInstance?.id}"/>
    <g:link class="btn btn-info" action="create"
            id="${userInstance?.id}"><g:message code="default.button.edit.label" default="Edit"/>&nbsp;<i
            class="icon-edit icon-white"></i></g:link>
    <g:actionSubmit class="btn btn-danger" action="delete"
                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
</g:form>
</body>
</html>
