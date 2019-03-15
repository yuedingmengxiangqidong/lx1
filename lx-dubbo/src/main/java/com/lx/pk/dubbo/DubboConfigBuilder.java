package com.lx.pk.dubbo;

import com.alibaba.dubbo.config.ReferenceConfig;

/**
 * 这是一个简单的dubbo服务配置生成器
 */
public interface DubboConfigBuilder {

    public <T> ReferenceConfig<T> build(Class<T> cls, String version);
}
