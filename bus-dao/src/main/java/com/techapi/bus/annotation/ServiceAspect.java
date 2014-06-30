package com.techapi.bus.annotation;


import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techapi.bus.util.TTL;

/***
 * aop拦截器,统一处理缓存
 * @ClassName: ServiceAspect
 * @author jiayusun@sohu-inc.com
 */
@Component("ServiceAspect")
@Aspect
public class ServiceAspect {

	private static final Log log = LogFactory.getLog(CacheProxyJedisImpl.class);
	
    public static final Model m = Model.ON;
    @Autowired
    public CacheProxy cacheProxy;
    
    @Pointcut("execution(@com.techapi.bus.annotation.ServiceCache * *(..))")  
    private void pointCutMethod() {  
        
    }  
    @Around("pointCutMethod()") 
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] arg = pjp.getArgs();    
        String methodName = pjp.getSignature().getName();
        String key = createMemacheKey(methodName,arg);
        Object st = cacheProxy.get(key);
        if(st!=null&&m==Model.ON){
            return st;
        }else{
            Object retVal = pjp.proceed();
            try {
            	cacheProxy.put(key, retVal,getMethodTTL(pjp.getTarget().getClass(),methodName).getTime());
            } catch (Exception e) {
            	log.error("redis set error: key is "+key,e);
            }
            return retVal;
        }
    }

    public String createMemacheKey(String methodName,Object[] arg){
        StringBuffer sb = new StringBuffer(methodName);
        for(Object o :arg){
            sb.append(":");
            if(o==null){
                sb.append("++");
            }else{
                sb.append(o.toString());
            }
        }
        return sb.toString();
    }
    
    public TTL getMethodTTL(Class<? extends Object> clazz,String methodName) throws Exception{
        Method[] methods = clazz.getMethods();
        for(Method method:methods){
            if(methodName.equals(method.getName())){
                if(method.isAnnotationPresent(ServiceCache.class)){
                    ServiceCache sc =    method.getAnnotation(ServiceCache.class); 
                    return sc.value();
                }
            }
        }
         throw  new RuntimeException("aop aspect execution @ServiceCache get class not contain @serviceCache");
    }
    
    enum Model{
        ON,OFF
    }

    public void doBefore(JoinPoint jp) {
    }
    public void doThrowing(JoinPoint jp, Throwable ex) {
    }
    public void doAfter(JoinPoint jp) {
    }

}
