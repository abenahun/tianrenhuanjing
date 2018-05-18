package com.tianren.acommon.bean;

public class CapacityBean {
    private Integer id;

    private Double gasProduction;//产气

    private Double powerGeneration;//产电

    private Integer entryType;

    private Double liftingCapacity;//提油量

    private String entryTime;

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