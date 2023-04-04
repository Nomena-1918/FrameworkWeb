package classesTest;

import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

public class Class2 {
    @URLMapping(valeur = "/truc1.run")
    public ModelView getBonjour() {

        //Manao traitements eto ...

        ModelView m = new ModelView();
        m.setView("bonjour.html");
        return m;
    }
}
