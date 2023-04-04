package classesTest;

import business.Emp;
import etu1918.framework.annotationPerso.Controller;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpController {
    @URLMapping(valeur = "/list-emp.run")
    public ModelView findAll() {

        // La vue à appeler
        ModelView m = new ModelView();
        m.setView("list-emp.jsp");

        // Les données à lui envoyer
        List<Emp> l = new ArrayList<>();
        l.add(new Emp("Jean", 1));
        l.add(new Emp("Jeanne", 2));
        l.add(new Emp("Rakoto", 3));
        m.addItem("lst", l);

        return m;
    }

    @URLMapping(valeur = "/form-emp.run")
    public ModelView formAddEmp() {

        // La vue à appeler
        ModelView m = new ModelView();
        m.setView("form-emp.jsp");

        return m;
    }
}
