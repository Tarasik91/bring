package com.bring.context;

import com.bring.annotation.Bean;

public class BeanNameService {

    public String generateName(Class<?> obj) {
        Bean bean = obj.getAnnotation(Bean.class);
        if (bean.value() != null && !bean.value().isEmpty()) {
            return bean.value();
        }
        String simpleName = obj.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }
}
