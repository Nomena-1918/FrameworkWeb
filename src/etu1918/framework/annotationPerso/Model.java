package etu1918.framework.annotationPerso;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
    String name() default "classe modely";
    boolean isDAO() default false;
}
