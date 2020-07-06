package com.yw.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yw.entity.Lux;
import com.yw.entity.User;
import com.yw.mapper.LuxMapper;
import com.yw.util.MQTTUtil;
import com.yw.util.PushCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


/**
 * Demo Class
 *
 * @Class MQTTController
 * @Author Administrator
 * @Date 2020/5/29
 * @Version Code No.1
 * @Description:
 **/
@Controller
public class MQTTController {

    private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    private LuxMapper luxMapper = ioc.getBean("luxMapper", LuxMapper.class);


    /**
     * 进行客户端连接
     *
     * @param user
     * @return int
     * @Author Administrator
     **/
    @RequestMapping("/sendMessage")
    @ResponseBody
    public int sendMessage(@RequestBody User user) throws MqttException {

        if (!"".equals(user.getUsername()) || user.getUsername() != null || !"".equals(user.getPassword()) || user.getPassword() != null
                || !"\n".equals(user.getUsername()) || !"\t".equals(user.getUsername()) || !"\n".equals(user.getPassword()) || !"\t".equals(user.getPassword())) {
            MQTTUtil.USERNAME = user.getUsername();
            MQTTUtil.PWD = user.getPassword();
            MQTTUtil.start();
            if (MQTTUtil.client.isConnected()) {

                return 1;
            } else {

                return 0;
            }

        } else {
            return 0;
        }

    }

    /**
     * 进行历史数据保存
     *
     * @param lux
     * @return
     * @Author Administrator
     **/
    @RequestMapping("/historyData")
    @ResponseBody
    public void historyData(@RequestBody Lux lux) {
        System.out.println("输出lux:" + lux);
        saveLux(lux.getLuxNumber());
    }

    /**
     * 进行开关灯数据展示
     *
     * @param
     * @return java.util.List
     * @Author Administrator
     **/
    @RequestMapping("/switchData")
    @ResponseBody
    public List switchData(@RequestBody String date) throws Exception {

        JSONObject jsonData = JSONObject.parseObject(date);
        List<Lux> list = luxMapper.selectList(new EntityWrapper<Lux>()
                .like("lux_time", jsonData.getString("date"))
                .orderDesc(Collections.singleton("lux_id"))
                .last("limit 2"));
        return list;
    }

    /**
     * 将从服务器防发过来的数据展示到前端
     *
     * @param
     * @return String
     * @Author Administrator
     **/
    @RequestMapping("/showMessage")
    @ResponseBody
    public String showMessage(@RequestBody Lux lux) {
        lux = PushCallback.getLux();
        return lux.getLuxNumber();
    }

    /**
     * 保存数据到数据库
     *
     * @param
     * @return java.lang.void
     * @Author Administrator
     **/
    public void saveLux(String number) {

        Lux lux = new Lux();
        lux.setLuxNumber(number);
        lux.setLuxTime(new java.util.Date());
        System.out.println(lux);
        Integer integer = luxMapper.insertAllColumn(lux);

    }

}
