<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <title>Projet test</title>
</head>
<body>

<p><%="Profil :  "+session.getAttribute("profil")%></p>
<!--<p><%="varSession :  "+session.getAttribute("varSession")%></p>-->

<%  if(request.getAttribute("emp_plat")!=null) {%>
<p><%="Emp_plat :  "+request.getAttribute("emp_plat")%></p>
<%  }   %>

    <h1>Bienvenue dans ce projet test !</h1>
    <h2>Les liens disponibles actuellement :  </h2>

    <p>Pour admin uniquement</p>
    <ul>
        <li><h3><a href="form-emp.run">form Emp-plat avec FileUpload</a></h3></li>
        <li><h3><a href="nbr/mistery.run?num=42&&num1=1">mistery.run?num=42&&num1=1</a></h3></li>
        <li><h3><a href="invalidate-session.run">invalidate-session.run</a></h3></li>
    </ul>

    <p>Pour utilisateurs authentifiés</p>
    <ul>
        <li><h3><a href="logout.run">logout.run</a></h3>
    </ul>

    <p>Pour tout le monde</p>
    <ul>
        <li><h3><a href="list-emp-plat.run">Liste des plats consommés</a></h3></li>
        <li><h3><a href="rest-api.run?arg=true">rest-api.run</a></h3></li>
        <li><h3><a href="first-test.run">first-test.run</a></h3></li>
        <li><h3><a href="login.run">login.run (devient admin)</a></h3></li>
        <li><h3><a href="export-xml.run">Export xml</a></h3></li>
    </ul>


</body>
</html>
