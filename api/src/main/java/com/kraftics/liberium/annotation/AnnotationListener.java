package com.kraftics.liberium.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

@FunctionalInterface
public interface AnnotationListener<A extends Annotation, E extends AnnotatedElement> {

    void init(A annotation, E element, Object object);

    default void castInit(Annotation annotation, AnnotatedElement element, Object object) {
        init((A) annotation, (E) element, object);
    }
}
