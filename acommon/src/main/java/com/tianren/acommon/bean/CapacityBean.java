package com.tianren.acommon.bean;

public class CapacityBean {
    private Integer id;
    private String gasProduction;
    private String powerGeneration;
    private Integer entryType;
    private String entryTime;
    private String addTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGasProduction() {
        return gasProduction;
    }

    public void setGasProduction(String gasProduction) {
        this.gasProduction = gasProduction;
    }

    public String getPowerGeneration() {
        return powerGeneration;
    }

    public void setPowerGeneration(String powerGeneration) {
        this.powerGeneration = powerGeneration;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}