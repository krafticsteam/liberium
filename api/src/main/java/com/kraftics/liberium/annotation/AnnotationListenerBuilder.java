package com.kraftics.liberium.annotation;

import com.kraftics.liberium.module.Module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AnnotationListenerBuilder<A extends Annotation> {
    private final Module module;
    private final Class<A> annotation;
    private final AnnotationProcessor processor;

    public AnnotationListenerBuilder(Module module, Class<A> annotation, AnnotationProcessor processor) {
        this.module = module;
        this.annotation = annotation;
        this.processor = processor;
    }

    public AnnotationListenerBuilder<A> onClass(AnnotationListener<A, Class> listener) {
        processor.registerListener(module, annotation, Class.class, listener);
        return this;
    }

    public AnnotationListenerBuilder<A> onClass(BiConsumer<A, Class<?>> listener) {
        return onClass((a, e, o) -> listener.accept(a, e));
    }

    public AnnotationListenerBuilder<A> onClass(Consumer<Class<?>> listener) {
        return onClass((a, e, o) -> listener.accept(e));
    }

    public AnnotationListenerBuilder<A> onField(AnnotationListener<A, Field> listener) {
        processor.registerListener(module, annotation, Field.class, listener);
        return this;
    }

    public AnnotationListenerBuilder<A> onField(BiConsumer<A, Field> listener) {
        return onField((a, e, o) -> listener.accept(a, e));
    }

    public AnnotationListenerBuilder<A> onField(Consumer<Field> listener) {
        return onField((a, e, o) -> listener.accept(e));
    }

    public AnnotationListenerBuilder<A> onField(Function<Object, Object> modify) {
        return onField((a, f, o) -> {
            try {
                f.setAccessible(true);
                f.set(o, modify.apply(f.get(o)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnnotationListenerBuilder<A> onField(Supplier<Object> modify) {
        return onField((a, f, o) -> {
            try {
                f.setAccessible(true);
                f.set(o, modify.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnnotationListenerBuilder<A> onField(Object modify) {
        return onField((a, f, o) -> {
            try {
                f.setAccessible(true);
                f.set(o, modify);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnnotationListenerBuilder<A> onMethod(AnnotationListener<A, Method> listener) {
        processor.registerListener(module, annotation, Method.class, listener);
        return this;
    }

    public AnnotationListenerBuilder<A> onMethod(BiConsumer<A, Method> listener) {
        return onMethod((a, e, o) -> listener.accept(a, e));
    }

    public AnnotationListenerBuilder<A> onMethod(Consumer<Method> listener) {
        return onMethod((a, e, o) -> listener.accept(e));
    }

    public AnnotationListenerBuilder<A> onMethodExecute(Object... args) {
        return onMethod((a, m, o) -> {
            try {
                m.setAccessible(true);
                m.invoke(o, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnnotationListenerBuilder<A> onMethodExecute(Consumer<Object> listener, Object... args) {
        return onMethod((a, m, o) -> {
            try {
                m.setAccessible(true);
                listener.accept(m.invoke(o, args));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnnotationListenerBuilder<A> onConstructor(AnnotationListener<A, Constructor> listener) {
        processor.registerListener(module, annotation, Constructor.class, listener);
        return this;
    }

    public AnnotationListenerBuilder<A> onConstructor(BiConsumer<A, Constructor<?>> listener) {
        return onConstructor((a, e, o) -> listener.accept(a, e));
    }

    public AnnotationListenerBuilder<A> onConstructor(Consumer<Constructor<?>> listener) {
        return onConstructor((a, e, o) -> listener.accept(e));
    }
}
