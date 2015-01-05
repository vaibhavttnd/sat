<table class="table table-striped table-hover table-bordered margin-t10">

    <tr>
        <g:if test="${userInstance?.username}">
            <td><strong><g:message code="user.username.label" default="Username"/></strong></td>

            <td><g:fieldValue bean="${userInstance}" field="username"/></td>

        </g:if>
    </tr>

    <tr>
        <td><strong><g:message code="user.email.label" default="Email"/></strong></td>

        <td>
            <g:if test="${userInstance?.email}">
                <g:fieldValue bean="${userInstance}" field="email"/>
            </g:if>
            <g:else>
                -
            </g:else>
        </td>
    </tr>

    <tr>
        <td><strong><g:message code="user.organisation.label" default="Organisation"/></strong></td>

        <td>
            <g:if test="${userInstance?.organisation}">
                <g:fieldValue bean="${userInstance}" field="organisation"/>
            </g:if>
            <g:else>
                -
            </g:else>
        </td>
    </tr>


    <sec:ifAllGranted roles="ROLE_ADMIN, ROLE_EDITOR">
        <tr>
            <g:if test="${userInstance?.twitterUser}">
                <td><strong><g:message code="user.twitterCredential.label" default="Twitter Credential"/></strong></td>

                <td><g:link controller="twitterCredential" action="show"
                            id="${userInstance?.twitterUser?.id}">${userInstance?.twitterUser?.
                            encodeAsHTML()}</g:link></td>

            </g:if>
        </tr>

        <tr>
            <g:if test="${userInstance?.categories}">
                <td><strong><g:message code="user.categories.label" default="Categories"/></strong></td>

                <td><ul class="unstyled"><g:each in="${userInstance.categories}" var="c">
                    <li><g:link controller="category" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                </g:each></ul></td>

            </g:if>
        </tr>

        <tr>
            <td><strong><g:message code="user.role.label" default="Role"/></strong></td>
            <td>
                <ul class="unstyled">
                    <g:each in="${userInstance?.authorities}" var="role">
                        <li>
                            ${role.authority}
                        </li>
                    </g:each>
                </ul>
            </td>
        </tr>

        <tr>
            <g:if test="${userInstance?.accountExpired || true}">
                <td><strong><g:message code="user.accountExpired.label" default="Account Expired"/></strong></td>

                <td><g:formatBoolean boolean="${userInstance?.accountExpired}"/></td>

            </g:if>
        </tr>

        <tr>
            <g:if test="${userInstance?.accountLocked || true}">
                <td><strong><g:message code="user.accountLocked.label" default="Account Locked"/></strong></td>

                <td><g:formatBoolean boolean="${userInstance?.accountLocked}"/></td>

            </g:if>
        </tr>

        <tr>
            <g:if test="${userInstance?.enabled || true}">
                <td><strong><g:message code="user.enabled.label" default="Enabled"/></strong></td>

                <td><g:formatBoolean boolean="${userInstance?.enabled}"/></td>

            </g:if>
        </tr>

        <tr>
            <g:if test="${userInstance?.passwordExpired || true}">
                <td><strong><g:message code="user.passwordExpired.label" default="Password Expired"/></strong></td>

                <td><g:formatBoolean boolean="${userInstance?.passwordExpired}"/></td>

            </g:if>
        </tr>
    </sec:ifAllGranted>
</table>