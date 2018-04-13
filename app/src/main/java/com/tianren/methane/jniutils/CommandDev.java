package com.tianren.methane.jniutils;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tianren.methane.activity.MainActivity;
import com.tianren.methane.service.SipService;
import com.tianren.methane.utils.DataCaculateUtils;

/**
 * @Athor: CaoXiuxia
 * @ClassName: CommandDev
 * @Description:1.主要负责完成向空调设备发送、查询相应的控制指令数据组合;
 * 				      根据***V1.2***手机和智能空调通信模块（MCU）通信规范
 *              2.追加手机与冰箱设备之间的交互（状态的设置）
 *              3.追加手机与洗衣机之间的交互（状态的设置）
 * @date 2014-8-27 上午10:55:48
 *
 */

 /*
  *   0x01--->1
	  0x02--->2
	  0x03--->3
	  0x04--->4
	  0x05--->5
	  0x06--->6
	  0x07--->7
	  0x08--->8
	  0x09--->9
	  0x0A--->10
	  0X0B--->11
	  0X0C--->12
	  0X0D--->13
	  0X0E--->14
      0X0F--->15

      0x10--->16
      0x20--->32
      0x30--->48
      0x40--->64
      0x50--->80
      0x60--->96
      0x70--->112
      0x80--->128
      0x90--->144
      0xA0--->160
      0xB0--->176
      0xC0--->192
      0xD0--->208
      0xE0--->224
      0xF0--->240
      0xF1--->241
      0xF0--->240
  */


public class CommandDev {

	private String TAG = "CommandDev";
	private Map<String, Object> devCmdMap;
	public static CommandDev commandDev = null;

	private CommandDev() {
		// TODO Auto-generated constructor stub
		devCmdMap = new HashMap<String, Object>();
	}
	public static CommandDev getCmdDevInstance() {
		if (commandDev == null) {
			commandDev = new CommandDev();
		}
		return commandDev;
	}

	/**
	* @Title: sendCmdDev
	* @Description: 与空调的通信,命令数据设置完之后，统一发送至空调;
	* 				 向空调发送控制指令(最终调用此函数);
	* 				 目前先用广域网一期;
	* @return void
	*/
	public void sendCmdToDev(String mDeviceId,String domainStr){//2014.11.07晚
		Log.e(TAG, "send cmd to device !");
		if(mDeviceId !=null){
			Log.d(TAG,"deviceid-->"+mDeviceId);
			Log.e(TAG, "userneme:  "+MainActivity.userName);
			if ((MainActivity.userName != null) && (!MainActivity.userName.equals("")) && getDevCmdJsonStr()!=null) {
				Log.d(TAG, "--userName-->"+MainActivity.userName);
				sendDevCmd(mDeviceId,MainActivity.userName,getDevCmdJsonStr(),false,domainStr);//先使用广域网的接口测
			}else {
				Log.e(TAG,"username is null !");
			}
			devCmdMap.clear();
		}else {
			Log.e(TAG,"deviceid is null !");
		}

		/*else {//这段程序留着，网络的选择：判断局域网 广域网
			setDeviceid(CurrentDev.getInstance().getDevId());
			Log.d("Command",getDevCmdJsonStr());
			sendDevCmd(mDeviceId,SlidFragActivity.userName,getDevCmdJsonStr(),false);
			//sendDevCmd(CurrentDev.getInstance().getDevId(), getDevCmdJsonStr(),CurrentDev.getInstance().getIsWan());
			devCmdMap.clear();
		}*/
	}

