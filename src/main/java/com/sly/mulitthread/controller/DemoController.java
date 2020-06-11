package com.sly.mulitthread.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sly.mulitthread.component.QueryInfoCall;
import com.sly.mulitthread.service.DemoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author SLY
 * @description
 * @date 2020/6/11
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DemoService demoService;

    @ResponseBody
    @RequestMapping("/serialRequest")
    public Map<String, Object> serialRequest() {
        Map<String, Object> result = new HashMap<>(16);
        try {
            long start = System.currentTimeMillis();
            demoService.query(1);
            demoService.query(2);
            demoService.query(3);
            demoService.query(4);
            demoService.query(5);

            long end = System.currentTimeMillis();


            result.put("status", 200);
            result.put("message", "响应时间：" + (end - start));
            return result;
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            result.put("status", 400);
            result.put("message", "错误");
            return result;
        }
    }


    @ResponseBody
    @RequestMapping("/parallelRequest")
    public Map<String, Object> parallelRequest() {
        Map<String, Object> result = new HashMap<>(16);
        try {
            long start = System.currentTimeMillis();

            ExecutorService executorService = Executors.newFixedThreadPool(5);

            List<Future<JSONObject>> futures = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                //申请单个线程执行类
                QueryInfoCall queryInfoCall = new QueryInfoCall(i + 1);
                //提交单个线程
                Future<JSONObject> future = executorService.submit(queryInfoCall);
                //将每个线程放入线程集合， 这里如果任何一个线程的执行结果没有回调，线程都会自动堵塞
                futures.add(future);

            }

            for (Future<JSONObject> future : futures) {
                JSONObject jsonObject = future.get();
            }

            long end = System.currentTimeMillis();


            result.put("status", 200);
            result.put("message", "响应时间：" + (end - start));
            return result;
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            result.put("status", 400);
            result.put("message", "错误");
            return result;
        }
    }
}
