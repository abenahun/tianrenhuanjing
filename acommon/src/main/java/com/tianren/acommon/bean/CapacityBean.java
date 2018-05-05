package com.tianren.acommon.bean;

import java.util.Date;

public class CapacityBean {
    private Integer id;

    private Integer gasProduction;

    private Integer powerGeneration;

    private Integer entryType;

    private Date entryTime;

    private String addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGasProduction() {
        return gasProduction;
    }

    public void setGasProduction(Integer gasProduction) {
        this.gasProduction = gasProduction;
    }

    public Integer getPowerGeneration() {
        return powerGeneration;
    }

    public void setPowerGeneration(Integer powerGeneration) {
        this.powerGeneration = powerGeneration;
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

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}