

<html>
<head>
    <title>Formulaire Emp</title>
</head>
<body>


<h1>  Bienvenue dans formEmp.jsp  </h1>
<h2> Formulaire d'insertion d'employé :</h2>



  <form action="form-data.run" method="post" enctype="multipart/form-data">
    <div>
      <label for="fichier">Fichier :</label>
      <input type="file" id="fichier" name="fichier">
    </div>
    <div>
      <label for="matricule">Matricule :</label>
      <input type="number" id="matricule" name="matricule">
    </div>
    <div>
      <label for="nom">Nom :</label>
      <input type="text" id="nom" name="nom">
    </div>
    <div>
      <label for="dtn">Date de naissance :</label>
      <input type="date" id="dtn" name="dtn">
    </div>

    <div>
      <input type="checkbox" id="isBoss" name="isBoss" value="true">
      <label for="isBoss"> Is Boss  :</label>
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

    <div>
      <input type="checkbox" id="p3" name="prenoms" value="Else...">
      <label for="p2">Else...</label>
    </div>
    <!--================================-->

    <div class="button">
      <button type="submit">Valider</button>
    </div>
  </form>


</body>
</html>
