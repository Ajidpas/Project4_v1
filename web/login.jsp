<%-- 
    Document   : login
    Created on : 09.01.2016, 4:04:27
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="servlet" method="get">
            <select name="language" onchange="submit()"  >
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option> 
                <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}>Українська</option>
                <input type="hidden" name="select" value="true">
            </select>
        </form>
        <form action="servlet" method="POST">
            <fmt:message key="login.text.name" />: <input type="text" name="name"><br>
            <fmt:message key="login.text.password" />: <input type="text" name="password"><br>
            <input type="submit" name="login" value=<fmt:message key="login.button.login" />>
        </form>
    </body>
</html>
