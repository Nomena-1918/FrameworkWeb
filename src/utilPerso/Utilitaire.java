package utilPerso;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class Utilitaire {
    public static List<String> getInfoURL(HttpServletRequest req) {
        return Arrays.asList(req.getServletPath().split("/"));
    }
}
