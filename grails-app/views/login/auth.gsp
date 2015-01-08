<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
<div class="col-md-2 col-md-offset-5">

    <div class="form-signin mg-btm">

        <div class="form-group">
            <div class="social-box">
                <div class="row mg-btm">
                    <div class="col-md-12 margin-t45">
                        <oauth:connect provider="twitter" id="twitter-connect-link" class="btn btn-transparent-blue btn-block">
                            <i class="fa fa-fw fa-twitter"></i> &nbsp;Login with Twitter
                        </oauth:connect>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>
