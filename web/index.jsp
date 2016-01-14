<%-- 
    Document   : index
    Created on : 08.01.2016, 4:14:17
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
        <%--
        <title>Tulip</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        --%>
    </head>
    <body>
        <jsp:forward page="/servlet" />
        <%--
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option> 
                <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}>Українська</option>
            </select>
        </form>
        <h4><fmt:message key="index.text.guest" /> | <a href="login.jsp"><fmt:message key="index.link.login" /></a></h4>
        <h1><fmt:message key="index.text.welcome" /></h1>
        <h2><a href="${pageContext.request.contextPath}/servlet?menu=1"><fmt:message key="index.link.mainmenu" /></a></h2>
        --%>
    </body>
</html>
