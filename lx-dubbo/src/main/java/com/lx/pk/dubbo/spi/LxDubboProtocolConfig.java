package com.lx.pk.dubbo.spi;

import com.alibaba.dubbo.config.ProtocolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by chenjiang on 2018/12/14.
 * dubbo协议配置类
 */
@Configuration
public class LxDubboProtocolConfig {
    @Value("${dubbo.protocol.dubbo.port:20880}")
    private int port;

    @Value("${dubbo.protocol.dubbo.threads:20}")
    private int threads;

    // 服务提供者协议配置
    @Bean(name = "dubboProtocol")
    public ProtocolConfig getProtocolConfig() {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(port);
        protocol.setThreads(threads);
        return protocol;
    }
}
