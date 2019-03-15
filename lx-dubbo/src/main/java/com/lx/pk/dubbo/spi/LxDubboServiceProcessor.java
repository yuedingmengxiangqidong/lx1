package com.lx.pk.dubbo.spi;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lx.pk.dubbo.DubboService;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by chenjiang on 2018/12/14.
 * 服务注册 扫描类
 */
@Component
public class LxDubboServiceProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationConfig application;

    @Resource(name = "dubboProtocol")
    private ProtocolConfig protocol;

    @Resource(name = "defaultRegistry")
    private RegistryConfig registry;

    @Value(value = "${dubbo.export.timeout:30000}")
    private int timeout;

    @Value(value = "${dubbo.export.retries:0}")
    private int retries;

    @Value(value = "${dubbo.export.accesslog:false}")
    private String accesslog;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = getBeanClass(bean);
        if (beanClass == null) {
            return bean;
        }
        //获取自定义的注解服务@DubboService

        DubboService annotation = AnnotationUtils.findAnnotation(beanClass, DubboService.class);
        if (annotation == null) {
            return bean;
        }

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        ServiceConfig<Object> service = new ServiceConfig<>();
        // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(this.getServiceInterface(beanClass));
        service.setRef(bean);
        service.setVersion(application.getVersion());
        service.setRetries(this.retries);
        service.setTimeout(this.timeout);
        service.setAccesslog(this.accesslog);
        // 暴露及注册服务
        service.export();
        return bean;
    }

    private Class<?> getBeanClass(Object bean) {
        Class<?> beanClass = null;
        if (bean instanceof Advised) {
            Advised advised = (Advised) bean;
            beanClass = advised.getTargetClass();
        } else {
            beanClass = bean.getClass();
        }
        return beanClass;
    }

    private Class<?> getServiceInterface(Class<?> beanClass) {
        Class<?>[] classes = beanClass.getInterfaces();
        if (classes.length == 0) {
            throw new RuntimeException("dubbo服务必须实现一个接口");
        } else if (classes.length > 1) {
            throw new RuntimeException("dubbo服务只能实现一个接口");
        }
        return classes[0];
    }
}
