<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>TweetAmp</title>
    <script type="text/javascript">
        var retweetUrl = "${createLink(controller: 'dashBoard' ,action: 'reTweetForm')}";
    </script>
    <asset:javascript src="/plugins/chosen/chosen.jquery.js"/>
    <asset:stylesheet src="/plugins/chosen/chosen.css"/>
</head>

<body>
<sec:ifAllGranted roles="ROLE_ADMIN">
    <g:render template="adminDashboard"/>
</sec:ifAllGranted>
<div class="${SpringSecurityUtils.ifNotGranted('ROLE_ADMIN') ? 'standard-user-welcome' : 'right-detail'}">
    <g:if test="${accessToken?.getScreenName()}">
        <p class="margin-b20">Thanks for registering with us.</p>

        <p class="margin-b20">
            <asset:image src="twitter.png"
                         style="width: 30px; height: 25px;"/> @${accessToken?.getScreenName()}
        </p>
        %{--<ul class="nav">--}%
            %{--<g:link controller="dashBoard" action="revokeApp" class="btn btn-transparent"--}%
                    %{--onclick="return confirm('Are you sure you want to revoke your twitter access?')">Revoke Access</g:link>--}%
        %{--</ul>--}%
    </g:if>
</div>
</body>

</html>