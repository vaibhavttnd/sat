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
    %{--<g:if test="${accessToken?.getAccessToken()}">--}%
        <p class="margin-b20">Thanks @${accessToken?.getScreenName()} for registering with us.</p>

    <sec:ifAllGranted roles="ROLE_USER">
        <h6 class="choose-category">You can select multiple categories for yourself <g:link controller="user" action="editProfile">here</g:link>. Categories are like Grails, Mobility, Marketing, etc</h6>
    </sec:ifAllGranted>

%{--<ul class="nav">--}%
            %{--<g:link controller="dashBoard" action="revokeApp" class="btn btn-transparent"--}%
                    %{--onclick="return confirm('Are you sure you want to revoke your twitter access?')">Revoke Access</g:link>--}%
        %{--</ul>--}%
    %{--</g:if>--}%
    %{--<g:else>--}%
        %{--<p>Please sign into your twitter Account.</p>--}%
        %{--<div id="sign-in-with-twitter">--}%
            %{--<oauth:connect provider="twitter" id="twitter-connect-link" class="btn btn-primary btn-block">--}%
                %{--<i class="fa fa-fw fa-twitter"></i> &nbsp;Sign in with Twitter--}%
            %{--</oauth:connect>--}%
        %{--</div>--}%
        %{--<g:link controller="dashBoard" action="signInTwitter">--}%
            %{--<img title="Sign in with Twitter" alt="Sign in with Twitter"--}%
                 %{--src="https://g.twimg.com/dev/sites/default/files/images_documentation/sign-in-with-twitter-gray.png">--}%
        %{--</g:link>--}%
    %{--</g:else>--}%
</div>
</body>

</html>