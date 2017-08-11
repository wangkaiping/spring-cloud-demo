package com.jedi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * 一句话简介.
 *
 * @auth: 王开苹【wangkaiping_1990@sina.com】
 * @date: 2017-08-08 09：44
 */
@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String index(@PathVariable String name) throws Exception{
        List<String> services = client.getServices();

        for (String service : services) {
            List<ServiceInstance> list = client.getInstances(service);
            for (ServiceInstance instance : list) {

                // 让处理线程等待几秒钟
                int sleepTime = new Random().nextInt(3000);
                logger.info("sleepTime : " + sleepTime);
                Thread.sleep(sleepTime);

                logger.info("/hello, host : " + instance.getHost() + " , service_id : " + instance.getServiceId() + " , port : " + instance.getPort());
            }
        }

        return "hello Jedi! My name is " + name;
    }
}
