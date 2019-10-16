/*
package com.gupaovip.service.web.servlet;

import com.gupaovip.service.annotation.Timeout;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.stream.Stream;

*/
/**
 * ClassName:TimeoutAnnotationHandlerInterceptor
 * Package:com.gupaovip.service.web.servlet
 * description
 * Created by zhangbin on 2019/9/27.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/27 16:18
 * {@link Timeout Timeout} 注解处理 Web MVV 拦截器
 * @see HandlerInterceptor
 * @see Timeout
 *//*

public class TimeoutAnnotationHandlerInterceptor implements HandlerInterceptor {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 1. 拦截处理方法 (Spring MVC 内建 HandlerInterceptor)
        // 2. 得到被拦截的方法对象 (handler 在 Sping MVC 中, 永远是 HandlerMethod)
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 2.1 通过HandlerMethod 获取 Method 对象
            Method method = handlerMethod.getMethod();
            // 3. 通过Method 获取标注的Timeout 注解
            Timeout timeout = method.getAnnotation(Timeout.class);
            if (timeout != null) { // 如果标注的话
                // 4. 获取@Timeout 注解中的属性
                Object bean = handlerMethod.getBean();
                long value = timeout.value();
                TimeUnit timeUnit = timeout.timeUnit();
                String fallbackMethodName = timeout.fallback();
                // 5. 根据以上数据构造 Future 超时时间
                Future<Object> future = executorService.submit(() -> method.invoke(bean));
                Object returnValue = null;
                try {
                    // 6. 执行被拦截的方法
                    returnValue = future.get(value, timeUnit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    if (!"".equals(fallbackMethodName)) {
                        // 7. 如果失败,调用 fallbackMethodName 方法
                        returnValue = invokeFallbackMethod(handlerMethod, bean, fallbackMethodName);
                    } else {
                        e.printStackTrace();
                    }
                } finally {
                    if (returnValue == null) {
                        returnValue = "FALLBACK 2";
                    }
                    // 8. 返回执行结果(当前实现存在缺陷, 大家可以尝试通过 HandlerMethodReturnValueHandler 实现)
                    response.getWriter().write(String.valueOf(returnValue));
                    return false;
                }
            }
        }

        return true;
    }

    public Object invokeFallbackMethod(HandlerMethod handlerMethod, Object bean, String fallback) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 7.1 查找 fallback 方法
        Method method = findFallbackMethod(handlerMethod, bean, fallback);
        return method.invoke(bean);
    }

    public Method findFallbackMethod(HandlerMethod handlerMethod, Object bean, String fallbackMethodName) throws NoSuchMethodException {
        // 通过被拦截方法的参数类型列表 结合方法名 , 从同一类中找到fallback 方法
        Class beanClass = bean.getClass();
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        Class[] parameterTypes = Stream.of(methodParameters)
                .map(MethodParameter::getParameterType) // Class
                .toArray(Class[]::new);                 // Stream(Class) -> Class[]
        Method fallBackMethod = beanClass.getMethod(fallbackMethodName, parameterTypes);
        return fallBackMethod;
    }
}
*/
