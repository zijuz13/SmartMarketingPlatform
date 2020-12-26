package com.huajun123.utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import java.lang.reflect.Field;

@Component
public class GenericCriteriaUtils {
    public <T> void addGenericCriteria(Example.Criteria criteria,Class<T> tClass,T obj) throws IllegalAccessException {
        for (Field declaredField : tClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Object o = declaredField.get(obj);
            //只有是string类型才可以进行模糊查询
            if(null!=o&&(o instanceof String)&&!StringUtils.isEmpty(o.toString())){
                criteria.andCondition("  "+declaredField.getName()+" like  "+"'"+'%'+o+'%'+"'");
            }
        }
    }
}
