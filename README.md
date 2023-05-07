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

                // nom du paramètre, obligatoire
                <param-name>classpath</param-name>
    
                // package à partir duquel scanner, au choix
                <param-value>root</param-value>

            </init-param>
        </servlet>
        <servlet-mapping>
          <servlet-name>FrontServlet</servlet-name>

            // extension de la FrontServlet
            <url-pattern>*.run</url-pattern>
        </servlet-mapping>


### Contrôleur personnalisé
- Ajout de l'annotation de classe Model
  - Association méthode-url : ajout d'annotation URLMapping(valeur="/YOUR_URL.run")
    - Une fonction appelant une vue doit retourner un objet ModelView : il faudra setter les data et la vue à appeler
      - Exemple concret : 

            @URLMapping(valeur = "/list-emp.run")
            public ModelView listView() {
              ModelView m = new ModelView();
  
                  List<Emp> listEmp = new ArrayList<>();
                  listEmp.add(new Emp(1, "Jeanne"));
                  listEmp.add(new Emp(2, "Rasoa"));
                  listEmp.add(new Emp(3, "Bema"));
    
                  m.addItem("list-emp", listEmp);
    
                  m.setView("listEmp.jsp");
                  return m;
            }
        - Dans la vue correspondante (listEmp.jsp) :
        
              <%
        
                  List<Emp> list = new ArrayList<>();
                  Object o = request.getAttribute("list-emp");
        
                  if (o != null)
                      if (o instanceof ArrayList<?>)
                          if (((ArrayList<?>)o).get(0) instanceof Emp)
                              list = (ArrayList<Emp>) o;
        
              %>
        
              ...
        
              <h2> La liste des employés :</h2>
              <% for(Emp e : list) { %>
          
              <ul>
                  <li><h3>Nom : <%= e.getNom() %> , Matricule : <%= e.getMatricule() %> </h3></li>
              </ul>
          
              <% } %>


### Gestion des données envoyées vers la méthode d'action 
#### Méthode 1 : Par l'objet appelant
(Ex d'usage : formulaire)
- Pas d'attributs avec des types primitifs (int, boolean, float, etc...) que des wrappers (Integer, Boolean, Float, etc, ...)
- Le name des input doit être le même que le nom des attributs de la classe Model traitant la requête
- Exemple concret :
  - La classe Emp :

    
        @Model
        public class Emp {
            Date dtn;
            String nom;
            ...
            
        @URLMapping(valeur = "/form-data.run")
        public ModelView affFormData() {
            ModelView m = new ModelView();
    
            m.addItem("formData", this);
            m.setView("formDataView.jsp");
    
            return m;
        }

- Le formulaire d'insertion :

      <form action="form-data.run" method="post">
        <div>
          <label for="nom">Nom :</label>
          <input type="text" id="nom" name="nom">
        </div>
        <div>
          <label for="dtn">Date de naissance :</label>
          <input type="date" id="dtn" name="dtn">
        </div>
         <div class="button">
      <button type="submit">Valider</button>
        </div>
      </form>
<br>

#### Méthode 2 : Paramètre extérieur à l'objet appelant
- Ici les données envoyées seront les paramètres de la méthode d'action (celle associée à l'URL)
- Pour l'instant, un paramètre maximum pour la méthode d'action 
- Le paramètre devra être annoté par @ParamValue et sa valeur doit être la même que le paramètre de la requête
  - Exemple concret :

        @URLMapping(valeur = "/nbr/mistery.run")
        public ModelView methodWithOneArg(@ParamValue(value = "num") Integer number) {
        ModelView m = new ModelView();
    
              if (number == null)
                  number = 19;
    
              m.addItem("numberMistery", number);
              m.setView("/view/affNumberMistery.jsp");
    
              return m;
        }
  - Cette méthode peut, par exemple, être appelée par l'URL nbr/mistery.run?num=27

