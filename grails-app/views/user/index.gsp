<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        User : (Total : ${userInstanceCount})
        <ul class="navbar-top-links pull-right">
            <li>
                <g:form controller="user" action="index" method="get">
                    <div class="input-group-sm">
                        <input type="text" class="form-control search-query input-sm" placeholder="Search" value="${params.q}" name="q" id="q">

                    </div>

                </g:form>
            </li>
        </ul>
    </div>


    <table class="table table-striped table-hover table-condensed table-bordered">
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'user.name.label', default: 'Name')}" params="[q: params.q]"/>
            <g:sortableColumn property="email" title="${message(code: 'user.email.label', default: 'Email')}" params="[q: params.q]"/>
            <g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled')}" params="[q: params.q]"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${users}" status="i" var="user">
            <tr class="${(i % 2) == 0 ? 'alternate' : ''}">

                <td>${fieldValue(bean: user, field: "name")}</td>

                <td>${fieldValue(bean: user, field: "email")}</td>

                <td><g:formatBoolean boolean="${user.enabled}"/></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="panel-footer">
        &nbsp;<g:paginate total="${userInstanceCount ?: 0}" params="[q: params.q]"/>
    </div>

</div>
</body>
</html>
