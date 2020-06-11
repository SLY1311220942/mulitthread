package com.sly.mulitthread.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author SLY
 * @description TODO
 * @date 2020/6/11
 */
@Service
public class DemoService {
    public JSONObject query(int count) {
        JSONObject jsonObject = new JSONObject();
        try {
            System.out.println("调用接口" + count);
            Thread.sleep(500);
            jsonObject.put("status", 200);
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put("status", 400);
            return jsonObject;
        }

    }
}
