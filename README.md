# FrameworkWeb
Suivi de la creation d'un framework web en Java en S4 à ITUniversity

## Utilisation du framework
### Installation
- System requirements : jdk17 et Tomcat 8.5
- Copie du framework.jar dans le répertoire lib du webapp

### Configuration 
- Dans web.xml du webapp :
  - Ajout de la servlet FrontServlet :
  
        <servlet>
        <servlet-name>FrontServlet</servlet-name>
        <servlet-class>etu1918.framework.servlet.FrontServlet</servlet-class>
        <init-param>
                <param-name>packClass</param-name>
                <param-value>Path/absolu/du/répertoire/obligatoire/classes</param-value>
        </init-param>
        </servlet>
    
  - Ajout de l'URL Pattern du FrontServlet :
 
        <servlet-mapping>
        <servlet-name>FrontServlet</servlet-name>
        <url-pattern>*.run</url-pattern>
        </servlet-mapping>


### Contrôleur personnalisé
- Ajout de l'annotation de classe Model
- Association méthode-url : ajout d'annotation URLMapping(valeur="/YOUR_URL.run")
- Une fonction appelant une vue doit retourner un objet ModelView : il faudra setter les data et la vue à appeler

### Gestion des paramètres de la méthode d'action 
(Ex d'usage : formulaire)
- Pas d'attributs avec des types primitifs (int, boolean, float, etc...) que des wrappers (Integer, Boolean, Float, etc, ...)
- Le name des input doit être le même que le nom des attributs de la classe Model traitant la requête