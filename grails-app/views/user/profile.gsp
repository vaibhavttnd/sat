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
        <g:link class="btn btn-info pull-right margin-t20" action="editProfile">Edit Profile&nbsp;
            <i class="icon-edit icon-white"></i>
        </g:link>
    </g:form>

</div>
</body>
</html>
