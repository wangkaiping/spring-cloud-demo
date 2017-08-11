package com.jedi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * 一句话简介.
 *
 * @auth: 王开苹【wangkaiping_1990@sina.com】
 * @date: 2017-08-11 16：15
 */
@Service
public class HelloService {

    private final Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    RestTemplate restTemplate;

    /**
     * Created with com.jedi.service.HelloService
     *
     * @HystrixCommand 指定回调函数
     *
     * @auth: 王开苹【wangkaiping_1990@sina.com】
     *
     * @date: 2017-08-11 16:17:50
     */
    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloService(String name) {
        long start = System.currentTimeMillis();

        // 为了精确的观察断路器的触发，在此调用了线程等待耗时
        final ExecutorService exec = Executors.newFixedThreadPool(1);

        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //开始执行耗时操作
                Thread.sleep(1000 * 5);
                return "线程执行完成.";
            }
        };

        try {
            Future<String> future = exec.submit(call);
            String obj = future.get(1000 * 5, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("处理失败.");
            e.printStackTrace();
        }
        // 关闭线程池
        exec.shutdown();
        long end = System.currentTimeMillis();

        logger.info("Spend time : " + (end - start));

        return restTemplate.getForEntity("http://hello-service/hello/{name}", String.class, name).getBody();
    }

    public String helloFallback(String name){
        return "can't get hello method, this name is " + name;
    }
}
