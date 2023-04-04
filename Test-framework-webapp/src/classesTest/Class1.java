package classesTest;

import etu1918.framework.annotationPerso.Controller;
import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

@Controller
public class Class1 {

    @URLMapping(valeur = "/truc.run")
    public ModelView getSalut() {

        //Manao traitements eto ...

        ModelView m = new ModelView();
        m.setView("salut.jsp");
        return m;
    }
}
