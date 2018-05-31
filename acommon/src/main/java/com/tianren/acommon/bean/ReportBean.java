package com.tianren.acommon.bean;

/**
 * @author Mr.Qiu
 * @date 2018/5/29
 */
public class ReportBean {
    private Integer reportDays;//生产天数
    private String reportTime;//生产时间

    private Double kitchenEnter;//(厨房垃圾)当日进厂量
    private Double kitchenDeal;//(厨房垃圾)当日处理量
    private Double kitchenPlan;//(厨房垃圾)月计划完成量
    private Double kitchenFinish;//(厨房垃圾)月完成量
    private Double kitchenRate;//(厨房垃圾)月完成率

    private Double repastEnter;//(餐饮垃圾)当日进厂量
    private Double repastDeal;//(餐饮垃圾)当日处理量
    private Double repastPlan;//(餐饮垃圾)月计划完成量
    private Double repastFinish;//(餐饮垃圾)月完成量
    private Double repastRate;//(餐饮垃圾)月完成率

    private Double oilPlan;//(提油)月计划完成量
    private Double oilFinish;//(提油)月完成量
    private Double oilRate;//(提油)月完成率

    private Double gasEnter1;//(产沼)日进料量(1号)
    private Double gasEnter2;//(产沼)日进料量(2号)
    private Double gasPlan;//(提油)月计划完成量
    private Double gasFinish;//(提油)月完成量
    private Double gasRate;//(提油)月完成率

    private Double eleProduct;//（发、用电）本月完成率
    private Double eleProvider;//（发、用电）日厂用电量
    private Double eleDayUseRate;//（发、用电）日站用电率
    private Double elePlan;//（发、用电）月计划完成量
    private Double eleFinish;//（发、用电）月完成量
    private Double eleDayRate;//（发、用电）本月完成率
    private Double eleUseFactoryData;//（发、用电）厂用电量
    private Double eleUseNetData;//（发、用电）日网用电量
    private Double elePlanUseData;//（发、用电）月计划网用电量
    private Double eleNetRate;//（发、用电）网用电率

    private Double waterFiltrateProduct;//（污水）日滤液产生量
    private Double waterRepertory;//（污水）库存
    private Double waterGasProduct;//（污水）日沼液产生量
    private Double waterBadIntroduceDay;//（污水）日污水处理量
    private Double waterBadIntroducePlan;//（污水）月计划污水处理量
    private Double waterBadIntroduceMonth;//（污水）月污水处理量


    public Integer getReportDays() {
        return reportDays;
    }

