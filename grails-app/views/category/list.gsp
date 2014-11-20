<%@ page import="com.tweetAmp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="panel panel-primary" style="margin-left: 15px;">
    <div class="panel-heading">
        Category : (Total : ${categoryInstanceTotal})
        <ul class="navbar-top-links pull-right">
            <li>
                <g:form controller="user" action="index" method="get" style="margin-top: -4px;">
                    <div class="input-group-sm">
                        <input type="text" class="form-control search-query input-sm" placeholder="Search"
                               value="${params.q}" name="q" id="q">

                    </div>

                </g:form>
            </li>
        </ul>
    </div>
    <table class="table table-striped table-hover table-condensed table-bordered">
        <thead>
        <tr>

            <g:sortableColumn class="" property="name" title="${message(code: 'category.name.label', default: 'Name')}" params="[q: params.q]"/>

            <g:sortableColumn class="hidden-phone" property="description" title="${message(code: 'category.description.label', default: 'Desc')}"   params="[q: params.q]"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
            <tr>

                <td><g:link action="show" id="${categoryInstance.id}">${fieldValue(bean: categoryInstance, field: "name")}</g:link></td>

                <td class="hidden-phone">${fieldValue(bean: categoryInstance, field: "description")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>


    <div class="panel-footer">
        &nbsp;<g:paginate total="${categoryInstanceTotal ?: 0}" params="[q: params.q]"/>
    </div>
</div>
</body>
</html>
