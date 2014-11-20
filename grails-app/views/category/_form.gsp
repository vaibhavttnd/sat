<%@ page import="com.tweetAmp.Category" %>
<div class="form-group">
    <label class="control-label hidden-phone" for="name">
        <g:message code="category.name.label" default="Name"/>

    </label>

    <g:textField class="form-control required" name="name" required="" value="${categoryInstance?.name}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="description">
        <g:message code="category.description.label" default="Desc"/>

    </label>

    <g:textField class="form-control" name="description" value="${categoryInstance?.description}"/>

</div>

<div class="form-group">
    <label class="control-label hidden-phone" for="users">
        <g:message code="category.users.label" default="Users"/>

    </label>

    <g:select name="users" from="${com.tweetAmp.User.list()}" multiple="multiple" optionKey="id" size="5"
              value="${categoryInstance?.users*.id}" class="many-to-many form-control" />

</div>

