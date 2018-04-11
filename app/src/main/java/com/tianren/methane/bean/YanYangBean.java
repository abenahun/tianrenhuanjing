package com.tianren.methane.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/27.
 */

public class YanYangBean {

    private String msg;
    private String code;
    private ArrayList<YanYangDate> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<YanYangDate> getList() {
        return list;
    }

    public void setList(ArrayList<YanYangDate> list) {
        this.list = list;
    }

    public class YanYangDate implements Serializable {

        private String ch4;
        private String co2;
        private String h2s;
        private String o2;
        private String status;
        private String heigh;
        private String time;
        private String pressure;//压力
        private String volume;//容积
        private String power;//耗电量

        public String getCh4() {
            return ch4;
        }

        public void setCh4(String ch4) {
            this.ch4 = ch4;
        }

        public String getCo2() {
            return co2;
        }

        public void setCo2(String co2) {
            this.co2 = co2;
        }

        public String getH2s() {
            return h2s;
        }

        public void setH2s(String h2s) {
            this.h2s = h2s;
        }

        public String getO2() {
            return o2;
        }

        public void setO2(String o2) {
            this.o2 = o2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHeigh() {
            return heigh;
        }

        public void setHeigh(String heigh) {
            this.heigh = heigh;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }
    }


}
