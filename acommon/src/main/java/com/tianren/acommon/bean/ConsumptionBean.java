package com.tianren.acommon.bean;

import java.util.Date;

public class ConsumptionBean {
    private Integer id;

    private Double powerConsumption;

    private Double waterConsumption;//水耗

    private Double airConsumption;//气耗

    private Double energyConsumption;//电耗

    private Double feedAmount;//进料量

    private Integer entryType;

    private Date entryTime;

    private Date addTime;

    public Double getFeedAmount() {
        return feedAmount;
    }

    public void setFeedAmount(Double feedAmount) {
        this.feedAmount = feedAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public Double getWaterConsumption() {
        return waterConsumption;
    }

    public void setWaterConsumption(Double waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public Double getAirConsumption() {
        return airConsumption;
    }

    public void setAirConsumption(Double airConsumption) {
        this.airConsumption = airConsumption;
    }

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public Integer getEntryType() {
        return entryType;
    }

    public void setEntryType(Integer entryType) {
        this.entryType = entryType;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}