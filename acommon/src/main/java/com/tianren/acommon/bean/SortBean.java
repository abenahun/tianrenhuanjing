package com.tianren.acommon.bean;

/**
 * User: Lee
 * Date: 2018/5/17 0017
 * Time: 下午 1:53
 * Desc: 神兽保佑代码无bug
 */
public class SortBean {

    public SortBean() {

    }

    public SortBean(String sort, String startTime, String endTime, String searchFields) {
        this.sort = sort;
        this.startTime = startTime;
        this.endTime = endTime;
        this.searchFields = searchFields;
    }

    public SortBean(String sort, String startTime, String endTime, int startItem, int endItem, String searchFields) {
        this.sort = sort;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startItem = startItem;
        this.endItem = endItem;
        this.searchFields = searchFields;
    }

    private String sort;

    private String startTime;

    private String endTime;

    private int startItem;

    private int endItem;

    private String searchFields;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStartItem() {
        return startItem;
    }

    public void setStartItem(int startItem) {
        this.startItem = startItem;
    }

    public int getEndItem() {
        return endItem;
    }

    public void setEndItem(int endItem) {
        this.endItem = endItem;
    }

    public String getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(String searchFields) {
        this.searchFields = searchFields;
    }
}
