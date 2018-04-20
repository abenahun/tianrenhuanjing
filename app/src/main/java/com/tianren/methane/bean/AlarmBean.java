package com.tianren.methane.bean;

import java.util.Date;

/**
 * @author Mr.Qiu
 * @date 2018/4/20
 */
public class AlarmBean {
    private Integer id;

    private Integer sensorId;

    private Integer alarmType;

    private Double currentValue;

    private Integer isDeal;

    private Integer dealStaffNo;

    private Integer dealProcess;

    private String dealMessage;

    private Date dealTime;

    private Date alarmTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }

    public Integer getDealStaffNo() {
        return dealStaffNo;
    }

    public void setDealStaffNo(Integer dealStaffNo) {
        this.dealStaffNo = dealStaffNo;
    }

    public Integer getDealProcess() {
        return dealProcess;
    }

    public void setDealProcess(Integer dealProcess) {
        this.dealProcess = dealProcess;
    }

    public String getDealMessage() {
        return dealMessage;
    }

    public void setDealMessage(String dealMessage) {
        this.dealMessage = dealMessage == null ? null : dealMessage.trim();
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }
}
