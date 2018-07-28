<!DOCTYPE html>
<html lang="en">
<head>
    <title>Protected Resource Service</title>
</head>
<body>
    <h1 style="color:cornflowerblue;">Website 1</h1>
    <h2 style="color: coral">Hello, ${Request.username!}</h2>
    <a href="/logout" style="color: darkslateblue">Logout</a>
    <br/>
    <a href="http://localhost:8280" style="color: blueviolet;">Go to Website 2</a>
    <br/>
    <a href="test.ftl">Go to Test</a>
</body>
</html>