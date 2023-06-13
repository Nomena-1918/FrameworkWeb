package etu1918.framework;

import etu1918.framework.annotationPerso.ParamTest;
import etu1918.framework.annotationPerso.TestClass;

@TestClass
public class Test {
    private String name;
    private int age;
    private String address;

    // Getters et setters

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "MyClass{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }




    public void methodTest(@ParamTest(listString = {"val1", "val2"}) String param){

    }
}
