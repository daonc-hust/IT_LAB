<!DOCTYPE html>
<html lang="en">
<head>
    <title>Authentication Service</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<form method="POST" action="/login?redirect=${RequestParameters.redirect!}">
    <h2 style="color: cornflowerblue;text-align: center">Log in</h2>
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
        <div class="form-group">
            <input name="username" type="text" placeholder="Username" class="form-control input-lg"
                   autofocus="true"/>
        </div>

        <div class="form-group">
            <input name="password" type="password" placeholder="Password" class="form-control input-lg"/>
        </div>
        <div style="color: red">${error!}</div>
        <br/>
        <div style="margin-left: 290px">
            <button class="btn btn-primary" type="submit">Log In</button>
        </div>
    </div>


</form>

</body>
</html>