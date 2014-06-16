package com.techapi.bus.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.techapi.bus.util.TTL;

/***
 * 方法层memcache缓存注解
* @ClassName: ServiceCache 
* @author jiayusun@sohu-inc.com 
* @date 2014-6-3 下午6:56:07 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceCache {
    public TTL value() default TTL._30M;
}
