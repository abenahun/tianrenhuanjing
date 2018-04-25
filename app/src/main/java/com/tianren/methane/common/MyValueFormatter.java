package com.tianren.methane.common;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tianren.methane.utils.StringUtil;

/**
 * Created by JKWANG-PC on 2016/11/21.
 */

public class MyValueFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return StringUtil.double2String(value, 2);
    }
}
