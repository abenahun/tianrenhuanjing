package com.tianren.acommon.bean;

import java.util.Date;

public class CapacityBean {
    private Integer id;

    private Double gasProduction;//产气

    private Double powerGeneration;//产电

    private Integer entryType;

    private Double liftingCapacity;//提油量

    private Date entryTime;

    private String addTime;

    public Double getLiftingCapacity() {
        return liftingCapacity;
    }

    public void setLiftingCapacity(Double liftingCapacity) {
        this.liftingCapacity = liftingCapacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGasProduction() {
        return gasProduction;
    }

    public void setGasProduction(Double gasProduction) {
        this.gasProduction = gasProduction;
    }

    public Double getPowerGeneration() {
        return powerGeneration;
    }

    public void setPowerGeneration(Double powerGeneration) {
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