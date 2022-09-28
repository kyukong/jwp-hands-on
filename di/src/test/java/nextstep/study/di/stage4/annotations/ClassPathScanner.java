package nextstep.study.di.stage4.annotations;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class ClassPathScanner {

    public static Set<Class<?>> getAllClassesInPackage(final String packageName) {
        return scanClasses(packageName, List.of(Repository.class, Service.class));
    }

    private static Set<Class<?>> scanClasses(final String packageName, final List<Class<? extends Annotation>> annotations) {
        final Reflections reflections = new Reflections(packageName);
        final Set<Class<?>> classes = new HashSet<>();
        for (final Class<? extends Annotation> annotation : annotations) {
            classes.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return classes;
    }
}
