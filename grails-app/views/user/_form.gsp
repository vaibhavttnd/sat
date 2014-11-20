<%@ page import="com.tweetAmp.User" %>
<div class="control-group">
    <label class="control-label hidden-phone" for="username">
        <g:message code="user.username.label" default="Username"/>

    </label>

    <div class="controls">
        <g:textField name="username" required="" value="${userInstance?.username}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="email">
        <g:message code="user.email.label" default="Email"/>

    </label>

    <div class="controls">
        <g:textField name="email" required="" value="${userInstance?.email}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="password">
        <g:message code="user.password.label" default="Password"/>

    </label>

    <div class="controls">
        <g:textField name="password" required="" value="${userInstance?.password}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="twitterCredential">
        <g:message code="user.twitterCredential.label" default="Twitter Credential"/>

    </label>

    <div class="controls">
        <g:select id="twitterCredential" name="twitterCredential.id" from="${com.tweetAmp.TwitterCredential.list()}"
                  optionKey="id" value="${userInstance?.twitterCredential?.id}" class="many-to-one"
                  noSelection="['null': '']"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="accountExpired">
        <g:message code="user.accountExpired.label" default="Account Expired"/>

    </label>

    <div class="controls">
        <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="accountLocked">
        <g:message code="user.accountLocked.label" default="Account Locked"/>

    </label>

    <div class="controls">
        <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="categories">
        <g:message code="user.categories.label" default="Categories"/>

    </label>

    <div class="controls">

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="enabled">
        <g:message code="user.enabled.label" default="Enabled"/>

    </label>

    <div class="controls">
        <g:checkBox name="enabled" value="${userInstance?.enabled}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="googleUsers">
        <g:message code="user.googleUsers.label" default="Google Users"/>

    </label>

    <div class="controls">

        <ul class="one-to-many">
            <g:each in="${userInstance?.googleUsers ?}" var="g">
                <li><g:link controller="googleUser" action="show" id="${g.id}">${g?.encodeAsHTML()}</g:link></li>
            </g:each>
            <li class="add">
                <g:link controller="googleUser" action="create"
                        params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'googleUser.label', default: 'GoogleUser')])}</g:link>
            </li>
        </ul>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="name">
        <g:message code="user.name.label" default="Name"/>

    </label>

    <div class="controls">
        <g:textField name="name" required="" value="${userInstance?.name}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="passwordExpired">
        <g:message code="user.passwordExpired.label" default="Password Expired"/>

    </label>

    <div class="controls">
        <g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}"/>

    </div>
</div>

<div class="control-group">
    <label class="control-label hidden-phone" for="picture">
        <g:message code="user.picture.label" default="Picture"/>

    </label>

    <div class="controls">
        <g:textField name="picture" required="" value="${userInstance?.picture}"/>

    </div>
</div>

