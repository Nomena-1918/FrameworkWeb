package root.classesTest;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Root(name = "Plats")
public class Plats {
    @ElementList(entry = "Plat", inline = true)
    List<V_Empmodel_plat> listV_Empmodel_plat;

    public Plats() {
    }


    public List<V_Empmodel_plat> getListV_Empmodel_plat() {
        return listV_Empmodel_plat;
    }

    public void setListV_Empmodel_plat(List<Object> listV_Empmodel_plat) {
        List<V_Empmodel_plat> l = new ArrayList<>();

        for (Object o : listV_Empmodel_plat)
            if (o instanceof V_Empmodel_plat)
                l.add((V_Empmodel_plat)o);

        this.listV_Empmodel_plat = l;
    }
}
