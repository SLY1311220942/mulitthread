package com.sly.mulitthread.component;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.Callable;

/**
 * @author SLY
 * @description
 * @date 2020/6/11
 */
public class QueryInfoCall implements Callable<JSONObject> {

    private int count;

    public QueryInfoCall(int count) {
        this.count = count;
    }

    @Override
    public JSONObject call() throws Exception {
        // 模拟调用时间
        System.out.println("调用接口" + count);
        Thread.sleep(500);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);

        return jsonObject;
    }
}
