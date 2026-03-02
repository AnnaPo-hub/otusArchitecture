package ru.otus.processor;

import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("ru.otus.processor.GenerateAdapter")
//процессор запускается  когда встречает @GenerateAdapter
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AdapterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateAdapter.class)) {

            if (element.getKind() != ElementKind.INTERFACE) {
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "@GenerateAdapter можно ставить только на interface",
                        element
                );
                continue;
            }

            TypeElement iface = (TypeElement) element;
            GenerateAdapter config = iface.getAnnotation(GenerateAdapter.class);

            try {
                generateAdapter(iface, config);
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Ошибка генерации адаптера: " + e.getMessage(),
                        element
                );
            }
        }

        return true;
    }

    private void generateAdapter(TypeElement iface, GenerateAdapter config) throws IOException {

        String interfaceName = iface.getSimpleName().toString(); // IMovable
        String adapterName = interfaceName.replaceFirst("^I", "") + config.classNameSuffix();
        String packageName = config.packageName();

        String interfaceFqn = iface.getQualifiedName().toString();

        ClassName iocClass = ClassName.get("ru.otus.container", "IoC");
        ClassName commandClass = ClassName.get("ru.otus.command", "ICommand");

        // поле obj
        FieldSpec objField = FieldSpec.builder(Object.class, "obj", Modifier.PRIVATE, Modifier.FINAL)
                .build();

        // конструктор
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "obj")
                .addStatement("this.obj = obj")
                .build();

        TypeSpec.Builder adapterBuilder = TypeSpec.classBuilder(adapterName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(TypeName.get(iface.asType()))
                .addField(objField)
                .addMethod(constructor);

        List<ExecutableElement> methods =
                ElementFilter.methodsIn(iface.getEnclosedElements());

        for (ExecutableElement method : methods) {
            adapterBuilder.addMethod(
                    generateMethod(method, interfaceFqn, iocClass, commandClass)
            );
        }

        JavaFile.builder(packageName, adapterBuilder.build())
                .indent("    ")
                .build()
                .writeTo(processingEnv.getFiler());
    }

    private MethodSpec generateMethod(
            ExecutableElement method,
            String interfaceFqn,
            ClassName iocClass,
            ClassName commandClass
    ) {

        String methodName = method.getSimpleName().toString();

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(method.getReturnType()));

        // параметры метода
        for (VariableElement param : method.getParameters()) {
            methodBuilder.addParameter(
                    TypeName.get(param.asType()),
                    param.getSimpleName().toString()
            );
        }

        // формируем ключ IoC
        String key = buildKey(interfaceFqn, methodName);

        // формируем аргументы resolve: obj + параметры метода
        StringBuilder argsBuilder = new StringBuilder("obj");
        for (VariableElement param : method.getParameters()) {
            argsBuilder.append(", ").append(param.getSimpleName());
        }

        TypeMirror returnType = method.getReturnType();
        boolean returnsVoid = returnType.getKind() == TypeKind.VOID;

        if (returnsVoid) {
            // void -> считаем это ICommand
            methodBuilder.addStatement(
                    "try {\n" +
                            "    (($T) $T.resolve($S, $L)).execute();\n" +
                            "} catch (Exception e) {\n" +
                            "    throw new RuntimeException(e);\n" +
                            "}",
                    commandClass,
                    iocClass,
                    key,
                    argsBuilder.toString()
            );
        } else {
            // не void -> возвращаем результат resolve
            methodBuilder.addStatement(
                    "return ($T) $T.resolve($S, $L)",
                    TypeName.get(returnType),
                    iocClass,
                    key,
                    argsBuilder.toString()
            );
        }

        return methodBuilder.build();
    }

    private String buildKey(String interfaceFqn, String methodName) {

        if (methodName.startsWith("get") && methodName.length() > 3) {
            String prop = decapitalize(methodName.substring(3));
            return interfaceFqn + ":" + prop + ".get";
        }

        if (methodName.startsWith("set") && methodName.length() > 3) {
            String prop = decapitalize(methodName.substring(3));
            return interfaceFqn + ":" + prop + ".set";
        }

        return interfaceFqn + ":" + methodName;
    }

    private String decapitalize(String value) {
        if (value.isEmpty()) return value;
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }
}
