<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>  Bienvenue dans login.jsp  </h1>


<form action="process-login.run" method="post">
    <div>
        <label for="mdp">Mot de passe :</label>
        <input type="text" id="mdp" name="mdp">
    </div>
    <div class="button">
        <button type="submit">Valider</button>
    </div>
</form>


</body>
</html>