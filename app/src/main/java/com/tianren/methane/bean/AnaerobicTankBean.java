package com.tianren.methane.bean;

/**
 * User: Lee
 * Date: 2018/4/3 0003
 * Time: 上午 9:39
 * Desc: 神兽保佑代码无bug
 */
public class AnaerobicTankBean {
    private String ph;
    private String ts;
    private String vfa;
    private String vs;
    private String cod;
    private String alkalinity;//碱度
    private String ammoniaNitrogen;//氨氮
    private String samplingPoint;//取样点
    private String entryTime;

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getVfa() {
        return vfa;
    }

    public void setVfa(String vfa) {
        this.vfa = vfa;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getAlkalinity() {
        return alkalinity;
    }

    public void setAlkalinity(String alkalinity) {
        this.alkalinity = alkalinity;
    }

    public String getAmmoniaNitrogen() {
        return ammoniaNitrogen;
    }

    public void setAmmoniaNitrogen(String ammoniaNitrogen) {
        this.ammoniaNitrogen = ammoniaNitrogen;
    }

    public String getSamplingPoint() {
        return samplingPoint;
    }

    public void setSamplingPoint(String samplingPoint) {
        this.samplingPoint = samplingPoint;
    }
}
