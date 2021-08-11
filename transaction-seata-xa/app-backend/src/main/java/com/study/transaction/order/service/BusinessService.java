package com.study.transaction.order.service;

import com.alibaba.fastjson.JSONObject;
import io.seata.integration.http.DefaultHttpExecutor;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;

/**
 * @author will
 */
@Service
public class BusinessService {

    /**
     * 下订单、派发运单
     */
    @GlobalTransactional(timeoutMills = 600000)
    public void createOrder(JSONObject orderInfo) throws Exception {
        callOrderHttpApi(orderInfo);  // 1. 订单接口
        callDispatchHttpApi(orderInfo); // 2. 运单接口
        int i = 1 / 0; // 人造异常
    }

    /**
     * 通过http接口发送 订单系统，将订单号传过去
     *
     * @return 接口调用结果
     */
    public String callOrderHttpApi(JSONObject orderInfo) throws IOException {
        DefaultHttpExecutor httpExecuter = DefaultHttpExecutor.getInstance();
        HttpResponse httpResponse = httpExecuter.executePost(
                "http://127.0.0.1:8080/order-api/order",
                "",
                orderInfo, HttpResponse.class);
        String result = readStreamAsStr(httpResponse.getEntity().getContent());
        return result;
    }

    /**
     * 通过http接口发送 运单系统，将订单号传过去
     *
     * @return 接口调用结果
     */
    public String callDispatchHttpApi(JSONObject orderInfo) throws IOException {
        DefaultHttpExecutor httpExecuter = DefaultHttpExecutor.getInstance();
        HttpResponse httpResponse = httpExecuter.executeGet(
                "http://127.0.0.1:8081/dispatch-api/dispatch?orderId=" + orderInfo.getString("orderId"),
                "",
                new HashMap<>(), HttpResponse.class);
        String result = readStreamAsStr(httpResponse.getEntity().getContent());
        return result;
    }


    public static String readStreamAsStr(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel dest = Channels.newChannel(bos);
        ReadableByteChannel src = Channels.newChannel(is);
        ByteBuffer bb = ByteBuffer.allocate(4096);

        while (src.read(bb) != -1) {
            bb.flip();
            dest.write(bb);
            bb.clear();
        }

        src.close();
        dest.close();
        return new String(bos.toByteArray(), "UTF-8");
    }
}
