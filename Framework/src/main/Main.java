package main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.EmpModel;
import etu1918.framework.mapping.Mapping;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("\n\tFramework built successfully ! 🚀\n");

        List<Object> l = new EmpModel().select(null);
        System.out.println(l);
/*
        Gson gson = new Gson();
        // Create a new HashMap
        HashMap<String, Integer> hashMap = new HashMap<>();

        // Add key-value pairs to the HashMap
        hashMap.put("apple", 10);
        hashMap.put("banana", 5);
        hashMap.put("orange", 8);

        String json = gson.toJson(hashMap);
        System.out.println(json);
*/
    }
}
