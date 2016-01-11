<%-- 
    Document   : login
    Created on : 09.01.2016, 4:04:27
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="servlet" method="POST">
            Name: <input type="text" name="name"><br>
            Password: <input type="text" name="password"><br>
            <input type="submit" name="login" value="login">
        </form>
    </body>
</html>
