package classPerso;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;

@Model
public class Truc {
    String attr;
    @URLMapping(valeur="/truc-salut")
    public String bonjour() {
        return "Bonjour !";
    }
}
