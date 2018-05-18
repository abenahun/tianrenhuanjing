package com.tianren.acommon.bean;

import java.util.Date;

public class SensorBean {
    private Integer sensorId;

    private String sensorName;

    private String nickName;

    private Integer companyId;

    private Integer moduleId;

    private Double suitableMaximum;

    private Double suitableMinimum;

    private Double highWarningValue;

    private Double lowWarningValue;

    private String warningMessage;

    private Double highErrorValue;

    private Double lowErrorValue;

    private String sensorUnit;

    private String errorMessage;

    private String sensorDesc;

    private Date addTime;

    private Date modifyTime;

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName == null ? null : sensorName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Double getSuitableMaximum() {
        return suitableMaximum;
    }

    public void setSuitableMaximum(Double suitableMaximum) {
        this.suitableMaximum = suitableMaximum;
    }

    public Double getSuitableMinimum() {
        return suitableMinimum;
    }

    public void setSuitableMinimum(Double suitableMinimum) {
        this.suitableMinimum = suitableMinimum;
    }

    public Double getHighWarningValue() {
        return highWarningValue;
    }

    public void setHighWarningValue(Double highWarningValue) {
        this.highWarningValue = highWarningValue;
    }

    public Double getLowWarningValue() {
        return lowWarningValue;
    }

    public void setLowWarningValue(Double lowWarningValue) {
        this.lowWarningValue = lowWarningValue;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage == null ? null : warningMessage.trim();
    }

    public Double getHighErrorValue() {
        return highErrorValue;
    }

    public void setHighErrorValue(Double highErrorValue) {
        this.highErrorValue = highErrorValue;
    }

    public Double getLowErrorValue() {
        return lowErrorValue;
    }

    public void setLowErrorValue(Double lowErrorValue) {
        this.lowErrorValue = lowErrorValue;
    }

    public String getSensorUnit() {
        return sensorUnit;
    }

    public void setSensorUnit(String sensorUnit) {
        this.sensorUnit = sensorUnit == null ? null : sensorUnit.trim();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage == null ? null : errorMessage.trim();
    }

    public String getSensorDesc() {
        return sensorDesc;
    }

    public void setSensorDesc(String sensorDesc) {
        this.sensorDesc = sensorDesc == null ? null : sensorDesc.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}