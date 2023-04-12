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

  <form action="form-data.run" method="post">
    <div>
      <label for="matricule">Matricule :</label>
      <input type="number" id="matricule" name="matricule">
    </div>
    <div>
      <label for="nom">Nom :</label>
      <input type="text" id="nom" name="nom">
    </div>
    <div class="button">
      <button type="submit">Valider</button>
    </div>
  </form>


</body>
</html>
