<%@ page import="com.tweetAmp.User" %>
<div class="form-group">
    <label class="control-label hidden-phone" for="name">
        <g:message code="user.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required" name="name" required="" value="${userInstance?.name}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="username">
        <g:message code="user.username.label" default="Username"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required" name="username" required="" value="${userInstance?.username}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="email">
        <g:message code="user.email.label" default="Email"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required" name="email" required="" value="${userInstance?.email}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="password">
        <g:message code="user.password.label" default="Password"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required" name="password" required="" value=""/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="accountExpired">
        <g:message code="user.accountExpired.label" default="Account Expired"/>
    </label>

    <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="accountLocked">
        <g:message code="user.accountLocked.label" default="Account Locked"/>
    </label>

    <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="enabled">
        <g:message code="user.enabled.label" default="Enabled"/>
    </label>

    <g:checkBox name="enabled" value="${userInstance?.enabled}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="passwordExpired">
        <g:message code="user.passwordExpired.label" default="Password Expired"/>
    </label>

    <g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}"/>

</div>

