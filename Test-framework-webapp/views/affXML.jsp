<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String data = (String) request.getAttribute("dataXml");
%>
<html>
<head>
    <title>XML page</title>
</head>
<body>

<h1>  Bienvenue dans affXML.jsp  </h1>
<h2> DataXML : <%= data %></h2>

</body>
</html>