<%@ page import="com.tweetAmp.User; com.tweetAmp.Category" %>
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
    <label class="control-label hidden-phone" for="userList">
        <g:message code="category.users.label" default="Users"/>

    </label>
    <select id="userList" name="userList" data-placeholder="Select Users..." class="chosen-select form-control" multiple
            style="width:350px" tabindex="4">
        <g:each in="${User.list()}" var="user">
            <option value="${user.id}" ${categoryInstance?.users ? (categoryInstance?.users*.id.contains(user.id) ? 'selected' : '') : ''}>${user.username}</option>
        </g:each>
    </select>
</div>


<script type="text/javascript">
    $("#userList").chosen();
</script>

