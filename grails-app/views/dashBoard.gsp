<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>TweetAmp</title>
    <script type="text/javascript">
        function revokeAccess(){
            window.location.href="../dashBoard/revokeApp"
        }
    </script>
</head>

<body>
<div class = "container top-padding-10">
    <div class="outer-left no-left-margin">

    <div class="content">
    <table class="table table-striped table-hover table-condensed table-bordered">
        <thead>
        <tr>
            <th colspan="4" class="head-text">IG Posts</th>
        </tr>

        </thead>

        <tbody>
        <g:if test="${statusUpdates}">
            <g:each in="${statusUpdates}" var="statusUpdate" status="i">
                <tr class="${(i % 2) == 0 ? 'alternate' : null}">
                    <td><t:tweet statusUpdate = "${statusUpdate}" userAuthenticated="${accessToken}"/></td>
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

    <div class="outer-right">
        <g:if test="${accessToken?.getScreenName()}">
            <img src="../assets/twitterIcon.jpeg" style = "width: 30px; height: 25px;"> @${accessToken?.getScreenName()}
            <ul class="nav pull-right">
                <input type="button" value="Revoke Access" onclick="javascript:revokeAccess()" class="revokeButton"/>
            </ul>
        </g:if>
        <g:else>
            <a href="/dashBoard/signInTwitter"><img title="Sign in with Twitter" alt="Sign in with Twitter" src="https://g.twimg.com/dev/sites/default/files/images_documentation/sign-in-with-twitter-gray.png"></a>
        </g:else>
    </div>

</div>
</body>



</html>