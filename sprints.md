# Session + Authentification

Ex :

## Objectif : Protection controller/model
### Modèle

    si rien, tout le monde peut le faire  
    findAll()

    @auth("admin")  -> authentifié admin
    delete

    @auth  -> juste authentifié
    save


## A faire : idée
- Nouvelles annotations :
    @auth(
        value = ""
    )

- Ajouter un attribut HashMap Session dans ModelView

    Modèle 

- web.xml :
    sessioname
    profilname


- Utilitaire:
    - CheckMethod(method, attrSession) :
        - Vérifie les annotations de méthode 
        - Si @auth return TRUE si  attrSession != null
        - Si @auth("admin) return TRUE si  attrSession == admin

- FrontServlet : 
    - Avant d'appeler la méthode par réflection, passer par CheckMethod(method, attrSession)


</br>
</br>
</br>
</br>


# Sprint 12

Dès l'instanciation d'un modèle, lui donner un HttpSession

## Comment 
- Ajout attribut HashMap<String, Object> session dans modèle

FrontServlet
- Injection des valeurs de HttpSession dans le modèle à instancier

    

