<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Page Title</title>
  <link rel="stylesheet" href="Shared/css/bootstrap.min.css">
  <script src="Shared/js/bootstrap.min.js"></script>
</head>
<body>

<%
  if (request.getAttribute("view")==null || request.getAttribute("view").equals(""))
    request.setAttribute("view", "Accueil.jsp");
%>
  <jsp:include page="${requestScope.view}"/>

</body>
</html>
