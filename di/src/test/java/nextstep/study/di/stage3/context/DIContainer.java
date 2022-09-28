package nextstep.study.di.stage3.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 스프링의 BeanFactory, ApplicationContext에 해당되는 클래스
 */
class DIContainer {

    private final Set<Object> beans;

    public DIContainer(final Set<Class<?>> classes) {
        this.beans = createBeans(classes);
        this.beans.forEach(this::setFields);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<T> aClass) {
        return beans.stream()
            .filter(aClass::isInstance)
            .findFirst()
            .map(bean -> (T) bean)
            .orElseThrow(() -> new IllegalArgumentException(String.format("등록되지 않은 빈 객체입니다. [%s]", aClass)));
    }

    private Set<Object> createBeans(final Set<Class<?>> classes) {
        return classes.stream()
            .map(this::createObject)
            .collect(Collectors.toSet());
    }

    private Object createObject(final Class<?> clazz) {
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void setFields(final Object bean) {
        final Field[] fields = bean.getClass().getDeclaredFields();
        for (final Field field : fields) {
            setFields(bean, field);
        }
    }

    private void setFields(final Object bean, final Field field) {
        field.setAccessible(true);
        beans.stream()
            .filter(object -> field.getType().isInstance(object))
            .forEach(object -> setField(bean, field, object));
    }

    private void setField(final Object bean, final Field field, final Object object) {
        try {
            field.set(bean, object);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
