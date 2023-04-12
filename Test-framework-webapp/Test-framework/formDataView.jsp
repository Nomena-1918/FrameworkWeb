<%@ page import="classesTest.Emp" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: nomena
  Date: 11/04/2023
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

  Emp e = (Emp) request.getAttribute("formData");

%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>

<h1>  Bienvenue dans formData.jsp  </h1>
  <h2> L'employé correspondant au formulaire :</h2>

    <ul>
      <li><h3>Matricule : <%= e.getMatricule() %></h3></li>
      <li><h3>Nom : <%= e.getNom() %></h3></li>
    </ul>


</body>
</html>