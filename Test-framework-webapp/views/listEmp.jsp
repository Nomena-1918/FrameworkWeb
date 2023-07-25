<%@ page import="root.classesTest.Emp" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="root.classesTest.V_Empmodel_plat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    List<V_Empmodel_plat> list = new ArrayList<>();
    Object o = request.getAttribute("list-emp-plat");

    if (o != null)
        if (o instanceof ArrayList<?>)
            if (((ArrayList<?>)o).get(0) instanceof V_Empmodel_plat)
                list = (List<V_Empmodel_plat>) o;


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

<% for(V_Empmodel_plat e : list) { %>

<ul>
    <li><h3>Date : <%= e.getDate() %> , Emp : <%= e.getNom() %>, Plat : <%= e.getLibelle() %> </h3></li>
</ul>

<% } %>





</body>
</html>