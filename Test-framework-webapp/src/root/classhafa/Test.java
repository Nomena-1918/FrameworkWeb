package root.classhafa;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

@Model
public class Test {

    @URLMapping(valeur = "/first-test.run")
    public ModelView affFirstView() {
        ModelView m = new ModelView();
        m.setView("firstView.jsp");
        return m;
    }

}
