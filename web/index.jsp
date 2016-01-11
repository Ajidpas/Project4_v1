<%-- 
    Document   : index
    Created on : 08.01.2016, 4:14:17
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Tulip</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Welcome to our restaurant Tulip (if statement was added)</h1>
        <a href="login.jsp">Login</a>
        <a href="${pageContext.request.contextPath}/servlet?logout=1">Logout</a>
        <div>
            <form action="servlet">
                Find meal by id : <input type="text" name="mealId">
                <input type="submit" name="findMeal" value="Search">
            </form>
        </div>
    </body>
</html>
