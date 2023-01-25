package com.bring.context;

import com.bring.annotation.Bean;
import com.bring.exception.NoSuchBeanException;
import com.bring.exception.NoUniqueBeanException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> beanMap = new HashMap<>();

    private BeanNameService beanNameService;

    public ApplicationContextImpl(String packageName) {
        init(packageName);
    }

    private void init(String packageName) {
        this.beanNameService = new BeanNameService();
        Reflections reflections = new Reflections(packageName);
        reflections.getTypesAnnotatedWith(Bean.class)
                .forEach(this::registerBean);

    }

    private void registerBean(Class<?> cls) {
        try {
            Constructor<?> ctor = cls.getDeclaredConstructor();
            Object instance = ctor.newInstance();
            String beanName = beanNameService.generateName(cls);
            beanMap.put(beanName, instance);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Can't create instance ", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Can't find default constructor ", e);
        }
    }


    public <T> T getBean(Class<T> beanType) {
        List<T> result = new ArrayList<>();
        for (Object obj : beanMap.values()) {
            if (beanType.isAssignableFrom(obj.getClass())) {
                result.add((T) obj);
            }
        }
        if (result.size() > 1) {
            throw new NoUniqueBeanException();
        }
        if (result.size() == 1) {
            return result.get(0);
        }
        throw new NoSuchBeanException(String.format("Can't find bean with #%s type", beanType.getSimpleName()));
    }

    public <T> T getBean(String name, Class<T> beanType) {
        Object obj = beanMap.get(name);
        if (obj == null) {
            throw new NoSuchBeanException(String.format("Can't find bean with #%s type", beanType.getSimpleName()));
        }
        return (T) obj;
    }

    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return beanMap
                .entrySet()
                .stream()
                .filter(x -> beanType.isAssignableFrom(x.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (T) e.getValue()));
    }

}
