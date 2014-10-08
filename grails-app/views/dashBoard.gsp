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
    <div class="span12">
    <div class="outer span2 no-left-margin">

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
                   <td><t:tweet id = "${statusUpdate.getId()}" val="${statusUpdate.getText()}" isRetweetedByMe="${statusUpdate.isRetweeted()}" userAuthenticated="${accessToken}"/></td>
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

    <div class="outer span2">
           <div class = "rightDivInner">
            <p>
                <g:if test="${accessToken?.getScreenName()}">
                    Welcome ${accessToken?.getScreenName()}</br>
                    <input type="button" value="Revoke Access" onclick="javascript:revokeAccess()" class="revokeButton"/>
                </g:if>
                <g:else>
                    <a href="/dashBoard/signInTwitter"><img title="Sign in with Twitter" alt="Sign in with Twitter" src="https://g.twimg.com/dev/sites/default/files/images_documentation/sign-in-with-twitter-gray.png"></a>
                </g:else>
            </p>
            </div>
    </div>
    </div>
</div>
</body>



</html>