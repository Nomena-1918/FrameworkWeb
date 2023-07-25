<%@ page import="root.classesTest.Emp" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="root.classesTest.EmpModel" %>
<%@ page import="root.classesTest.Plat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  List<Object> listEmp = (List<Object>) request.getAttribute("list-emp");
  List<Object> listPlat = (List<Object>) request.getAttribute("list-plat");
%>

<html>
<head>
    <title>Formulaire Emp</title>
</head>
<body>

<h1>  Bienvenue dans formEmp.jsp  </h1>
<h2> Formulaire d'insertion de plat consommé :</h2>

  <form action="form-data.run" method="post" enctype="multipart/form-data">
    <div>
      <label for="dtn">Date de consommation :</label>
      <input type="date" id="dtn" name="dtn">
    </div>


    <div>
      <label>Emp</label>
      <label>
        <select name="idEmp">

          <% for(Object o : listEmp) { %>
            <option value="<%= ((EmpModel)o).getId() %>"> <%= ((EmpModel)o).getNom() %> </option>
          <% } %>

        </select>
      </label>
    </div>

    <div>
      <label>PLat</label>
      <label>
        <select name="idPlat">

          <% for(Object o : listPlat) { %>
          <option value="<%= ((Plat)o).getId() %>"> <%= ((Plat)o).getLibelle() %> </option>
          <% } %>

        </select>
      </label>
    </div>

    <div>
      <label for="fichier">Fichier :</label>
      <input type="file" id="fichier" name="fichier">
    </div>


    <div class="button">
      <button type="submit">Valider</button>
    </div>
  </form>


</body>
</html>
