package classesTest;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

@Model
public class Class1 {

    @URLMapping(valeur = "/truc")
    public ModelView getSalut() {

        //Manao traitements eto ...

        ModelView m = new ModelView();
        m.setView("salut.html");
        return m;
    }
}
