package utilPerso;

import org.jetbrains.annotations.Unmodifiable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Utilitaire {
    public static @Unmodifiable List<String> getInfoURL(HttpServletRequest req) {
        return List.of(req.getServletPath().split("/"));
    }
}
