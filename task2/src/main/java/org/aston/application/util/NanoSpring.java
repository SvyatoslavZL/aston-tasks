package org.aston.application.util;

import org.aston.application.exception.NanoSpringException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class NanoSpring {

    private static final Map<Class<?>, Object> beans = new HashMap<>();
    public static final String CLASSES = File.separator + "classes" + File.separator;
    public static final String EXT = ".class";
    public static final String DOT = ".";
    public static final String EMPTY = "";

    private NanoSpring() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T find(Class<T> type) {
        if (beanDefinitions.isEmpty()) {
            init();
        }
        Object component = beans.get(type);
        if (component == null) {
            Constructor<?> constructor = type.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Type[] genericParameterTypes = constructor.getGenericParameterTypes(); //2.
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameters.length; i++) {
                Class<?> impl = findImpl(parameterTypes[i], genericParameterTypes[i]); //3.
                parameters[i] = find(impl);
            }
            Object newInstance;
            try {
                newInstance = constructor.newInstance(parameters);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new NanoSpringException(e);
            }
            beans.put(type, newInstance);
        }
        return (T) beans.get(type);
    }

    private static final List<Class<?>> beanDefinitions = new ArrayList<>();

    private static void init() {
        URL resource = NanoSpring.class.getResource("NanoSpring.class");
        URI uri;
        try {
            uri = Objects.requireNonNull(resource).toURI();
        } catch (URISyntaxException e) {
            throw new NanoSpringException(e);
        }
        Path appRoot = Path.of(uri).getParent().getParent();
        scanPackages(appRoot, "Servlet");
    }

    public static void scanPackages(Path appPackage, String... excludes) {
        try (Stream<Path> walk = Files.walk(appPackage)) {
            List<String> names = walk.map(Path::toString)
                    .filter(o -> o.endsWith(EXT))
                    .filter(o -> Arrays.stream(excludes)
                            .noneMatch(o::contains))
                    .map(s -> s.substring(getStartClassName(s)))
                    .map(s -> s.replace(EXT, EMPTY))
                    .map(s -> s.replace(File.separator, DOT))
                    .toList();
            for (String name : names) {
                beanDefinitions.add(Class.forName(name));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new NanoSpringException(e);
        }
    }

    private static int getStartClassName(String s) {
        return s.indexOf(NanoSpring.CLASSES) + NanoSpring.CLASSES.length();
    }

    private static Class<?> findImpl(Class<?> aClass, Type type) {
        for (Class<?> beanDefinition : beanDefinitions) {
            boolean assignable = aClass.isAssignableFrom(beanDefinition);
            boolean nonGeneric = beanDefinition.getTypeParameters().length == 0;
            boolean nonInterface = !beanDefinition.isInterface();
            boolean nonAbstract = !Modifier.isAbstract(beanDefinition.getModifiers());
            boolean checkGenerics = checkGenerics(type, beanDefinition);
            if (assignable && nonGeneric && nonInterface && nonAbstract && checkGenerics) {
                return beanDefinition;
            }
        }
        throw new NanoSpringException("Not found impl for %s (type=%s)".formatted(aClass, type));
    }

    private static boolean checkGenerics(Type type, Class<?> impl) {
        var typeContractGeneric = NanoSpring.getContractGeneric(type);
        return Objects.nonNull(impl) &&
               Stream.iterate(impl, Objects::nonNull, (Class<?> c) -> c.getSuperclass())
                       .flatMap(c -> Stream.concat(
                               Stream.of(c.getGenericSuperclass()),
                               Stream.of(c.getGenericInterfaces())))
                       .filter(Objects::nonNull)
                       .map(NanoSpring::getContractGeneric)
                       .anyMatch(typeContractGeneric::equals);
    }

    private static List<? extends Class<?>> getContractGeneric(Type type) {
        var typeName = type.getTypeName();
        return !typeName.contains("<")
                ? List.of()
                : Arrays.stream(typeName
                        .replaceFirst(".+<", EMPTY)
                        .replace(">", EMPTY)
                        .split(","))
                .map(NanoSpring::getaClassOrNull)
                .toList();
    }

    private static Class<?> getaClassOrNull(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
