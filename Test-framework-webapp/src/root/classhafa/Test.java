package root.classhafa;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.Scope;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;
import root.classesTest.Emp;

import java.util.ArrayList;
import java.util.List;

@Model
@Scope("singleton")
public class Test {
    Integer count = 0;

    @URLMapping(value = "/first-test.run")
    public ModelView affFirstView() {
        ModelView m = new ModelView();
        m.setView("firstView.jsp");
        return m;
    }

    @URLMapping(value = "/list-emp-test.run")
    public ModelView listView() {
        count++;
        ModelView m = new ModelView();

        List<Emp> listEmp = new ArrayList<>();
        listEmp.add(new Emp(1, "JeanneT"));
        listEmp.add(new Emp(2, "RasoaT"));
        listEmp.add(new Emp(3, "BemaT"));

        m.addItem("countTest", count);
        m.addItem("list-empTest", listEmp);

        m.setView("viewTest/listEmp.jsp");
        return m;
    }

}
