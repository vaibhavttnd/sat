<%@ page import="com.tweetAmp.Role; com.tweetAmp.User; com.tweetAmp.Category" %>

<div class="form-group">
    <label class="control-label hidden-phone" for="username">
        <g:message code="user.username.label" default="Username"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required width-350" name="username" readonly="" required=""
                 value="${userInstance?.username}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="email">
        <g:message code="user.email.label" default="Email"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required width-350" required="" name="email" value="${userInstance?.email}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="email">
        <g:message code="user.organisation.label" default="Organisation"/>
        <span class="required-indicator">*</span>
    </label>

    <g:textField class="form-control required width-350" required="" name="organisation"
                 value="${userInstance?.organisation}"/>

</div>

<sec:ifAnyGranted roles="ROLE_EDITOR, ROLE_ADMIN">
    <div class="form-group">
        <label class="control-label hidden-phone" for="role">
            <g:message code="user.role.label" default="Role"/>
            <span class="required-indicator">*</span>
        </label>

        <g:select name="role" from="${Role.list()}"
                  value="${userInstance?.id ? userInstance?.authorities?.first()?.id : ''}"
                  optionKey="id" optionValue="authority" class="form-control width-350"
                  required=""/>
    </div>


    %{--<div class="form-group">--}%
        %{--<label class="control-label hidden-phone" for="accountExpired">--}%
            %{--<g:message code="user.accountExpired.label" default="Account Expired"/>--}%
        %{--</label>--}%

        %{--<g:checkBox name="accountExpired" value="${userInstance?.accountExpired}"/>--}%

    %{--</div>--}%

    %{--<div class="form-group">--}%
        %{--<label class="control-label hidden-phone" for="accountLocked">--}%
            %{--<g:message code="user.accountLocked.label" default="Account Locked"/>--}%
        %{--</label>--}%

        %{--<g:checkBox name="accountLocked" value="${userInstance?.accountLocked}"/>--}%

    %{--</div>--}%

    %{--<div class="form-group">--}%
        %{--<label class="control-label hidden-phone" for="enabled">--}%
            %{--<g:message code="user.enabled.label" default="Enabled"/>--}%
        %{--</label>--}%

        %{--<g:checkBox name="enabled" value="${userInstance?.enabled}"/>--}%

    %{--</div>--}%

    %{--<div class="form-group">--}%
        %{--<label class="control-label hidden-phone" for="passwordExpired">--}%
            %{--<g:message code="user.passwordExpired.label" default="Password Expired"/>--}%
        %{--</label>--}%

        %{--<g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}"/>--}%

    %{--</div>--}%


</sec:ifAnyGranted>

<div class="form-group user-categories">
    <label class="control-label hidden-phone" for="categories">
        <g:message code="category.users.label" default="Select Categories"/>

    </label>
    <select id="categories" name="categories" data-placeholder="Select categories..."
            class="chosen-select form-control" multiple
            style="width:350px;display:block">
        <g:each in="${Category.list()}" var="category">
            <option value="${category.id}" ${userInstance?.categories ? (userInstance?.categories*.id.contains(category.id) ? 'selected' : '') : ''}>${category.name}</option>
        </g:each>
    </select>
</div>

<script type="text/javascript">
    $("#categories").chosen();
</script>