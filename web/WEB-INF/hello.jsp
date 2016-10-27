<%--
  Created by IntelliJ IDEA.
  User: giacomo
  Date: 12/10/16
  Time: 23.22
  To change this template use File | Settings | File Templates.
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <c:redirect url="/hello.htm"/>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html>
  <head>
    <title>Actors and films </title>
  </head>
  <body>
  <h1>This page displays a list of actors and films they interpreted</h1>
  <h2>...maybe</h2>

    <ul>

        <c:forEach items="${actorsAndFilms}" var="item">

           <li> "${item}"</li>
        </c:forEach>

    </ul>







  </body>
</html>
