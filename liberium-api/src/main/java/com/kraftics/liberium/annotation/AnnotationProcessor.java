package com.kraftics.liberium.annotation;

import com.kraftics.liberium.module.Module;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

public interface AnnotationProcessor {

    void runEvents(Object object);
    default void runEvents(AnnotatedElement element, Object object) {
        for (Annotation annotation : element.getAnnotations()) {
            runEvents(annotation, element, object);
        }
    }
    void runEvents(Annotation annotation, AnnotatedElement element, Object object);

    List<AnnotationListener<?, ?>> getListeners(Module module);
    List<AnnotationListener<?, ?>> getListeners(Annotation annotation, AnnotatedElement element);
    List<AnnotationListener<?, ?>> getEnabledListeners(Annotation annotation, AnnotatedElement element);
    <A extends Annotation, E extends AnnotatedElement> void registerListener(Module module, Class<A> annotation, Class<E> element, AnnotationListener<A, E> listener);
    void disableModule(Module module);
    void enableModule(Module module);
    boolean isModuleEnabled(Module module);
}
