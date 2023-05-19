# Workflow de développement
## Etapes :
### 1) Compilation du projet Framework avec compileFramework.sh             
### 2) Exécution de deployFramework.sh
### 3) Compilation du projet Test-framework-webapp avec compileTest.sh
### 4) Exécution de deployTomcat.sh 
## ou deploy.sh
### 5) Implémentaion d'une nouvelle fonctionnalité et on revient à 1)
### ...

<br>

next

<br>

# Sprint 9 
## Gestion d'upload de fichier
### 1. Classe framework :
    FileUpload {
        String name;
        String path; (laisser vide d'abord)
        byte[] file;

        ... get-set 
    }
<br>

### 2. Classe test : 
Exemple : 
        
    Client {
        String name;
        ...
        FileUpload badge;
    }

### 3. Formulaire : 
Exemple :

    <form ... enctype="...">
        ...
        <input type="file" name="badge">
        ...
    </form>

### 4. FrontServlet :  
- Traitement au préalable lorsqu'il a un attribut de type FileUpload dans une classe 
