package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
        @Autowired
        private PaymentService paymentService;
        @Autowired
        private DiscoveryClient discoveryClient;
        @Value("${server.port}")
        private String serverPort;
        @PostMapping(value = "/payment/create")
        public CommonResult create(@RequestBody Payment payment){
            int result = paymentService.create(payment);
            if(result > 0){
                //log.info("**********插入数据成功**********"+result+"条儿");
                return new CommonResult(200,"端口:"+serverPort+"插入数据成功",result);
            }else{
                //log.info("**************插入数据失败***************");
                return new CommonResult(444,"端口:"+serverPort+"插入数据失败",null);
            }
        }
        @GetMapping(value = "/payment/get/{id}")
        public CommonResult getPaymentById(@PathVariable("id") Long id){
            Payment result = paymentService.getPaymentById(id);
            if(null != result){
                //log.info("**********插入数据成功**********"+result+"条儿");
                return new CommonResult(200,"端口:"+serverPort+"查询数据成功",result);
            }else{
                //log.info("**************插入数据失败***************");
                return new CommonResult(444,"端口:"+serverPort+"查询数据失败",null);
            }
        }
        @GetMapping("/payment/discover")
        public Object discoveryService(){
            List<String> instance = discoveryClient.getServices();
            for (String element:instance) {
                log.info("*********element:"+element);
            }
            List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
            for (ServiceInstance instance02:instances) {
                log.info(instance02.getInstanceId()+"\t"+instance02.getHost()+"\t"+instance02.getPort()+"\t"+instance02.getUri());
            }
            return this.discoveryClient;
        }

    @GetMapping("/payment/zk")
    public String paymentZk(){

        return "spring cloud zookeeper:"+serverPort+"\t"+ UUID.randomUUID().toString();
    }
}
