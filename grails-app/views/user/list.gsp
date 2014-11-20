<%@ page import="com.tweetAmp.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        User : (Total : ${userInstanceTotal})
        <ul class="navbar-top-links pull-right">
            <li class="float-left">
                <g:form controller="user" action="index" method="get" style="margin-top: -4px;">
                    <div class="input-group-sm">
                        <input type="text" class="form-control search-query input-sm" placeholder="Search"
                               value="${params.q}" name="q" id="q">

                    </div>

                </g:form>
            </li>
            <li>
                <g:link action="create" class="text-white"><i class="icon-plus-sign"></i>&nbsp;<g:message
                        code="default.new.label"
                        args="[entityName]"/></g:link>
            </li>
        </ul>
    </div>

    <table class="table table-striped table-hover table-condensed table-bordered">
        <thead>
        <tr>
            <g:sortableColumn property="name" title="${message(code: 'user.name.label', default: 'Name')}"
                              params="[q: params.q]"/>
            <g:sortableColumn property="email" title="${message(code: 'user.email.label', default: 'Email')}"
                              params="[q: params.q]"/>
            <g:sortableColumn property="twitterCredential"
                              title="${message(code: 'user.twitterCredential.label', default: 'Twitter')}"
                              params="[q: params.q]"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${userInstanceList}" status="i" var="user">
            <tr class="${(i % 2) == 0 ? 'alternate' : ''}">

                <td><g:link controller="user" action="show"
                            id="${user.id}">${fieldValue(bean: user, field: "name")}</g:link></td>

                <td>${fieldValue(bean: user, field: "email")}</td>

                <td>${user.twitterCredential?.screenName}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="panel-footer">
        &nbsp;<g:paginate total="${userInstanceTotal ?: 0}" params="[q: params.q]"/>
    </div>
</div>
</body>
</html>
