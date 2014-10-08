<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div class="container-fluid" id='loginForm'>
    <div class='inner '>

        <div class='head'><h3><g:message code="springSecurity.login.header"/></h3></div>

        <g:if test='${flash.message}'>
            <div class='login_message'>${flash.message}</div>
        </g:if>

        <%--<g:form controller="login" method='POST' id='loginForm' class='custom-form' autocomplete='off'>--%>
            <div class="custom-control-label span8 ">

                <div class="span6">
                    <p class="pull-left">
                        <oauth:connect provider="google" id="google-connect-link"><input type="button" value="IntelliGrape" class="btn btn-info span3" /></oauth:connect>
                    </p>

                </div>
            </div>
        <%--</g:form>--%>
    </div>
</div>
<script type='text/javascript'>
    <!--
    //(function () {
    //    document.forms['loginForm'].elements['j_username'].focus();
    //})();
    // -->
</script>
</body>
</html>
