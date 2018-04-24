package com.tianren.acommon.bean;

/**
 * Created by Mr.Qiu on 2018/4/23.
 */

public class AlarmBean {
    private int alarmId;
    private int sensorId;
    private int alarmType;
    private double currentValue;
    private int isDeal;
    private int dealStaffNo;
    private String dealMessage;
    private String dealTime;
    private String alarmTime;
    private UserBean user;
    private SensorBean sensor;
    private int dealProcess;
    private String submitTime;

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public int getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(int isDeal) {
        this.isDeal = isDeal;
    }

    public int getDealStaffNo() {
        return dealStaffNo;
    }

    public void setDealStaffNo(int dealStaffNo) {
        this.dealStaffNo = dealStaffNo;
    }

    public String getDealMessage() {
        return dealMessage;
    }

    public void setDealMessage(String dealMessage) {
        this.dealMessage = dealMessage;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public SensorBean getSensor() {
        return sensor;
    }

    public void setSensor(SensorBean sensor) {
        this.sensor = sensor;
    }

    public int getDealProcess() {
        return dealProcess;
    }

    public void setDealProcess(int dealProcess) {
        this.dealProcess = dealProcess;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public static class UserBean {

        private int userId;
        private String userName;
        private String password;
        private int companyId;
        private int userNo;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getUserNo() {
            return userNo;
        }

        public void setUserNo(int userNo) {
            this.userNo = userNo;
        }
    }

    public static class SensorBean {
        private int sensorId;
        private String sensorName;
        private String nickName;
        private int companyId;
        private int moduleId;
        private double suitableMaximum;
        private double suitableMinimum;
        private double highWarningValue;
        private double lowWarningValue;
        private double highErrorValue;
        private double lowErrorValue;
        private String sensorUnit;

        public int getSensorId() {
            return sensorId;
        }

        public void setSensorId(int sensorId) {
            this.sensorId = sensorId;
        }

        public String getSensorName() {
            return sensorName;
        }

        public void setSensorName(String sensorName) {
            this.sensorName = sensorName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getModuleId() {
            return moduleId;
        }

        public void setModuleId(int moduleId) {
            this.moduleId = moduleId;
        }

        public double getSuitableMaximum() {
            return suitableMaximum;
        }

        public void setSuitableMaximum(double suitableMaximum) {
            this.suitableMaximum = suitableMaximum;
        }

        public double getSuitableMinimum() {
            return suitableMinimum;
        }

        public void setSuitableMinimum(double suitableMinimum) {
            this.suitableMinimum = suitableMinimum;
        }

        public double getHighWarningValue() {
            return highWarningValue;
        }

        public void setHighWarningValue(double highWarningValue) {
            this.highWarningValue = highWarningValue;
        }

        public double getLowWarningValue() {
            return lowWarningValue;
        }

        public void setLowWarningValue(double lowWarningValue) {
            this.lowWarningValue = lowWarningValue;
        }

        public double getHighErrorValue() {
            return highErrorValue;
        }

        public void setHighErrorValue(double highErrorValue) {
            this.highErrorValue = highErrorValue;
        }

        public double getLowErrorValue() {
            return lowErrorValue;
        }

        public void setLowErrorValue(double lowErrorValue) {
            this.lowErrorValue = lowErrorValue;
        }

        public String getSensorUnit() {
            return sensorUnit;
        }

        public void setSensorUnit(String sensorUnit) {
            this.sensorUnit = sensorUnit;
        }
    }

    @Override
    public String toString() {
        return "AlarmBean{" +
                "alarmId=" + alarmId +
                ", sensorId=" + sensorId +
                ", alarmType=" + alarmType +
                ", currentValue=" + currentValue +
                ", isDeal=" + isDeal +
                ", dealStaffNo=" + dealStaffNo +
                ", dealMessage='" + dealMessage + '\'' +
                ", dealTime='" + dealTime + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", user=" + user +
                ", sensor=" + sensor +
                ", dealProcess=" + dealProcess +
                ", submitTime='" + submitTime + '\'' +
                '}';
    }
}
