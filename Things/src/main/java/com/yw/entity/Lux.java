package com.yw.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Demo Class
 *
 * @Class Customer
 * @Author Administrator
 * @Date 2020/5/30
 * @Version Code No.1
 * @Description:
 **/
public class Lux{

    private Integer luxId;

    private String luxNumber;


    @DateTimeFormat
    private Date luxTime;

    public Integer getLuxId() {
        return luxId;
    }

    public void setLuxId(Integer luxId) {
        this.luxId = luxId;
    }

    public String getLuxNumber() {
        return luxNumber;
    }

    public void setLuxNumber(String luxNumber) {
        this.luxNumber = luxNumber;
    }

    public Date getLuxTime() {
        return luxTime;
    }

    public void setLuxTime(Date luxTime) {
        this.luxTime = luxTime;
    }

    @Override
    public String toString() {
        return "Lux{" +
                "luxId=" + luxId +
                ", luxNumber='" + luxNumber + '\'' +
                ", luxTime=" + luxTime +
                '}';
    }
}
