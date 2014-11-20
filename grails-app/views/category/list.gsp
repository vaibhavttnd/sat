
<%@ page import="com.tweetAmp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
    <div class="navbar">
        <div class="navbar-inner-crud">
            <a class="brand" href="#"><g:message code="default.list.label" args="[entityName]"/></a>
            <ul class="nav pull-right">
                <li><g:link action="create"><i class="icon-plus-sign"></i>&nbsp;<g:message code="default.new.label" args="[entityName]"/></g:link></li>
            </ul>
        </div>
    </div>
    <table class="table table-bordered table-striped table-hover table-crud">
        <thead>
            <tr>
                
                <g:sortableColumn class="" property="name" title="${message(code: 'category.name.label', default: 'Name')}"/>
                
                <g:sortableColumn class="hidden-phone" property="description" title="${message(code: 'category.description.label', default: 'Desc')}"/>
                
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

    <div class="pagination pagination-right">
        <g:paginate total="${categoryInstanceTotal}"/>
    </div>
</body>
</html>
