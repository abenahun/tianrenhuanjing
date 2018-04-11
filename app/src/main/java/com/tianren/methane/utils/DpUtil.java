package com.tianren.methane.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.blankj.aloglibrary.ALog;

import java.lang.reflect.Field;

/**
 * Created by ang on 17/3/8.
 */

public class DpUtil {
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //这个是标准算法
    public static int DpToPx(Activity activity, int size) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int Px2Sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int Sp2Px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕字体密度
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度
     */
    public static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 获取顶部状态栏高度
     */
    public static int getStatusBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取底部导航栏高度（魅族获取SmartBar高度）
     */
    public static int getNavigationBarHeight(Activity mActivity) {
        final boolean isMeiZu = Build.MANUFACTURER.equals("Meizu");

        final boolean autoHideSmartBar = Settings.System.getInt(mActivity.getContentResolver(), "mz_smartbar_auto_hide", 0) == 1;

        if (isMeiZu) {
            if (autoHideSmartBar || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                return 0;
            } else {
                try {
                    Class c = Class.forName("com.android.internal.R$dimen");
                    Object obj = c.newInstance();
                    Field field = c.getField("mz_action_button_min_height");
                    int height = Integer.parseInt(field.get(obj).toString());
                    return mActivity.getResources().getDimensionPixelSize(height);
                } catch (Throwable e) { // 不自动隐藏smartbar同时又没有smartbar高度字段供访问，取系统navigationbar的高度
                    return getNormalNavigationBarHeight(mActivity);
                }
            }
        } else {
            return getNormalNavigationBarHeight(mActivity);
        }
    }

    protected static int getNormalNavigationBarHeight(final Context ctx) {
        try {
            final Resources res = ctx.getResources();
            int rid = res.getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid > 0) {
                boolean flag = res.getBoolean(rid);
                if (flag) {
                    int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        return res.getDimensionPixelSize(resourceId);
                    }
                }
            }
        } catch (Throwable e) {
            ALog.e("FBTools", "getNormalNavigationBarHeight() exception:" + e.getMessage());
        }
        return 0;
    }

    /**
     * 判断屏幕类型（分辨率和density）
     */
    public static Integer getScreenType(Context context) {
        float density = getDensity(context);
        int screenWidth = getScreenWidth(context);
        if (screenWidth >= 1400 && density >= 3.0) { //旗舰机型超高分辨率
            return 3;
        }
        if (screenWidth >= 1000 && density >= 2.5) { //高分辨率，1980*1080 1080p
            return 2;
        }
        if (screenWidth >= 600 && density >= 2) { //一般分辨率 720p
            return 1;
        }
        if (screenWidth <= 600 && density <= 2) { //低分辨率
            return 0;
        }
        return 0;
    }

    /**
     * 是否是全面屏
     */
    public static boolean isFullScreen(Context context) {
        float screenWidth = getScreenWidth(context);
        float screenHeight = getScreenHeight(context);
        float proportion = screenHeight / screenWidth;
        return getScreenType(context) >= 2 && proportion > 1.8;
    }

}
