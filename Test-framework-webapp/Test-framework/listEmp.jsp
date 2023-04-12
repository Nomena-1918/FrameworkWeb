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

    List<Emp> list = new ArrayList<>();
    Object o = request.getAttribute("list-emp");

    if (o != null)
        if (o instanceof ArrayList<?>)
            if (((ArrayList<?>)o).get(0) instanceof Emp)
                list = (ArrayList<Emp>) o;

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>  Bienvenue dans listEmp.jsp  </h1>
<h2> La liste des employés :</h2>

<% for(Emp e : list) { %>

<ul>
    <li><h3>Nom : <%= e.getNom() %> , Matricule : <%= e.getMatricule() %> </h3></li>
</ul>

<% } %>


</body>
</html>