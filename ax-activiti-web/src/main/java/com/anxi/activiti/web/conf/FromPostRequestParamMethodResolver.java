package com.anxi.activiti.web.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义springWeb参数解析器
 */
@Slf4j
@Component
public class FromPostRequestParamMethodResolver implements HandlerMethodArgumentResolver {

    /**
     * 检查控制层方法参数是否符合本解析器解析规则
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FormPostParam.class);
    }

    /**
     * 自定义参数绑定实现
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String name = parameter.getParameterName();

        // 查找是否已有名为name的参数值的映射，如果没有则创建一个
        Object attribute = mavContainer.containsAttribute(name) ? mavContainer.getModel().get(name) : BeanUtils.instantiateClass(parameter.getParameterType());

        if (binderFactory != null) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);

            if (binder.getTarget() != null) {
                // 进行参数绑定
                this.bindRequestParameters(binder, webRequest);
            }

            // 将参数转到预期类型，第一个参数为解析后的值，第二个参数为绑定Controller参数的类型，第三个参数为绑定的Controller参数
            attribute = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
        }

        return attribute;
    }

    private void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) throws UnsupportedEncodingException {
        // 将key-value封装为map，传给bind方法进行参数值绑定
        Map<String, String> map = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = new String(entry.getValue()[0].getBytes("ISO-8859-1"), "UTF-8");
            map.put(name, value);
        }

        PropertyValues propertyValues = new MutablePropertyValues(map);

        // 将K-V绑定到binder.target属性上
        binder.bind(propertyValues);
    }

}
