package com.lx.pk.dubbo;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 这是一个简单的dubbo服务注解配置,
 * <p>
 * 该注解提供了一些常见的默认参数 如果有复杂的参数配置的话请参考dubbo提供的xml
 * </p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DubboService {

}
