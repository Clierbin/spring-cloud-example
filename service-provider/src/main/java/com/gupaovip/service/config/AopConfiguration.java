package com.gupaovip.service.config;

import com.gupaovip.service.annotation.Limited;
import com.gupaovip.service.annotation.Timeout;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.*;

/**
 * ClassName:AopConfiguration
 * Package:com.gupaovip.service.config
 * description
 * Created by zhangbin on 2019/9/27.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/27 18:51
 */
@Aspect
@Configuration
public class AopConfiguration {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private Map<Method, Semaphore> semaphoreCache = new ConcurrentHashMap<>();

    @Around("@annotation(com.gupaovip.service.annotation.Limited)")
    public Object aroundLimitedMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue = null;

        Signature signature = pjp.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            // 3.1 根据属性构造 Semaphore(10)
            Semaphore semaphore = getSemaphore(method);
            // 4. 在方法主动调用之前,Semaphore#acquire()   -1
            try {
                semaphore.acquire();
                // 5. 被拦截的方法执行
                returnValue = pjp.proceed();
            } finally {
                // 6. 在方法执行后,调用 Semaphore#relesase()   +1
                semaphore.release();
            }
        }

        return returnValue;
    }

    /**
     * 获取方法对应的信号量
     * @param method
     * @return
     */
    public Semaphore getSemaphore(Method method) {
        return semaphoreCache.computeIfAbsent(method, k -> {
            // 1. 得到 @Limited 注解
            Limited limited = method.getAnnotation(Limited.class);
            // 2. 得到 @Limited 注解中的属性 10
            int perimits = limited.value();
            // 3 根据属性构造 Semaphore(10)
            return new Semaphore(perimits);
        });
    }


    // 1. 拦截处理方法 (Spring + Aspectj)
    @Around("@annotation(com.gupaovip.service.annotation.Timeout)")
    public Object aroundTimeoutMethodInvocation(ProceedingJoinPoint pjp) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object returnValue = null;

        Signature signature = pjp.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            // 3. 通过Method 获取标注的Timeout 注解
            Timeout timeout = method.getAnnotation(Timeout.class);
            if (timeout != null) { // 如果标注的话
                // 4. 获取@Timeout 注解中的属性
                long value = timeout.value();
                TimeUnit timeUnit = timeout.timeUnit();
                String fallbackMethodName = timeout.fallback();
                // 方法参数
                Object[] arguments = pjp.getArgs();
                // 5. 根据以上数据构造 Future 超时时间
                Future<Object> future = executorService.submit(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        try {
                            return pjp.proceed(arguments);
                        } catch (Throwable throwable) {
                           throw new Exception(throwable);
                        }
                    }
                });
                try {
                    // 6. 执行被拦截的方法
                    returnValue = future.get(value, timeUnit);
                } catch (TimeoutException | InterruptedException | ExecutionException e) {
                    if (!"".equals(fallbackMethodName)) {
                        // 7. 如果失败,调用 fallbackMethodName 方法
                        returnValue = invokeFallbackMethod(method, pjp.getTarget(), arguments, fallbackMethodName);
                    } else {
                        e.printStackTrace();
                    }
                } finally {
                    if (returnValue == null) {
                        returnValue = "FALLBACK 2";
                    }

                }
            }
        }
        return returnValue;
    }
    public Object invokeFallbackMethod(Method method, Object bean, Object[] arguments, String fallback) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 7.1 查找 fallback 方法
        Method fallbackMethod = findFallbackMethod(method, bean, fallback);
        return fallbackMethod.invoke(bean,arguments);
    }

    public Method findFallbackMethod(Method method, Object bean, String fallbackMethodName) throws NoSuchMethodException {
        // 通过被拦截方法的参数类型列表 结合方法名 , 从同一类中找到fallback 方法
        Class beanClass = bean.getClass();
        Method fallBackMethod = beanClass.getMethod(fallbackMethodName,method.getParameterTypes());
        return fallBackMethod;
    }

}
