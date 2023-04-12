<%--
  Created by IntelliJ IDEA.
  User: nomena
  Date: 11/04/2023
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire Emp</title>
</head>
<body>

<h1>  Bienvenue dans formEmp.jsp  </h1>
<h2> Formulaire d'insertion d'employé :</h2>


  <form action="form-data.run" method="post">
    <div>
      <label for="matricule">Matricule :</label>
      <input type="number" id="matricule" name="matricule">
    </div>
    <div>
      <label for="nom">Nom :</label>
      <input type="text" id="nom" name="nom">
    </div>

<!--=========== CHECKBOXES ==========-->
    <div>
      <label>Prénoms :</label>
    </div>
    <div>
      <input type="checkbox" id="p1" name="prenoms" value="Nomena" checked>
      <label for="p1">Nomena</label>
    </div>

    <div>
      <input type="checkbox" id="p2" name="prenoms" value="Vahatra">
      <label for="p2">Vahatra</label>
    </div>
<!--================================-->

    <div class="button">
      <button type="submit">Valider</button>
    </div>
  </form>


</body>
</html>
