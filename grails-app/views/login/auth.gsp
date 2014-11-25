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
                        <oauth:connect provider="google" id="google-connect-link" class="btn btn-danger btn-block">
                            <i class="fa fa-fw fa-google-plus"></i>Login with Google
                        </oauth:connect>

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>
