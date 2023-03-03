package utilPerso;

import org.jetbrains.annotations.Unmodifiable;

import java.net.URL;
import java.util.List;

public class Utilitaire {
    public static @Unmodifiable List<String> getInfoURL(String url) {
        return List.of(url.split("/"));
    }
}
