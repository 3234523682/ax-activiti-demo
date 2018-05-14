package com.anxi.activiti.web.conf;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义springWeb参数解析器
 */
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
        //获取类包括所有父类的所有字段
        List<Field> list = this.getClassCoverAllSuperAllField(parameter.getParameterType());
        Object o = BeanUtils.instantiate(parameter.getParameterType());
        //请求参数名
        String paramName;
        //请求参数名 Iterator
        Iterator<String> itr = webRequest.getParameterNames();

        //参数注入处理逻辑
        while (itr.hasNext()) {
            paramName = itr.next();
            for (Field field : list) {
                //将请求参数名与实体属性名或注解值匹配，匹配成功则进行赋值操作
                if (field.getType() == String.class && paramName.equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(o, new String(webRequest.getParameter(paramName).getBytes("ISO-8859-1"), "UTF-8"));
                }
            }
        }

        return o;
    }

    private List<Field> getClassCoverAllSuperAllField(Class nowClass) {
        List<Field> list = new ArrayList<>();
        Field[] declaredFields;
        //获取类 + 所有父类 所有字段
        while (Object.class != nowClass) {
            declaredFields = nowClass.getDeclaredFields();
            list.addAll(Arrays.asList(declaredFields));
            nowClass = nowClass.getSuperclass();
        }
        return list;
    }
}
