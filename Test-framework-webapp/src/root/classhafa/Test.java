package root.classhafa;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.Scope;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;
import root.classesTest.Emp;

import java.util.ArrayList;
import java.util.List;

@Model
public class Test {

    @URLMapping(value = "/first-test.run")
    public ModelView affFirstView() {
        ModelView m = new ModelView();
        m.setView("firstView.jsp");
        return m;
    }

}
