package com.tianren.methane.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.blankj.aloglibrary.ALog;

import java.text.NumberFormat;

/**
 * Created by ang on 17/3/21.
 */

public class StringUtil {

    public static boolean isEmpty(String s) {
        return (s == null || s.isEmpty() || s.length() == 0 || s.equals("null"));
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 字符串是否equals判断
     **/
    public static boolean isEquals(String str1, String str2) {
        if ((str1 != null && str1.equals(str2)) || (str1 == null && str2 == null)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串和指定String[]中任意一个equals判断
     **/
    public static boolean isEquals(String str1, String[] list) {
        if (str1 != null) {
            for (String data : list) {
                if (str1.equals(data)) return true;
            }
        } else {
            for (String data : list) {
                if (data == null) return true;
            }
        }
        return false;
    }

    /**
     * 某字符串中指定字符串的个数
     **/
    public static int containCount(String text, String des) {
        int cnt = 0;
        int offset = 0;
        while ((offset = text.indexOf(des, offset)) != -1) {
            offset = offset + des.length();
            cnt++;
        }
        return cnt;
    }

    /**
     * 获取VersionName
     **/
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    /**
     * 获取VersionCode
     **/
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        if (android.os.Build.VERSION.RELEASE != null) {
            return android.os.Build.VERSION.RELEASE;
        } else {
            return "";
        }
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        if (android.os.Build.MODEL != null) {
            return android.os.Build.MODEL;
        } else {
            return "";
        }
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        if (android.os.Build.BRAND != null) {
            return android.os.Build.BRAND;
        } else {
            return "";
        }
    }

    /**
     * 手机唯一标识：DEVICE_ID + ANDROID_ID + Serial Number
     * 使用前请务必保证api23以上已获取读取设备权限
     * <p>
     * DEVICE_ID     非手机设备会返回null  部分定制系统会返回垃圾，如:zeros或者asterisks
     * ANDROID_ID    部分定制系统不同的设备会返回相同的ANDROID_ID 9774d56d682e549c  有些设备返回的值为null
     * Serial Number 部分手机返回null
     */
    public static String getUniqueId(Activity mActivity) {
        StringBuilder uniqueId = new StringBuilder("");
        TelephonyManager tm = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null && StringUtil.isNotEmpty(tm.getDeviceId())) {
            uniqueId.append(tm.getDeviceId());
        }
        if (StringUtil.isNotEmpty(Settings.System.getString(mActivity.getContentResolver(), Settings.System.ANDROID_ID))) {
            uniqueId.append(Settings.System.getString(mActivity.getContentResolver(), Settings.System.ANDROID_ID));
        }
        if (StringUtil.isNotEmpty(android.os.Build.SERIAL)) {
            uniqueId.append(android.os.Build.SERIAL);
        }
        return uniqueId.toString();
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 检测含有emoji表情的数量
     */
    public static int getEmojiCounts(String source) {
        int countEmoji = 0;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                countEmoji++;
            }
        }
        return countEmoji / 2;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 获取已连接的Wifi路由器的Mac地址
     */
    public static String getConnectedWifiMacAddress(Context context) {
        String connectedWifiMacAddress = "";
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                connectedWifiMacAddress = info.getBSSID();
//                ALog.e("info.getBSSID():" + info.getBSSID());
            }
//            List<ScanResult> wifiList = wifiManager.getScanResults();
//            for (int i = 0; i < wifiList.size(); i++) {
//                ScanResult result = wifiList.get(i);
//                if (info.getBSSID().equals(result.BSSID)) {
//                    connectedWifiMacAddress = result.BSSID;
//                }
//            }
        }
//        ALog.e("connectedWifiMacAddress:" + connectedWifiMacAddress);
        return connectedWifiMacAddress;
    }

    /**
     * 是否连接WIFI
     */
    public static boolean hasConnectedWifi(Activity activitiy) {
        WifiManager mWifiManager = (WifiManager) activitiy.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        return (mWifiManager.isWifiEnabled() && ipAddress != 0);
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值, 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || StringUtil.isEmpty(key)) {
            return "";
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null && tm.getDeviceId() != null) {
            return tm.getDeviceId();
        }
        return "";
    }

    /**
     * 从html中找到可网络显示的imgUrl
     **/
    public static String getImgUrlFromHtml(String html) {
        String url = "";
        if (html.contains("<img")) {
            html = html.substring(html.indexOf("<img"));
            String data = html.substring(0, html.indexOf(">"));
            if (data.contains("data_src")) {
                url = data.substring(data.indexOf("data-src=\"") + 10);
                url = url.substring(0, url.indexOf("\"") + 1);
            }
            if (data.contains("src")) {
                url = data.substring(data.indexOf("src=\"") + 5);
                url = url.substring(0, url.indexOf("\""));
            }
            if (!url.startsWith("http")) {
                url = "";
            }
            ALog.e("First url:" + url);
            int i = 0;
            while (StringUtil.isEmpty(url) && html.contains("<img")) {
                i++;
                html = html.substring(html.indexOf("<img") + 4);
                data = html.substring(0, html.indexOf(">"));
                if (data.contains("data_src")) {
                    url = data.substring(data.indexOf("data-src=\"") + 10);
                    url = url.substring(0, url.indexOf("\""));
                }
                if (data.contains("src")) {
                    url = data.substring(data.indexOf("src=\"") + 5);
                    url = url.substring(0, url.indexOf("\""));
                }
                ALog.e("第" + i + "次切割");
                ALog.e("data:" + data);
                ALog.e("url:" + url);
                if (!url.startsWith("http")) {
                    url = "";
                }
            }
            ALog.e("循环走完，最终url:" + url);
        }
        return url;
    }

    /**
     * 根据两点距离获取高德地图缩放级别
     **/
    public static int getAmapScaleByDistance(Activity mActivity, float distance) {
        int scale;
        if (distance <= 100) {
            scale = 19;
        } else if (distance <= 200) {
            scale = 18;
        } else if (distance <= 500) {
            scale = 17;
        } else if (distance <= 1200) {
            scale = 16;
        } else if (distance <= 2000) {
            scale = 15;
        } else if (distance <= 4000) {
            scale = 14;
        } else if (distance <= 7000) {
            scale = 13;
        } else if (distance <= 12000) {
            scale = 12;
        } else if (distance <= 25000) {
            scale = 11;
        } else if (distance <= 40000) {
            scale = 10;
        } else if (distance <= 60000) {
            scale = 9;
        } else if (distance <= 100000) {
            scale = 8;
        } else if (distance <= 400000) {
            scale = 7;
        } else if (distance <= 1200000) {
            scale = 6;
        } else if (distance <= 3000000) {
            scale = 5;
        } else if (distance <= 10000000) {
            scale = 4;
        } else {
            scale = 3;
        }
        // 上方分级是按照S8Plus测试得出，普通分辨率需适当降低
        if (DpUtil.getScreenType(mActivity) != 3 && scale < 19) {
            scale--;
        }
//        ALog.e("distance:" + distance);
//        ALog.e("scale:" + scale);
        return scale;
    }

    /**
     * 将double转为数值，并最多保留num位小数。例如当num为2时，1.268为1.27，1.2仍为1.2；1仍为1，而非1.00;100.00则返回100。
     *
     * @param d
     * @param num 小数位数
     * @return
     */
    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);//保留两位小数
        nf.setGroupingUsed(false);//去掉数值中的千位分隔符

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!s2.substring(i - 1, i).equals("0")) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 将double转为数值，并最多保留num位小数。
     *
     * @param d
     * @param num 小数个数
     * @param defValue 默认值。当d为null时，返回该值。
     * @return
     */
    public static String double2String(Double d, int num, String defValue){
        if(d==null){
            return defValue;
        }

        return double2String(d,num);
    }
}
