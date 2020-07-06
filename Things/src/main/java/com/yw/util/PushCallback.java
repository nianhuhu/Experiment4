package com.yw.util;


import com.alibaba.fastjson.JSONObject;
import com.yw.controller.MQTTController;

import com.yw.entity.Lux;
import com.yw.mapper.LuxMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Demo Class
 *
 * @Class PushCallback
 * @Author Administrator
 * @Date 2020/5/30
 * @Version Code No.1
 * @Description:
 **/
public class PushCallback implements MqttCallback {

    private static Lux lux;


    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload(),"GB2312"));
        String jsonData = new String(message.getPayload());
        lux = JSONObject.parseObject(jsonData, Lux.class);

        getLux();

    }

    public static Lux getLux(){
        return lux;
    }

}
