package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Configuration;

/**
 *OpenFeign的日志级别:
 *  1.NONE
 *  2.BASIC
 *  3.HEADERS
 *  4.FULL
 */
@Configuration
public class FeignConfigLog {
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
