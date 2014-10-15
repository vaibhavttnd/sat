<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>TweetAmp</title>
</head>

<body>

<div class="content">
    <div class="col-md-8">
        <div class="panel panel-primary">
            <div class="panel-heading">
                IG Post
            </div>

            <div class="panel-body">
                <table class="table table-striped table-hover table-condensed table-bordered">
                    <tbody>
                    <g:if test="${statusUpdates}">
                        <g:each in="${statusUpdates}" var="statusUpdate" status="i">
                            <tr class="${(i % 2) == 0 ? 'alternate' : ''}">
                                <td><t:tweet statusUpdate="${statusUpdate}" userAuthenticated="${accessToken}"/></td>
                            </tr>
                        </g:each>

                    </g:if>
                    <g:else>
                        <tr>
                            <td>No Tweets Found
                            </td>
                        </tr>
                    </g:else>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <div class="col-md-4">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Accounts
            </div>

            <div class="panel-body">
                <g:if test="${accessToken?.getScreenName()}">
                    <asset:image src="twitterIcon.jpeg" style="width: 30px; height: 25px;"/> @${accessToken?.getScreenName()}
                    <ul class="nav pull-right">
                        <g:link controller="dashBoard" action="revokeApp" class="btn btn-danger" onclick="return confirm('Are you sure you want to revoke your twitter access?')">Revoke Access</g:link>
                    </ul>
                </g:if>
                <g:else>
                    <g:link controller="dashBoard" action="signInTwitter">
                        <img title="Sign in with Twitter" alt="Sign in with Twitter"
                             src="https://g.twimg.com/dev/sites/default/files/images_documentation/sign-in-with-twitter-gray.png">
                    </g:link>
                </g:else>
            </div>
        </div>

    </div>
</div>

</body>

</html>