package com.tianren.acommon.bean;

public class CapacityBean {
    private String id;

    private String gasProduction;

    private String powerGeneration;
    private String entryType;
    private String entryTime;

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}