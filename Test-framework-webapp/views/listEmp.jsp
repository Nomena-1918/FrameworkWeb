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
            if (((ArrayList<?>) o).size() >0)
                if (((ArrayList<?>)o).get(0) instanceof V_Empmodel_plat)
                    list = (List<V_Empmodel_plat>) o;

%>

<h1>  Bienvenue dans listEmp.jsp  </h1>
<h2> La liste des employés :</h2>

<table>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Emp</th>
        <th>Plat</th>
        <th>Fichier</th>
    </tr>


    <% for(V_Empmodel_plat e : list) { %>
    <tr>
        <td><%= e.getId() %></td>
        <td><%= e.getDate() %></td>
        <td><%= e.getNom() %></td>
        <td><%= e.getLibelle() %></td>
        <td><a href="download-file.run?filename=<%= e.getNomfichier().replaceAll("\"", "") %>"><%= e.getNomfichier() %></a></td>
    </tr>
    <% } %>
</table>