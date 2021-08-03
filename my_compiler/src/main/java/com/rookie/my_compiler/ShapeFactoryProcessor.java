package com.rookie.my_compiler;

import com.google.auto.service.AutoService;
import com.rookie.my_annotation.anno.ShapeFactoryAnnotation;
import com.rookie.my_compiler.entity.ShapeFactoryGroupedClasses;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

//它的作用时用来生成META-INF/services/javax.annotation.processing.Processor文件，
//也就是我们在使用注解处理器的时候需要手动添加META-INF/services/javax.annotation.processing.Processor，而有了@AutoService后它会自动帮我们生成。
@AutoService(Processor.class)
public class ShapeFactoryProcessor extends AbstractProcessor {
    private Types mTypeUtils;
    private Messager mMessager;
    private Filer mFiler;
    private Elements mElementUtils;
    private Map<String, ShapeFactoryGroupedClasses> groupedClassesLinkedHashMap = new LinkedHashMap<>();

    @Override

    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new LinkedHashSet<>();
        result.add(ShapeFactoryAnnotation.class.getCanonicalName());
        return result;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment environment) {
        super.init(environment);
        mTypeUtils = environment.getTypeUtils();
        mMessager = environment.getMessager();
        mFiler = environment.getFiler();
        mElementUtils = environment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> shapeFactoryAnnotationElements = roundEnvironment.getElementsAnnotatedWith(ShapeFactoryAnnotation.class);
        for (Element shapeFactoryAnnotationElement : shapeFactoryAnnotationElements) {
            TypeElement typeElement = (TypeElement) shapeFactoryAnnotationElement;
            if (typeElement.getKind() != ElementKind.CLASS) {
                continue;
            }
            ShapeFactoryGroupedClasses classes = new ShapeFactoryGroupedClasses(typeElement, mMessager);
//            ShapeFactoryAnnotation annotation = typeElement.getAnnotation(ShapeFactoryAnnotation.class);
//            String id = annotation.id();
//            Class aClass = annotation.type();
//            mMessager.printMessage(Diagnostic.Kind.NOTE, id + "," + aClass.getSimpleName());

        }
        return true;
    }
}