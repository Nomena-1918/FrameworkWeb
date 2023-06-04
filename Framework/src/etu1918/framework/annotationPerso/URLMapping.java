package etu1918.framework.annotationPerso;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface URLMapping {
    String value() default "/";
}
