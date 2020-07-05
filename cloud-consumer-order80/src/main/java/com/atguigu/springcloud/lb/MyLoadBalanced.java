package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyLoadBalanced {
    //从注册中心中获取服务所对应的机器数量
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
