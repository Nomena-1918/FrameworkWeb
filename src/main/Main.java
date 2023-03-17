package main;

import etu1918.framework.Mapping;
import utilPerso.Utilitaire;
import java.util.HashMap;
import java.util.Map;

public class Main {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception {

        String[] listPack = {"classPerso", "string"};

        HashMap<String, Mapping> hash = Utilitaire.initHashMap(listPack);
        System.out.println(hash);

        for (Map.Entry me : hash.entrySet()) {
            System.out.println("Key : "+me.getKey()+", Value : "+me.getValue());
        }

        System.out.println("L'URL est supportée : "+hash.containsKey("/truc-salut"));

    }
}