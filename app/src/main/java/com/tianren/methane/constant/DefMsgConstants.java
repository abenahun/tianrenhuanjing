/**  
* @Title: DefMsgConstants.java
* @Package com.hisense.acclient.utils.cc
* @Description: TODO(用一句话描述该文件做什么)
* @author lixiaolan  
* @date 2014-9-1 下午02:50:46
* @version V1.0  
*/

package com.tianren.methane.constant;

import com.tianren.methane.bean.FindDevInfo;

import java.util.ArrayList;

/**
 * @ClassName: DefMsgConstants
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lixiaolan
 * @date 2014-9-1 下午02:50:46
 *
 */
public class DefMsgConstants {
	//*****************
	//常量为>1000的值
	//*****************
	public static final int MSG_REGISTER_TIME_OUT 	 = 0;
	public static final int MSG_REGISTER_SUCCESS 	 = 1;
	public static final int MSG_REGISTER_FAILURE 	 = 2;
	public static final int MSG_REGISTER_EXCEPTION   = 3;
	public static final int MSG_LOGIN_SUCCESS 		 = 4;
	public static final int MSG_LOGIN_FAILURE 		 = 5;
	public static final int MSG_LOAD_IMAGE 	 	 = 8;
	public static final int MSG_LOGIN_TIME_OUT 	 = 9;
	public static final int MSG_BIND_SUCCESS 	 = 10;
	public static final int MSG_BIND_FAIL		 = 11;
	public static final int MSG_UNBIND_SUCCESS 	 = 12;
	public static final int MSG_UNBIND_FAIL		 = 13;
	public static final int MSG_GET_BIND_RELATION_SUCCESS = 14;
	public static final int MSG_PUSH = 15;
	public static final int MSG_FIND_PASSWD_SUCCESS  = 16;
	public static final int MSG_FIND_PASSWD_FAIL 	 = 17;
	public static final int MSG_RESET_PASSWD_SUCCESS = 18;
	public static final int MSG_RESET_PASSWD_FAIL 	 = 19;
	public static final int MSG_NO_CONNECT_NET		 = 20;
	
	public static final int MSG_GET_BIND_DEV_INFO_SUCCESS = 21;
	public static final int MSG_GET_BIND_DEV_INFO_FAIL = 22;
	public static final int MSG_UPDATE_NICKNAME_SUCCESS = 23;
	public static final int MSG_UPDATE_NICKNAME_FAIL = 24;
	public static final int MSG_UPDATE_BARCODE_SUCCESS = 25;
	public static final int MSG_UPDATE_BARCODE_FAIL = 26;
	public static final int MSG_MODIFY_PASSWD_SUCCESS = 27;
	public static final int MSG_MODIFY_PASSWD_FAIL = 28;
	public static final int MSG_CONFIG_FINISH = 29;	//与AP全部配置结束
	public static final int MSG_UPDATE_USERINFO_SUCCESS = 30;
	public static final int MSG_UPDATE_USERINFO_FAIL = 31;
	public static final int MSG_GET_USERINFO_SUCCESS = 32;
	public static final int MSG_GET_USERINFO_FAIL = 33;
	public static final int MSG_DEVICE_ONLINE = 34;
	public static final int MSG_AGREEMENT = 35;
	public static final int MSG_BIND_EXCESSIVE = 36;
	public static final int MSG_PASSWORD_SET_TIME_OUT = 37;
	public static final int MSG_MODIFY_PASSWD_TIME_OUT = 38;
	
	public static ArrayList<FindDevInfo> findDevInfos = new ArrayList<FindDevInfo>();
}
