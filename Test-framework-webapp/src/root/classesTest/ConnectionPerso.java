package root.classesTest;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPerso {
    
    // PostgreSQL
    public static Connection getConnection() throws Exception {
        Connection co = null;
        try {
            // A adapter selon le SGBDR utilisé
            Class.forName("org.postgresql.Driver");

            // url = nom_SGBDR + adresse ip + port + nom_database
            co = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testframework","nomena","root");

            // Eviter les commits automatiques (il faudra commit pour que les opérations soient sauvegardées)
            co.setAutoCommit(false);
        } catch (Exception e) {
            throw e;
        }
        return co;
    }
}
