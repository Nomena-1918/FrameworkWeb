<%@ page import="root.classesTest.Emp" %><%--
  Created by IntelliJ IDEA.
  User: nomena
  Date: 07/05/2023
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer num = (Integer) request.getAttribute("numberMistery");
    Integer countAppel = (Integer) request.getAttribute("count");

%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Count nbr appel objet : <%= countAppel %></h3>


<h1>  Bienvenue dans affNumberMistery.jsp  </h1>
<h2> Le nombre mistery est : <%= num %></h2>

</body>
</html>
