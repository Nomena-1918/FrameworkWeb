<%@ page import="root.classesTest.Emp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String data = request.getAttribute("dataJson").toString();
    //Integer countAppel = (Integer) request.getAttribute("count");

%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>  Bienvenue dans affNumberMistery.jsp  </h1>
<h2> DataJSON : <%=  data %></h2>

</body>
</html>
