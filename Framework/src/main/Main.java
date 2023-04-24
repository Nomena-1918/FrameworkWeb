package main;

import etu1918.framework.mapping.Mapping;
import utilPerso.Utilitaire;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\nFramework built successfully ! 🚀");
/*
        String dte = "2023-04-03";
        Date dtte = new SimpleDateFormat("yyyy/MM/dd").parse(dte);

        if (dtte != null)
            System.out.println("Mety lay cast ka !");

        else System.out.println("Nope");
*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2022-01-01";
        Date date = dateFormat.parse(dateString);
        System.out.println(date.toString());
    }
}