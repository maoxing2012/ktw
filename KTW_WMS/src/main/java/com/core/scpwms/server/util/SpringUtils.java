package com.core.scpwms.server.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.MethodInvoker;

public class SpringUtils implements ApplicationContextAware {
    static Log logger = LogFactory.getLog(SpringUtils.class);
    private static ApplicationContext applicationContext;

    private SpringUtils() {
    }

    public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
        applicationContext = _applicationContext;
    }

    /**
     * 获取Bean对象
     * 
     * @param name
     * @return bean对象
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取Bean对象
     * 
     * @param name
     * @param requiredType
     * @return bean
     */
    public static Object getBean(String name, Class requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 运行Bean中指定名称的方法并返回其返回值
     * 
     * @param beanName
     * @param methodName
     * @param arguments
     * @return 
     */
    public static Object invokeBeanMethod(String beanName, String methodName, Object[] arguments) {
        try {
            // 初始化
            MethodInvoker methodInvoker = new MethodInvoker();
            methodInvoker.setTargetObject(SpringUtils.getBean(beanName));
            methodInvoker.setTargetMethod(methodName);

            // 设置参数
            if (arguments != null && arguments.length > 0) {
                methodInvoker.setArguments(arguments);
            }

            // 准备方法
            methodInvoker.prepare();

            // 执行方法
            return methodInvoker.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return null;
        }
    }
}