	/**
	 * @author CaoXiuxia
	 * @return
	 */
	private String getDevIdEncStr(String devId){
		String devIdStr = null;
		try {
			devIdStr = AESTool.getInstance().content(devId,AESTool.AES_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG,"生成 devIdStr ："+devIdStr);
		return devIdStr;
	}
	/**
	 * @author CaoXiuxia
	 * @return
	 */
	private String getUserNameEncStr(String userName){
		String encUserNameStr = null;
		try {
			encUserNameStr = AESTool.getInstance().content(userName,AESTool.AES_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG,"生成 userName ："+encUserNameStr);
		return encUserNameStr;
	}
	/**
	* @Title: InitsendSettingCmd
	* @Description: 发送空调状态控制指令
	* @return void
	* @throws
	*/
	public void InitSendSettingCmd(){
		setCmdtype(204);
		setUsrid(MainActivity.userName);
	}
	/**
	* @Title: sendQueryCmdInit
	* @Description: 发送查询空调状态指令,第一步
	* @return void
	*/
	public void InitSendQueryCmd(){
		setCmdtype(201);
		setUsrid(MainActivity.userName);
	}


	/*
	 * 命令类型：
	 * 201--查询设备运行状态参数;
	 * 202--查询设备实时时间和日期;
	 * 203--查询设备使用过的电量;
	 * 204--设置设备运行状态参数;
	 * 205--设置设备实时时间和日期;
	 * 206--绑定用户查询;
	 * 207--重置;
	 * 208--升级;
	 */
	//手机向空调发送
	private int cmdtype = 0;

	 //手机登陆SIP服务器的 user ID
	private String usrid = null;

	 //空调设备号
	private String deviceid = null;

	//用户密码(*****是否需要******不需要)
	private String usrpasswd = null;


	/*
	 * 室内风量设置,String 类型：
	 *
	 * 						本次需设置：		本次无设置：
	 * 0000000-自动风  ,( 00000001--> 0x01 );( 00000000--> 0x00 )
	 * 0000001-静音风速,( 00000011--> 0x03 );( 00000010--> 0x02 )
	 * 0000010-低风风速,( 00000101--> 0x05 );( 00000100--> 0x04 )
	 * 0000011-中风风速,( 00000111--> 0x07 );( 00000110--> 0x06 )
	 * 0000100-高风风速,( 00001001--> 0x09 );( 00001000--> 0x08 )
	 * 默认值是自动风
	 *
	 *
	 * 查询的返回值
	 * */
		/*0000000 0：自动风速控制；0
		0000001 0：静音风速；2
		0000010 0：低风风速；4
		0000011 0：中风风速；6
		0 0 00100 0：高风风速；8
		无级风速：转换函数
		1档风：
		2档风：

		*/
	private String windspeed ;
	/*
	 * 睡眠模式设置
	 *
	 * 							本次需设置：		本次无设置：
	 * 0000000-关睡眠模式：	( 00000001--> 0x01 );( 00000000--> 0x00 )
	 * 0000001-1#睡眠模式：	( 00000011--> 0x03 );( 00000010--> 0x02 )
	 * 0000010-2#睡眠模式：	( 00000101--> 0x05 );( 00000100--> 0x04 )
	 * 0000011-3#睡眠模式：	( 00000111--> 0x07 );( 00000110--> 0x06 )
	 * 0000100-4#睡眠模式：	( 00001001--> 0x09 );( 00001000--> 0x08 )
	 * 0000101-5#睡眠模式：	( 00001011--> 0x0B );( 00001010--> 0x0A )
	 * 0000110-6#睡眠模式：	( 00001101--> 0x0D );( 00001100--> 0x0C )
	 * 0000111-7#睡眠模式：	( 00001111--> 0x0F );( 00001110--> 0x0D )
	 * 0001000-8#睡眠模式：	( 00010001--> 0xF1 );( 00010000--> 0xF0 )
	 */
	private String sleep;
	private String sleep_last;

	/*
	 * 工作模式与开停机控制、风向开停控制（具体的可能还需要改）
	 * */
	/*
	 * 设定工作模式
	 *
	 * 			本次需设置			本次无需设置
	 * 送风:(00010000--> 0x10 )  16 ;(00000000--> 0x00 )
	 * 制热:(00110000--> 0x30 )  48 ;(00100000--> 0x20 )
	 * 制冷:(01010000--> 0x50 )  80 ;(01000000--> 0x40 )
	 * 除湿:(01110000--> 0x70 )  112 ;(01100000--> 0x60 )
	 * 自动:(10010000--> 0x90 )  144 ;(10000000--> 0x80 )
	 */
	/*查询
	  0000 0000：送风--> 00
	  0001 0000：制热--> 10
	  0010 0000：制冷--> 20
	  0011 0000：除湿--> 30
	  0100 0000：自动下的送风-->40
	  0101 0000：自动下的制热；
	  0110 0000：自动下的制冷；
	  0111 0000：自动下的除湿；
	*/
	private String mode = "0x90";
	/*
	 * 开停机控制
	 * 			本次需设置			本次无需设置
	 * 开机：(00001100--> 0x0C );(00001000--> 0x08 )
	 * 停机：(00000100--> 0x04 );(00000000--> 0x00 )
	 */
	private String poweronoff = "0x00";


	/*
	 * 设置风向开停（单向风的空调使用）
	 * 				本次需设置		本次无需设置
	 * 风门开：(00000011--> 0x03 );(00000010--> 0x20 )
	 * 风门关：(00000001--> 0x01 );(00000000--> 0x00 )
	 * */
	private String winddironoff = "0x00";
	/*
	 * 室内温度设置（？？？？？？？？？？补码？？？？？？）
	 * 	 	本次需设置		本次无需设置
	 * (00000001--> 0x01 );(00000000--> 0x00 )
	 * */
	//929
	private String indegree = "0x1a";//25度默认值

	/*
	 * 室内湿度设置（？？？？？？？？？？原码？？？？？？）
	 * 	 	 本次需设置		本次无需设置
	 * (00000001--> 0x01 );(00000000--> 0x00 )
	 * */
	private String inhumidity ="0x01";

	/////////////////start 相应的数据值，再定义一下根据文档/////////////
	//体感室内温度
	private String indegreefeel ="0x00";
	//体感室内温度补偿
	private String intempcompensate ="0x00";
	private String motioncontrol ="0x00";

	//自动工作模式和除湿模式的温度补偿，华氏/摄氏温度显示设置
	private String modetempcompensate ="0x00";
	private String tempdisplay ="0x00";


	//1.4版本,实时时钟的开机参数,17
	/*命令值：
	 *
	 *
	 *
	 *
	 *
	 * */
	private String ontimecontrol;//开机控制 0x04
	private String ontimerhour;//开机小时值
	private String ontimermins;//开机分钟值
	//1.4版本,实时时钟的关机参数,19
	private String offtimecontrol;//关机控制0x06


	private String offtimerhour;//关机小时值
	private String offtimermins;//关机分钟值

	private String ampm_last_bootStr;
	private String ampm_last_shutStr;



	private String ontimerhour_last;//开机小时值备份
	private String ontimermins_last;//开机分钟值备份
	//1.4版本,实时时钟的关机参数,19
	private String offtimerhour_last;//关机小时值备份
	private String offtimermins_last;//关机分钟值备份


	//////////////////////////////end//////////////////////////////////////

	//PM2.5质量等级状态显示,相关的等级需要后续添加

	//public static String pmrank = "";
	//PM2.5质量百分比,相关的等级需要后续添加
	//public static String pmpercent = "";

	//左右风门
	private String leftrightofairdoor ="0x80" ;
	/*
	 * 21
	 * 除湿模式和风门位置设置
	 * */
	private String dehumidity = "0x10";
	/*
	 * 设置除湿模式
	 * BIT3-BIT0为0, BIT7-BIT4为数据位，代表实时开机控制。
	 * 					本次需设置			本次无需设置
	 * 自动除湿：	(00010000--> 0x10 );(00000000--> 0x00 ,默认值)
	 * 1#除湿模式：	(00110000--> 0x30 );(00100000--> 0x20 )
	 * 2#除湿模式：	(01010000--> 0x50 );(01000000--> 0x40 )
	 * 3#除湿模式：	(01110000--> 0x70 );(01100000--> 0x60 )
	 * 4#除湿模式：	(10010000--> 0x90 );(10000000--> 0x80 )
	 * 5#除湿模式：	(10110000--> 0xB0 );(10100000--> 0xA0 )
	 * 6#除湿模式：	(11010000--> 0xD0 );(11000000--> 0xC0 )
	 * 7#除湿模式：	(11110000--> 0xF0 );(11100000--> 0xE0 )
	 * */
	/*
	 * 上下风门位置,速度
	 * 					本次需设置			本次无需设置
	 * 扫掠:		(0000 000 1->0x01)	（0000 000 0->0x00)
	 * 自动:		(0000 001 1->0x03）	（0000 001 0->0x02）
	 * 角度1#:		(0000 010 1->0x05）	（0000 010 0->0x04）
	 * 角度2#:		(0000 011 1->0x07）	（0000 011 0->0x06）
	 * 角度3#:		(0000 100 1->0x09）	（0000 100 0->0x08）
	 * 角度4#:		(0000 101 1->0x0B）	（0000 101 0->0x0A）
	 * 角度5#:		(0000 110 1->0x0D）	（0000 110 0->0x0C）
	 * 角度6#:		(0000 111 1->0x0F）	（0000 111 0->0x0E）
	 * */
	private String winddoorud ="0x01" ;
	/*
	 * 22
	 * 状态设置1：上下/左右/自然风门开停控制
	 *  					本次需设置			本次无需设置
	 * 上下风门开停：
	 * 开:				(11 00 0000->0xC0)	（10 00 0000->0x80)
	 * 停:				(01 00 0000->0x40)	（00 00 0000->0x00)
	 * 左右风门开停：
	 * 开：				(00 11 0000->0x30)	（00 10 0000->0x20)
	 * 停：				(00 01 0000->0x10)	（00 00 0000->0x00)
	 * 自然风开停控制：
	 * 开:				(0000 11 00->0x0C)	（0000 10 00->0x08)
	 * 停:				(0000 01 00->0x04)	（0000 00 00->0x00)
	 *
	 * 电热开停控制：
	 * 开：				(0000 00 11->0x03)	（0000 00 10->0x02)
	 * 停：				(0000 00 01->0x01)	（0000 00 00->0x00)
	 * */
	private String updown = "0xC0";
	private String leftright = "0x30";
	private String natural = "0x0C";
	private String electricheat = "0x03";

	/*
	 * 23
	 * 状态设置2
	 * 状态设置1：节能/并用/高效节电开停,双模切换
	 *  					本次需设置			本次无需设置
	 * 节能开停控制：
	 * 开:				(11 00 0000->0xC0)	（10 00 0000->0x80)
	 * 停:				(01 00 0000->0x40)	（00 00 0000->0x00)
	 * 并用节电开停控制：
	 * 开：				(00 11 0000->0x30)	（00 10 0000->0x20)
	 * 停：				(00 01 0000->0x10)	（00 00 0000->0x00)
	 * 高效（强力）开停控制：
	 * 开:				(0000 11 00->0x0C)	（0000 10 00->0x08)
	 * 停:				(0000 01 00->0x04)	（0000 00 00->0x00)
	 *
	 * 双模切换控制：
	 * 定频：			(0000 00 11->0x03)	（0000 00 10->0x02)
	 * 变频：			(0000 00 01->0x01)	（0000 00 00->0x00)
	 * */
	private String energysaving = "0x00";
	private String powersaving = "0x00";
	private String efficient = "0x00";
	private String dualband = "0x00";
	/*
	 * 24
	 * 状态设置3：清新/换风、室内清洁开停控制
	 *  					本次需设置			本次无需设置
	 * 清新开停控制：
	 * 开:				(11 00 0000->0xC0)	（10 00 0000->0x80)
	 * 停:				(01 00 0000->0x40)	（00 00 0000->0x00)
	 * 换风开停控制：
	 * 开：				(00 11 0000->0x30)	（00 10 0000->0x20)
	 * 停：				(00 01 0000->0x10)	（00 00 0000->0x00)
	 * 室内清洁开停控制：
	 * 开:				(0000 11 00->0x0C)	（0000 10 00->0x08)
	 * 停:				(0000 01 00->0x04)	（0000 00 00->0x00)
	 *
	 * 室外清洁开停控制：
	 * 开：				(0000 00 11->0x03)	（0000 00 10->0x02)
	 * 停：				(0000 00 01->0x01)	（0000 00 00->0x00)
	 * */




	private String freshness = "0xC0";
	private String airfresh = "0x30";
	private String inclean = "0x0C";
	private String outclean = "0x03";

	private String freshness_last;

	/*
	 * 25
	 * 状态设置4：智慧眼/静音模式/语音控制/除烟开停控制
	 *  					本次需设置			本次无需设置
	 * 智慧眼开停控制：
	 * 开:				(11 00 0000->0xC0)	（10 00 0000->0x80)
	 * 停:				(01 00 0000->0x40)	（00 00 0000->0x00)
	 * 静音模式开停控制：
	 * 开：				(00 11 0000->0x30)	（00 10 0000->0x20)
	 * 停：				(00 01 0000->0x10)	（00 00 0000->0x00)
	 * 语音控制开停控制：
	 * 开:				(0000 11 00->0x0C)	（0000 10 00->0x08)
	 * 停:				(0000 01 00->0x04)	（0000 00 00->0x00)
	 *
	 * 除烟开停控制：
	 * 开：				(0000 00 11->0x03)	（0000 00 10->0x02)
	 * 停：				(0000 00 01->0x01)	（0000 00 00->0x00)
	 * */



	private String smarteye = "0xC0";
	private String silence = "0x00";
	private String voice = "0x00";
	private String smokeabatement = "0x00";

	private String silence_last;

	/*
	 * 26
	 * 状态设置5：背景灯设置
	 *  					本次需设置			本次无需设置
	 * 背景灯开停控制：
	 * 开:				(11 00 0000->0xC0)	（10 00 0000->0x80)
	 * 停:				(01 00 0000->0x40)	（00 00 0000->0x00)
	 * 显示屏发光显示开停控制：
	 * 开：				(00 11 0000->0x30)	（00 10 0000->0x20)
	 * 停：				(00 01 0000->0x10)	（00 00 0000->0x00)
	 * LED指示灯开停控制：
	 * 开:				(0000 11 00->0x0C)	（0000 10 00->0x08)
	 * 停:				(0000 01 00->0x04)	（0000 00 00->0x00)
	 *
	 * 室内外温度切换显示开停控制：
	 * 显示室外温度：				(0000 00 11->0x03)	（0000 00 10->0x02)
	 * 显示室内温度：				(0000 00 01->0x01)	（0000 00 00->0x00)
	 * */



	private String backlight = "0xC0";
	private String screenlight = "0x00";
	private String led = "0x00";
	private String tempswitchdisplay = "0x00";

	/*
	 * 27
	 * 状态设置6：清新/换风、室内清洁开停控制
	 *  					本次需设置			本次无需设置
	 * 室内过滤网清洁复位：
	 * 复位:			(11 00 0000->0xC0)	（10 00 0000->0x80)
	 * 正常工作:		(01 00 0000->0x40)	（00 00 0000->0x00)
	 * 左风摆开停控制：
	 * 开：				(00 11 0000->0x30)	（00 10 0000->0x20)
	 * 停：				(00 01 0000->0x10)	（00 00 0000->0x00)
	 * 右风摆开停控制：
	 * 开:				(0000 11 00->0x0C)	（0000 10 00->0x08)
	 * 停:				(0000 01 00->0x04)	（0000 00 00->0x00)
	 *
	 * 预留：
	 * 开(1)：			(0000 00 11->0x03)	（0000 00 10->0x02)
	 * 停(0)：			(0000 00 01->0x01)	（0000 00 00->0x00)
	 * */

	private String netrestorate = "0xC0";
	private String leftswing = "0x00";
	private String rightswing = "0x00";

	//屏幕亮度
	private String screenlumi;
	public String getScreenlumi() {
		return screenlumi;
	}

	public void setScreenlumi(String screenlumi) {
		this.screenlumi = screenlumi;
		devCmdMap.put("screenlumi", screenlumi);
	}

	public String getDevCmdJsonStr()
	{
		Log.e(TAG, "creat cmdjson："+ JSON.toJSONString(devCmdMap));
		return JSON.toJSONString(devCmdMap);
	}
	public Map<String, Object> getDevCmdMap() {
		return devCmdMap;
	}
	public void setDevCmdMap(Map<String, Object> devCmdMap) {
		this.devCmdMap = devCmdMap;
	}

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	///////暂时先改这些2014.09.06//////////////////////////////

	public int getCmdtype() {
		return cmdtype;
	}
	public void setCmdtype(int cmdtype) {
		this.cmdtype = cmdtype;
		devCmdMap.put("cmdtype", cmdtype);
	}
	public String getUsrid() {
		return usrid;
	}
	public void setUsrid(String usrid) {
		this.usrid = usrid;
		devCmdMap.put("usrid", usrid);
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
		devCmdMap.put("deviceid", deviceid);
	}
	public String getUsrpasswd() {
		return usrpasswd;
	}
	public void setUsrpasswd(String usrpasswd) {
		this.usrpasswd = usrpasswd;
		devCmdMap.put("usrpasswd", usrpasswd);
	}

	public void setWindspeed(String windspeed) {
		this.windspeed = windspeed;
		devCmdMap.put("windspeed", windspeed);
	}

	public void setSleep(String sleep) {
		this.sleep = sleep;
		devCmdMap.put("sleep", sleep);
	}

	public void setMode(String mode) {
		this.mode = mode;
		devCmdMap.put("mode", mode);
	}
	public void setPoweronoff(String poweronoff) {
		this.poweronoff = poweronoff;
		devCmdMap.put("poweronoff", poweronoff);
	}

	public void setWinddironoff(String winddironoff) {
		this.winddironoff = winddironoff;
		devCmdMap.put("winddironoff", winddironoff);
	}

	public void setIndegree(String indegree) {
		this.indegree = indegree;
		devCmdMap.put("indegree", indegree);
	}

	public void setInhumidity(String inhumidity) {
		this.inhumidity = inhumidity;
		devCmdMap.put("inhumidity", inhumidity);
	}

	public  void setLeftrightofairdoor(String leftrightofairdoor) {
		this.leftrightofairdoor = leftrightofairdoor;
		devCmdMap.put("leftrightofairdoor", leftrightofairdoor);
	}

	public  void setDehumidity(String dehumidity) {
		this.dehumidity = dehumidity;
		devCmdMap.put("dehumidity", dehumidity);
	}

	public  void setWinddoorud(String winddoorud) {
		this.winddoorud = winddoorud;
		devCmdMap.put("winddoorud", winddoorud);
	}

	public  void setUpdown(String updown) {
		this.updown = updown;
		devCmdMap.put("updown", updown);
	}

	public  void setLeftright(String leftright) {
		this.leftright = leftright;
		devCmdMap.put("leftright", leftright);
	}

	public  void setNatural(String natural) {
		this.natural = natural;
		devCmdMap.put("natural", natural);
	}

	public void setElectricheat(String electricheat) {
		this.electricheat = electricheat;
		devCmdMap.put("electricheat", electricheat);
	}

	public void setEnergysaving(String energysaving) {
		this.energysaving = energysaving;
		devCmdMap.put("energysaving", energysaving);
	}

	public void setPowersaving(String powersaving) {
		this.powersaving = powersaving;
		devCmdMap.put("powersaving", powersaving);
	}

	public void setEfficient(String efficient) {
		this.efficient = efficient;
		devCmdMap.put("efficient", efficient);
	}

	public void setDualband(String dualband) {
		this.dualband = dualband;
		devCmdMap.put("dualband", dualband);
	}

	public void setFreshness(String freshness) {
		this.freshness = freshness;
		devCmdMap.put("freshness", freshness);
	}

	public void setAirfresh(String airfresh) {
		this.airfresh = airfresh;
		devCmdMap.put("airfresh", airfresh);
	}

	public void setInclean(String inclean) {
		this.inclean = inclean;
		devCmdMap.put("inclean", inclean);

	}

	public void setOutclean(String outclean) {
		this.outclean = outclean;
		devCmdMap.put("outclean", outclean);
	}

	public void setSmarteye(String smarteye) {
		this.smarteye = smarteye;
		devCmdMap.put("smarteye", smarteye);
	}

	public void setSilence(String silence) {
		this.silence = silence;
		devCmdMap.put("silence", silence);
	}

	public void setSmokeabatement(String smokeabatement) {
		this.smokeabatement = smokeabatement;
		devCmdMap.put("smokeabatement", smokeabatement);
	}

	public void setBacklight(String backlight) {
		this.backlight = backlight;
		devCmdMap.put("backlight", backlight);
	}

	public void setScreenlight(String screenlight) {
		this.screenlight = screenlight;
		devCmdMap.put("screenlight", screenlight);
	}

	public void setLed(String led) {
		this.led = led;
		devCmdMap.put("led", led);
	}

	public void setTempswitchdisplay(String tempswitchdisplay) {
		this.tempswitchdisplay = tempswitchdisplay;
		devCmdMap.put("tempswitchdisplay", tempswitchdisplay);
	}

	public void setNetrestorate(String netrestorate) {
		this.netrestorate = netrestorate;
		devCmdMap.put("netrestorate", netrestorate);
	}

	public void setLeftswing(String leftswing) {
		this.leftswing = leftswing;
		devCmdMap.put("leftswing", leftswing);
	}

	public void setRightswing(String rightswing) {
		this.rightswing = rightswing;
		devCmdMap.put("rightswing", rightswing);
	}

	////////////////2014.09.16 //////////////////////

	public void setIndegreefeel(String indegreefeel) {
		this.indegreefeel = indegreefeel;
		devCmdMap.put("indegreefeel", indegreefeel);
	}

	public void setIntempcompensate(String intempcompensate) {
		this.intempcompensate = intempcompensate;
		devCmdMap.put("intempcompensate", intempcompensate);
	}

	public void setMotioncontrol(String motioncontrol) {
		this.motioncontrol = motioncontrol;
		devCmdMap.put("motioncontrol", motioncontrol);
	}

	public void setModetempcompensate(String modetempcompensate) {
		this.modetempcompensate = modetempcompensate;
		devCmdMap.put("modetempcompensate", modetempcompensate);
	}

	public void setTempdisplay(String tempdisplay) {
		this.tempdisplay = tempdisplay;
		devCmdMap.put("tempdisplay", tempdisplay);
	}

	public void setOntimerhour(String ontimerhour) {
		this.ontimerhour = ontimerhour;
		devCmdMap.put("ontimerhour", ontimerhour);
	}

	public void setOntimecontrol(String ontimecontrol) {
		this.ontimecontrol = ontimecontrol;
		devCmdMap.put("ontimecontrol", ontimecontrol);
	}

	public void setOntimermins(String ontimermins) {
		this.ontimermins = ontimermins;
		devCmdMap.put("ontimermins", ontimermins);
	}

	public void setOfftimerhour(String offtimerhour) {
		this.offtimerhour = offtimerhour;
		devCmdMap.put("offtimerhour", offtimerhour);
	}

	public void setOfftimermins(String offtimermins) {
		this.offtimermins = offtimermins;
		devCmdMap.put("offtimermins", offtimermins);
	}
	public String getAmpm_last_bootStr() {
		return ampm_last_bootStr;
	}

	public void setAmpm_last_bootStr(String ampm_last_bootStr) {
		this.ampm_last_bootStr = ampm_last_bootStr;
	}

	public String getAmpm_last_shutStr() {
		return ampm_last_shutStr;
	}

	public void setAmpm_last_shutStr(String ampm_last_shutStr) {
		this.ampm_last_shutStr = ampm_last_shutStr;
	}
	public void setVoice(String voice) {
		this.voice = voice;
		devCmdMap.put("voice", voice);
	}
	public String getOfftimecontrol() {
		return offtimecontrol;
	}
	public void setOfftimecontrol(String offtimecontrol) {
		this.offtimecontrol = offtimecontrol;
	}
	//冰箱：模式以及温度参数cxx，2014.12.01，变量名最后根据协议文档的参数名称保持一致吧！
 	private int mQuickFreezeState;//速冻(冷藏)
 	private int mRapidCoolState;//速冷（冷冻）
 	private int mIntelligenceState;//智能
 	private int mSaveEnergyState;//节能
 	private int mHolidyState;//假日
	private int mRefrigerateTemp;//冷藏区温度
	private int mFreezingTemp;//冷冻区温度
	private int mRetainfreshnessTemp;//保鲜区温度
	private String devid ;
	private String devtype ;
	private final String DEV_TYPE_FR = "FR";


	/**
	* @Title: InitSendSettingFrigerCmd
	* @Description: 发送冰箱控制命令，第一步
	* @return void
	*/
	public void InitSendSettingFrigerCmd(String deviceId){
		setCmdtype(304);
		setUsrid(MainActivity.userName);
		if(MainActivity.mDeviceId != null){
			setDevid(MainActivity.mDeviceId);
		}


	}

	/**
	* @Title: InitSendQueryFrigerCmd
	* @Description: 发送查询空调状态指令，第一步
	* @return void
	*/
	public void InitSendQueryFrigerCmd(String mDeviceId){
		setCmdtype(301);
		setUsrid(MainActivity.userName);
		if(mDeviceId != null){
			setDevid(mDeviceId);
		}
	}

	public void setDevid(String devid) {
		this.devid = devid;
		devCmdMap.put("devid", devid);
	}

	public void setDevtype(String devtype) {
		this.devtype = devtype;
		devCmdMap.put("devtype", devtype);
	}

	public void setmQuickFreezeState(int mQuickFreezeState) {
		this.mQuickFreezeState = mQuickFreezeState;
		devCmdMap.put("superfreeze", mQuickFreezeState);
		Log.e(TAG, "superfreeze cmd："+mQuickFreezeState);
	}

	public void setmRapidCoolState(int mRapidCoolState) {
		this.mRapidCoolState = mRapidCoolState;
		devCmdMap.put("supercool", mRapidCoolState);
		Log.e(TAG, "supercool cmd："+mRapidCoolState);
	}

	public void setmIntelligenceState(int mIntelligenceState) {
		this.mIntelligenceState = mIntelligenceState;
		devCmdMap.put("smart", mIntelligenceState);
		Log.e(TAG, "smart cmd："+mIntelligenceState);
	}

	public void setmSaveEnergyState(int mSaveEnergyState) {
		this.mSaveEnergyState = mSaveEnergyState;
		devCmdMap.put("eco", mSaveEnergyState);
		Log.e(TAG, "eco cmd："+mSaveEnergyState);
	}

	public void setmHolidyState(int mHolidyState) {
		this.mHolidyState = mHolidyState;
		devCmdMap.put("holiday", mHolidyState);
		Log.e(TAG, "holiday cmd："+mHolidyState);
	}

	public void setmRefrigerateTemp(int mRefrigerateTemp) {
		this.mRefrigerateTemp = mRefrigerateTemp;
		devCmdMap.put("fridge", mRefrigerateTemp);
		Log.e(TAG, "fridge cmd："+mRefrigerateTemp);
	}

	public void setmFreezingTemp(int mFreezingTemp) {
		this.mFreezingTemp = mFreezingTemp;
		devCmdMap.put("freezer", mFreezingTemp);
		Log.e(TAG, "freezer cmd："+mFreezingTemp);
	}

	public void setmRetainfreshnessTemp(int mRetainfreshnessTemp) {
		this.mRetainfreshnessTemp = mRetainfreshnessTemp;
		devCmdMap.put("variety", mRetainfreshnessTemp);
		Log.e(TAG, "variety cmd："+mRetainfreshnessTemp);
	}

	/*****************洗衣机相关状态，start 2015.05.05*******************/
	private int mPowerWasher = 0;	//(0/1)电源开关
	private int mStartWasher = 0;	//(0/1)启动/暂停
	private int mPresetWasher = 0;	//(0/1)预约
	private int mPrgmWasher = 1;	//(1~16)
	private int mSpeed = 0;			//(0/400/500/600/700/800/1000/1200/1400)
	private int mDegree = 0;		//(0/20/30/40/50/60/90/95)
	private int mKidlock = 0;		//(0/1)童锁
	private int mImmersion = 0;		//(0/1)防皱浸泡
	private int mAiring = 0;		//(0/1)凉护
	private int mHilevel = 0;		//(0/1)高水位
	private int mPrewash = 0;		//(0/1)预洗
	private int mStrong = 0;		//(0/1)强洗
	private int mDisinfect = 0;		//(0/1)Ag+杀菌
	private int mTubelight = 0;		//(0/1)筒灯
	private int mEconomic = 0;		//(0/1)经济洗
	private int mMute = 0;			//(0/1)静音
	private int mStain = 0;			//(0/1)特殊污渍开关
	private int mSoftening = 0;		//(0/1)软化投放
	private int mPresethrs = 0;		//(0<x<24)预约小时（预约功能开启之后，预约时间才有效）
	private int mPresetmins = 0;	//预约分钟
	private int mWashtime = 0;		//洗涤时间（倒计时）
	private int mRinsings = 0;		//漂洗次数(主控板上面的)
	private int mSoftener = 0;		//柔顺剂投放，mSoftening = 1时起作用。0,自动；1:少;2，中；3，多；
	private int mAbluentswitch = 0;	//(0/1)洗涤投放；
	private int mAbluent = 0;		//洗涤剂投放，abluent = 1时起作用。自动：0；预留：(少：1；中：2；多：3)；
	private int mStainparams = 0;	//返回值是多少？特渍参数（泥渍、酒渍、咖啡渍、果汁渍、血渍、草渍）在洗衣机功能功能22，特殊污渍功能开启以后，才可设定特渍参数。
	private int mYearHigh = 0;		//年,高位
	private int mYearLow = 0;		//年,低位

	private int mMonth = 0;			//月
	private int mDay = 0;			//日
	private int mHour = 0;			//小时
	private int mMinute= 0;			//分钟
	private int mWeekday= 0;		//周几

	private final String DEV_TYPE_WASHER = "WS";
	private DataCaculateUtils caculateUtils = new DataCaculateUtils();

	/**
	* @Title: InitSendSettingWasherCmd
	* @Description: 发送洗衣机控制命令，第一步
	* @return void
	*/
	public void InitSendSettingWasherCmd(String deviceId,Context mContext){
		setCmdtype(404);
		setUsrid(MainActivity.userName);
		if(MainActivity.mDeviceId != null){
			setDevid(MainActivity.mDeviceId);
		}
		/*每发一次设置，年月日时分星期，都必须下发给设备*/
		setmYearHigh(caculateUtils.getYearHigh());
		setmYearLow(caculateUtils.getYearLow());
		setmMonth(caculateUtils.getLocalMouth());
		setmDay(caculateUtils.getLocalDay());
		setmHour(caculateUtils.getLocalHour(mContext));
		setmMinute(caculateUtils.getLocalMinite());
		setmWeekday(caculateUtils.getLocalWeekday());
	}

	/**
	* @Title: InitSendQueryWasherCmd
	* @Description: 发送查询洗衣机状态指令，第一步
	* @return void
	*/
	public void InitSendQueryWasherCmd(String mDeviceId){
		setCmdtype(401);
		setUsrid(MainActivity.userName);
		if(mDeviceId != null){
			setDevid(mDeviceId);
		}

	}

	public void setmPowerWasher(int mPowerWasher) {
		this.mPowerWasher = mPowerWasher;
		devCmdMap.put("power", mPowerWasher);
	}
	public void setmStartWasher(int mStartWasher) {
		this.mStartWasher = mStartWasher;
		devCmdMap.put("start", mStartWasher);
	}
	public void setmPresetWasher(int mPresetWasher) {
		this.mPresetWasher = mPresetWasher;
		devCmdMap.put("preset", mPresetWasher);
	}
	public void setmPrgmWasher(int mPrgmWasher) {
		this.mPrgmWasher = mPrgmWasher;
		devCmdMap.put("prgm", mPrgmWasher);
	}
	public void setmSpeed(int mSpeed) {
		this.mSpeed = mSpeed;
		devCmdMap.put("speed", mSpeed);
	}
	public void setmDegree(int mDegree) {
		this.mDegree = mDegree;
		devCmdMap.put("degree", mDegree);
	}
	public void setmKidlock(int mKidlock) {
		this.mKidlock = mKidlock;
		devCmdMap.put("kidlock", mKidlock);
	}
	public void setmImmersion(int mImmersion) {
		this.mImmersion = mImmersion;
		devCmdMap.put("immersion", mImmersion);
	}
	public void setmAiring(int mAiring) {
		this.mAiring = mAiring;
		devCmdMap.put("airing", mAiring);
	}
	public void setmHilevel(int mHilevel) {
		this.mHilevel = mHilevel;
		devCmdMap.put("hilevel", mHilevel);
	}
	public void setmPrewash(int mPrewash) {
		this.mPrewash = mPrewash;
		devCmdMap.put("prewash", mPrewash);
	}
	public void setmStrong(int mStrong) {
		this.mStrong = mStrong;
		devCmdMap.put("strong", mStrong);
	}
	public void setmDisinfect(int mDisinfect) {
		this.mDisinfect = mDisinfect;
		devCmdMap.put("disinfect", mDisinfect);
	}
	public void setmTubelight(int mTubelight) {
		this.mTubelight = mTubelight;
		devCmdMap.put("tubelight", mTubelight);
	}
	public void setmEconomic(int mEconomic) {
		this.mEconomic = mEconomic;
		devCmdMap.put("economic", mEconomic);
	}
	public void setmMute(int mMute) {
		this.mMute = mMute;
		devCmdMap.put("mute", mMute);
	}
	public void setmStain(int mStain) {
		this.mStain = mStain;
		devCmdMap.put("stain", mStain);
	}
	/*
	 * 设置预约时间
	 * */
	public void setmPresetTimeWasher(double time){
		int hour = (int)time/1;
		int mins = (int) ((time%1)*60);
		Log.e(TAG, "预约 hour :"+hour+"  mins :"+mins);
		setmPresetWasher(1);//预约开关开启之后，预约时间才有效
		setmPresethrs(hour);
		setmPresetmins(mins);
	}
	private void setmPresethrs(int mPresethrs) {
		this.mPresethrs = mPresethrs;
		devCmdMap.put("presethrs", mPresethrs);
	}
	private void setmPresetmins(int mPresetmins) {
		this.mPresetmins = mPresetmins;
		devCmdMap.put("presetmins", mPresetmins);
	}
	public void setmWashtime(int mWashtime) {
		this.mWashtime = mWashtime;
		devCmdMap.put("washtime", mWashtime);
	}
	public void setmRinsings(int mRinsings) {
		this.mRinsings = mRinsings;
		devCmdMap.put("rinsings", mRinsings);
	}
	public void setmStainparams(int mStainparams) {
		this.mStainparams = mStainparams;
		devCmdMap.put("stainparams", mStainparams);
	}
//	public void setmYear(int mYear) {
//		this.mYear = mYear;
//		devCmdMap.put("year", mYear);
//	}

	public void setmMonth(int mMonth) {
		this.mMonth = mMonth;
		devCmdMap.put("month", mMonth);
	}
	public void setmYearHigh(int mYearHigh) {
		this.mYearHigh = mYearHigh;
		devCmdMap.put("yearhi", mYearHigh);
	}
	public void setmYearLow(int mYearLow) {
		this.mYearLow = mYearLow;
		devCmdMap.put("yearlo", mYearLow);
	}
	public void setmDay(int mDay) {
		this.mDay = mDay;
		devCmdMap.put("day", mDay);
	}
	public void setmHour(int mHour) {
		this.mHour = mHour;
		devCmdMap.put("hour", mHour);
	}
	public void setmMinute(int mMinute) {
		this.mMinute = mMinute;
		devCmdMap.put("minute", mMinute);
	}
	public void setmWeekday(int mWeekday) {
		this.mWeekday = mWeekday;
		devCmdMap.put("weekday", mWeekday);
	}
	public void setmSoftening(int mSoftening) {
		this.mSoftening = mSoftening;
		devCmdMap.put("softening", mSoftening);
	}
	public void setmSoftener(int mSoftener) {
		this.mSoftener = mSoftener;
		devCmdMap.put("softener", mSoftener);
	}

	public void setmAbluent(int mAbluent) {
		this.mAbluent = mAbluent;
		devCmdMap.put("abluent", mAbluent);
	}
	public void setmAbluentswitch(int mAbluentswitch) {
		this.mAbluentswitch = mAbluentswitch;
		devCmdMap.put("abluentswitch", mAbluentswitch);
	}
	/*******************end******************/

	/**
	 * @Title: sendCmdToDevice
	 * @Description: 向空调发送指令

	 * @param isWan：是否处于局域网
	 * @return void
	 */
	public void sendDevCmd(String to, String from,String cmd, boolean isWan,String domainStr) {
		if (isWan) {
			sendCmdLan(to, from, cmd);//userID 这个参数从绑定的列表中获取
			Log.e(TAG, "send cmd to dev in lan");
		} else {
			sendCmdWan(to,from,cmd,domainStr);
			Log.e(TAG, "send cmd to dev in wan");
		}
	}
	private Context context = null;
	public void sendCmdLan(String to,String from,String msg_body){
		if (to == null) {
			//Toast.makeText(context, "设备id不存在", Toast.LENGTH_LONG);
		}else{
			if (SipService.getMyInterface() != null) {
				SipService.getMyInterface().sendMsgInLan(to, from, msg_body);
			}
		}
	}
	public void sendCmdWan(String to,String from,String msg_body,String domainStr){
		if (to == null) {
			//Toast.makeText(context, "设备id不存在", Toast.LENGTH_LONG);
		}else{
			if (SipService.getMyInterface() != null) {
				SipService.getMyInterface().sendMsgInWAN(to, from, msg_body,domainStr);
			}

		}
	}



}
