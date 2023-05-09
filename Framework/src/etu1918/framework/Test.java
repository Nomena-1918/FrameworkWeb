package etu1918.framework;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.ParamTest;
import etu1918.framework.annotationPerso.ParamValue;
import etu1918.framework.annotationPerso.TestClass;

@TestClass
public class Test {

    public void methodTest(@ParamTest(listString = {"val1", "val2"}) String param){

    }
}
