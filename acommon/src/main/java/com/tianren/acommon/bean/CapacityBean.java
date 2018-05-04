package com.tianren.acommon.bean;

public class CapacityBean {
    private Integer id;

    private Integer gasProduction;

    private Integer powerGeneration;

    private Integer entryType;

    private String entryTime;

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

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}