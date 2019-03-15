package com.lx.pk.dubbo.spi;

import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenjiang on 2018/12/14.
 * 服务注册类
 */
@Configuration
public class LxDubboRegistryConfig {
    //dubbo注册地址
    @Value("${dubbo.registry.address}")
    private String registryAddress;
    //zookeeper进行连接方式
    @Value("${dubbo.registry.client:zkclient}")
    private String registryClient;

    // 连接注册中心配置
    @Bean(name = "defaultRegistry")
    public RegistryConfig getRegistryConfig() {
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(this.registryAddress);
        registry.setCheck(false);
        registry.setClient(registryClient);
        return registry;
    }
}
