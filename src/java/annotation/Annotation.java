/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annotation;

import java.lang.annotation.*;

/**
 *
 * @author P15A-13-Hendry
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Annotation {
    String url();
    String attribut();
}
