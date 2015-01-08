<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title>My Profile</title>
</head>

<body>
<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        Profile
    </div>

    <g:render template="showUser" model="[userInstance: userInstance]"/>

    <g:form class="">
        <g:hiddenField name="id" value="${userInstance?.id}"/>
        <g:link class="btn btn-info pull-right margin-t20 margin-l10" action="editProfile">Edit Profile&nbsp;
            <i class="icon-edit icon-white"></i>
        </g:link>
        <g:if test="${userInstance.twitterUser.accessToken}">
            <g:link controller="dashBoard" action="revokeApp" class="btn btn-info pull-right margin-t20 margin-l10"
                    onclick="return confirm('Are you sure you want to revoke your twitter access?')">Revoke Access</g:link>
        </g:if>
    </g:form>

</div>
</body>
</html>
