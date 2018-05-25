package com.tianren.acommon.bean;

public class AnaerobicTank {
    private Integer anaerobicTankId;

    private String samplingPoint;

    private Double ph;

    private Double ts;

    private Double vs;

    private Double vfa;

    private Double alkalinity;

    private Double ammoniaNitrogen;

    private Double cod;

    private String entryTime;

    private String addTime;

    public AnaerobicTank() {

    }

    public Integer getAnaerobicTankId() {
        return anaerobicTankId;
    }

    public void setAnaerobicTankId(Integer anaerobicTankId) {
        this.anaerobicTankId = anaerobicTankId;
    }

    public String getSamplingPoint() {
        return samplingPoint;
    }

    public void setSamplingPoint(String samplingPoint) {
        this.samplingPoint = samplingPoint;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getTs() {
        return ts;
    }

    public void setTs(Double ts) {
        this.ts = ts;
    }

    public Double getVs() {
        return vs;
    }

    public void setVs(Double vs) {
        this.vs = vs;
    }

    public Double getVfa() {
        return vfa;
    }

    public void setVfa(Double vfa) {
        this.vfa = vfa;
    }

    public Double getAlkalinity() {
        return alkalinity;
    }

    public void setAlkalinity(Double alkalinity) {
        this.alkalinity = alkalinity;
    }

    public Double getAmmoniaNitrogen() {
        return ammoniaNitrogen;
    }

    public void setAmmoniaNitrogen(Double ammoniaNitrogen) {
        this.ammoniaNitrogen = ammoniaNitrogen;
    }

    public Double getCod() {
        return cod;
    }

    public void setCod(Double cod) {
        this.cod = cod;
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