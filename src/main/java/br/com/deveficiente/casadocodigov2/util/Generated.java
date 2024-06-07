package br.com.deveficiente.casadocodigov2.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, ElementType.CONSTRUCTOR})
public @interface Generated {
    public static final String ECLIPSE = "Gerado pelo eclipse";
    String value();
}
