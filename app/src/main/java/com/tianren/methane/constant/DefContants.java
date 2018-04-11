package com.tianren.methane.constant;

public class DefContants {
	public static final int DEVICE_ID_SUM = 17;//设备id字符个数
	public static final int TIME_OUT_LOGIN = 15000;//登录超时
	public static final int STORAGE_COUNT_DEFAULT = 3;//默认冰箱温区个数
	public static final int WELCOME_ANIMATION_TIME = 1000;//欢迎界面动画时长
	public static final int FOOD_SUM_EACH_ITEM = 3;//界面一行显示的食物数
	
	public static final int ID_MY_DEVICE = 0;	   //我的家电设备界面id
	public static final int ID_ONLINE_SHOPPING = 1;//网上上传界面id
	public static final int ID_CUSTOM_SERVICE = 2; //售后服务界面id
	public static final int ID_MSG_CENTER = 3;	   //消息中心界面id
	public static final int ID_SETTING = 4;		   //设置界面id
	
	public static final int PASSWORD_MIN_NUM = 6;
	public static final int PASSWORD_MAX_NUM = 15;
	
	//添加食物：最喜爱、水果、蔬菜等id
	public static final int ID_FAVORITE = 0;	  
	public static final int ID_FRUIT = 1;
	public static final int ID_VEGETABLES = 2; 
	public static final int ID_MEAT = 3;	   
	public static final int ID_OTHER = 4;	
	
    /**食品存放位置 */
    public static final int LOCATION_FRESH=3;
    public static final int LOCATION_FREEZER=2;
	public static final int LOCATION_REFRIGERATOR=1;
	
	public static final int MODE_OFFLINE=20;//模式默认值，用于判断是否离线
}
