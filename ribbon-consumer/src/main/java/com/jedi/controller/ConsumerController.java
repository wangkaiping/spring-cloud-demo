package com.jedi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * 一句话简介.
 *
 * @auth: 王开苹【wangkaiping_1990@sina.com】
 * @date: 2017-08-08 13：23
 */
@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "ribbon-consumer/{name}", method = RequestMethod.GET)
    public String helloConsumer(@PathVariable String name) {
        return restTemplate.getForEntity("http://hello-service/hello/{name}", String.class, name).getBody();
    }
}
