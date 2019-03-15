package com.lx.pk.dubbo.spi;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by chenjiang on 2018/12/14.
 * dubboApplicationConfig整合springboot核心配置详情可以参考官网给出的案例
 * http://dubbo.apache.org/en-us/
 */
@Configuration
public class LxDubboApplicationConfig {
    //注册的dubbo服务名称
    @Value("${dubbo.application.name}")
    private String name;
    //服务版本号
    @Value("${dubbo.application.version}")
    private String version;

    @Value("${dubbo.application.owner:undefined}")
    private String owner;
    //默认使用的是自己的dubbo监控
    @Resource(name = "defaultMonitor")
    private MonitorConfig monitor;

    @Value(value = "${dubbo.application.simple-monitor:false}")
    private boolean simpleMonitor;

    @Bean
    public ApplicationConfig getApplicationConfig() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(name);
        application.setVersion(version);
        application.setOwner(owner);
        if (simpleMonitor) {
            application.setMonitor(monitor);
        }
        return application;
    }

    // 简单的监控中心
    @Bean(name = "defaultMonitor")
    public MonitorConfig getMonitorConfig() {
        MonitorConfig monitor = new MonitorConfig();
        monitor.setProtocol("registry");
        return monitor;
    }
}
