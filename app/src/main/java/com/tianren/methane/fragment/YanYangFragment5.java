package com.tianren.methane.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianren.methane.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author qiushengtao
 */
public class YanYangFragment5 extends BaseFragment{

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_yan_yang_fragment5, container, false);
        initView();
        return view;
    }

    private void initView() {
    }
}
