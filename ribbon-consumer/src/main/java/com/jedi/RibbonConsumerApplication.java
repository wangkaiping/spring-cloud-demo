package com.jedi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker // 断路器开启
public class RibbonConsumerApplication {

	/**
	 * Created with com.jedi.RibbonConsumerApplication
	 *
	 * 创建RestTemplate的Spring Bean	 实例，并通过@LoadBalanced 注解开启客户端负载均衡
	 *
	 * @auth: 王开苹【wangkaiping_1990@sina.com】
	 *
	 * @date: 2017-08-08 11:46:44
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(RibbonConsumerApplication.class, args);
	}
}
