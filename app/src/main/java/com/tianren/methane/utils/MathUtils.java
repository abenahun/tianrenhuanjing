package com.tianren.methane.utils;

import java.text.DecimalFormat;

/**
 * @author Mr.Qiu
 * @date 2018/5/30
 */

public class MathUtils {
    /**
     * 保留两位小数
     *
     * @param f
     * @return
     */
    public static String scale2(Double f) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(f);
    }

    public static String scale(Double f) {
        DecimalFormat df = new DecimalFormat("#0");
        return df.format(f);
    }
}
