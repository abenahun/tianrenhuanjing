package com.tianren.methane.bean;

import java.util.Date;

public class ConsumptionBean {
    private String id;

    private String entryType;

    private String waterConsumption;

    private String powerConsumption;

    private String airConsumption;

    private String entryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getWaterConsumption() {
        return waterConsumption;
    }

    public void setWaterConsumption(String waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public String getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(String powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getAirConsumption() {
        return airConsumption;
    }

    public void setAirConsumption(String airConsumption) {
        this.airConsumption = airConsumption;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }
}