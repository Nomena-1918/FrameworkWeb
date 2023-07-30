package main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.EmpModel;
import etu1918.framework.mapping.Mapping;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


import java.awt.print.Book;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Persons {
    @ElementList(entry = "person", inline = true)
    private List<Person> list;

    public Persons() {
    }

    public Persons(List<Person> list) {
        this.list = list;
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }
}


public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("\n\tFramework built successfully ! 🚀\n");
/*
        Person person = new Person();
        // Définition des propriétés...

        person.setName("Jean");
        person.setAge(25);
  */
        // Création d'une liste pour stocker les personnes
        List<Person> personsList = new ArrayList<>();

        // Génération des 6 personnes et ajout à la liste
        personsList.add(new Person("Alice", 25));
        personsList.add(new Person("Bob", 30));
        personsList.add(new Person("Charlie", 22));
        personsList.add(new Person("David", 27));
        personsList.add(new Person("Eva", 29));
        personsList.add(new Person("Frank", 33));

        Persons p = new Persons(personsList);

        // Sérialisation de l'objet en XML
        Serializer serializer = new Persister();
        StringWriter result = new StringWriter();
        serializer.write(p, result);

        // Obtention de la représentation XML en tant que String
        String xmlString = result.toString();

        // Affichage de la représentation XML
        System.out.println(xmlString);

/*
        List<Object> l = new EmpModel().select(null);
        System.out.println(l);

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
