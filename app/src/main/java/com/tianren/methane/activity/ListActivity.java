package com.tianren.methane.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tianren.methane.R;
import com.tianren.methane.constant.Constant;
import com.tianren.methane.fragment.QiGuiFragment;

/**
 * Created by Administrator on 2018/3/26.
 */

public class ListActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_list);
        int type = getIntent().getIntExtra("type", 0);
        String id = getIntent().getStringExtra("id");
        initFragment(type, id);
    }

    public static Intent newInstance(Context context, int type, String id) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        return intent;
    }
    private void initFragment(int type, String id) {
        switch (type) {
            case Constant.INTENT_TYPE_YANYANG://厌氧
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.container,
                                QiGuiFragment
                                        .newInstance(id))
                        .commit();
                break;

            default:
                break;
        }

    }

}
