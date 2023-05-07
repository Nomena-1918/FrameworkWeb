<%@ page import="root.classesTest.Emp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

  Emp e = (Emp) request.getAttribute("formData");
  String[] prenoms = e.getPrenoms();

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
      <li><h3>Date de naissance : <%= e.getDtn() %></h3></li>
      <li><h3>Is boss : <%= e.getIsBoss() %></h3></li>
      <li><h3>Prénoms : </h3>
        <h3>
          <ul>
            <%
            if (prenoms != null) {
              for (String s : e.getPrenoms()) {%>
            <li> <%= s %> </li>
            <% }
            }
            else {
            %>
            <li> <%= "pas de prénoms" %> </li>
            <% }%>
          </ul>
        </h3>
      </li>
    </ul>


</body>
</html>