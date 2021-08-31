package com.kraftics.liberium.annotation;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.List;

public interface AnnotationHandler {

    void handleComponent(Object component);
    void registerAnnotation(Class<? extends Annotation> annotation, @Nullable OnMethod onMethod, @Nullable OnField onField, @Nullable OnComponent onComponent);
    void unregisterAnnotation(Class<? extends Annotation> annotation);
    List<Class<? extends Annotation>> getAnnotations();
    OnMethod getOnMethod(Class<? extends Annotation> annotation);
    OnField getOnField(Class<? extends Annotation> annotation);
    OnComponent getOnComponent(Class<? extends Annotation> annotation);
}
