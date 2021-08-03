package com.rookie.my_compiler.entity;

import com.rookie.my_annotation.anno.ShapeFactoryAnnotation;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ShapeFactoryGroupedClasses {
    private final TypeElement mAnnotatedClassElement;
    private final String mId;

    public ShapeFactoryGroupedClasses(TypeElement classElement, Messager messager) {
        this.mAnnotatedClassElement = classElement;
        ShapeFactoryAnnotation annotation = classElement.getAnnotation(ShapeFactoryAnnotation.class);
        mId = annotation.id();
        if (mId.length() == 0) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            ShapeFactoryAnnotation.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }
        try {
            //该类已经被编译
            Class clazz = annotation.type();
        String canonicalName = clazz.getCanonicalName();
        String simpleName = clazz.getSimpleName();
        messager.printMessage(Diagnostic.Kind.NOTE, canonicalName);
        } catch (MirroredTypeException e) {
            //该类未被编译
            messager.printMessage(Diagnostic.Kind.NOTE, e.getMessage());
            TypeMirror typeMirror = e.getTypeMirror();
            DeclaredType declaredType = (DeclaredType) typeMirror;
            TypeElement typeElement = (TypeElement) declaredType.asElement();
            String canonicalName = typeElement.getQualifiedName().toString();
            String simpleName = typeElement.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "exception:"+canonicalName);
        }

    }
}
