package main;

import etu1918.framework.mapping.Mapping;
import utilPerso.Utilitaire;

import java.util.*;

public class Main {
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception {

        HashMap<String, Mapping> hash = Utilitaire.initHashMap("randomPackage");
        System.out.println(hash);

        for (Map.Entry me : hash.entrySet()) {
            System.out.println("Key : "+me.getKey()+", Value : "+((Mapping)me.getValue()).getClassName());
        }

        System.out.println("L'URL est supportée : "+hash.containsKey("/truc-salut"));
        System.out.println("Hey! Frame works !!!");
    }
}