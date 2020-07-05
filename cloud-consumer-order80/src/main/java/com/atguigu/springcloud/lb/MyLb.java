package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLb implements MyLoadBalanced {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    //拿到访问服务器的请求次数next
    public final int getAndIncrement(){
        int current;
        int next;
        do{
            current = this.atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*************next:"+next);
        return next;
    }
    @Override//选出服务所对应的具体机器
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //计算公式:器请求的次数 %
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