    public void setReportDays(Integer reportDays) {
        this.reportDays = reportDays;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Double getKitchenEnter() {
        return kitchenEnter;
    }

    public void setKitchenEnter(Double kitchenEnter) {
        this.kitchenEnter = kitchenEnter;
    }

    public Double getKitchenDeal() {
        return kitchenDeal;
    }

    public void setKitchenDeal(Double kitchenDeal) {
        this.kitchenDeal = kitchenDeal;
    }

    public Double getKitchenPlan() {
        return kitchenPlan;
    }

    public void setKitchenPlan(Double kitchenPlan) {
        this.kitchenPlan = kitchenPlan;
    }

    public Double getKitchenFinish() {
        return kitchenFinish;
    }

    public void setKitchenFinish(Double kitchenFinish) {
        this.kitchenFinish = kitchenFinish;
    }

    public Double getKitchenRate() {
        return kitchenRate;
    }

    public void setKitchenRate(Double kitchenRate) {
        this.kitchenRate = kitchenRate;
    }

    public Double getRepastEnter() {
        return repastEnter;
    }

    public void setRepastEnter(Double repastEnter) {
        this.repastEnter = repastEnter;
    }

    public Double getRepastDeal() {
        return repastDeal;
    }

    public void setRepastDeal(Double repastDeal) {
        this.repastDeal = repastDeal;
    }

    public Double getRepastPlan() {
        return repastPlan;
    }

    public void setRepastPlan(Double repastPlan) {
        this.repastPlan = repastPlan;
    }

    public Double getRepastFinish() {
        return repastFinish;
    }

    public void setRepastFinish(Double repastFinish) {
        this.repastFinish = repastFinish;
    }

    public Double getRepastRate() {
        return repastRate;
    }

    public void setRepastRate(Double repastRate) {
        this.repastRate = repastRate;
    }

    public Double getOilPlan() {
        return oilPlan;
    }

    public void setOilPlan(Double oilPlan) {
        this.oilPlan = oilPlan;
    }

    public Double getOilFinish() {
        return oilFinish;
    }

    public void setOilFinish(Double oilFinish) {
        this.oilFinish = oilFinish;
    }

    public Double getOilRate() {
        return oilRate;
    }

    public void setOilRate(Double oilRate) {
        this.oilRate = oilRate;
    }

    public Double getGasEnter1() {
        return gasEnter1;
    }

    public void setGasEnter1(Double gasEnter1) {
        this.gasEnter1 = gasEnter1;
    }

    public Double getGasEnter2() {
        return gasEnter2;
    }

    public void setGasEnter2(Double gasEnter2) {
        this.gasEnter2 = gasEnter2;
    }

    public Double getGasPlan() {
        return gasPlan;
    }

    public void setGasPlan(Double gasPlan) {
        this.gasPlan = gasPlan;
    }

    public Double getGasFinish() {
        return gasFinish;
    }

    public void setGasFinish(Double gasFinish) {
        this.gasFinish = gasFinish;
    }

    public Double getGasRate() {
        return gasRate;
    }

    public void setGasRate(Double gasRate) {
        this.gasRate = gasRate;
    }

    public Double getEleProduct() {
        return eleProduct;
    }

    public void setEleProduct(Double eleProduct) {
        this.eleProduct = eleProduct;
    }

    public Double getEleProvider() {
        return eleProvider;
    }

    public void setEleProvider(Double eleProvider) {
        this.eleProvider = eleProvider;
    }

    public Double getEleDayUseRate() {
        return eleDayUseRate;
    }

    public void setEleDayUseRate(Double eleDayUseRate) {
        this.eleDayUseRate = eleDayUseRate;
    }

    public Double getElePlan() {
        return elePlan;
    }

    public void setElePlan(Double elePlan) {
        this.elePlan = elePlan;
    }

    public Double getEleFinish() {
        return eleFinish;
    }

    public void setEleFinish(Double eleFinish) {
        this.eleFinish = eleFinish;
    }

    public Double getEleDayRate() {
        return eleDayRate;
    }

    public void setEleDayRate(Double eleDayRate) {
        this.eleDayRate = eleDayRate;
    }

    public Double getEleUseFactoryData() {
        return eleUseFactoryData;
    }

    public void setEleUseFactoryData(Double eleUseFactoryData) {
        this.eleUseFactoryData = eleUseFactoryData;
    }

    public Double getEleUseNetData() {
        return eleUseNetData;
    }

    public void setEleUseNetData(Double eleUseNetData) {
        this.eleUseNetData = eleUseNetData;
    }

    public Double getElePlanUseData() {
        return elePlanUseData;
    }

    public void setElePlanUseData(Double elePlanUseData) {
        this.elePlanUseData = elePlanUseData;
    }

    public Double getEleNetRate() {
        return eleNetRate;
    }

    public void setEleNetRate(Double eleNetRate) {
        this.eleNetRate = eleNetRate;
    }

    public Double getWaterFiltrateProduct() {
        return waterFiltrateProduct;
    }

    public void setWaterFiltrateProduct(Double waterFiltrateProduct) {
        this.waterFiltrateProduct = waterFiltrateProduct;
    }

    public Double getWaterRepertory() {
        return waterRepertory;
    }

    public void setWaterRepertory(Double waterRepertory) {
        this.waterRepertory = waterRepertory;
    }

    public Double getWaterGasProduct() {
        return waterGasProduct;
    }

    public void setWaterGasProduct(Double waterGasProduct) {
        this.waterGasProduct = waterGasProduct;
    }

    public Double getWaterBadIntroduceDay() {
        return waterBadIntroduceDay;
    }

    public void setWaterBadIntroduceDay(Double waterBadIntroduceDay) {
        this.waterBadIntroduceDay = waterBadIntroduceDay;
    }

    public Double getWaterBadIntroducePlan() {
        return waterBadIntroducePlan;
    }

    public void setWaterBadIntroducePlan(Double waterBadIntroducePlan) {
        this.waterBadIntroducePlan = waterBadIntroducePlan;
    }

    public Double getWaterBadIntroduceMonth() {
        return waterBadIntroduceMonth;
    }

    public void setWaterBadIntroduceMonth(Double waterBadIntroduceMonth) {
        this.waterBadIntroduceMonth = waterBadIntroduceMonth;
    }
}
