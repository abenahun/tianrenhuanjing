package com.tianren.methane.bean;

public class CapacityBean {
    private String id;

    private String gasProduction;

    private String powerGeneration;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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