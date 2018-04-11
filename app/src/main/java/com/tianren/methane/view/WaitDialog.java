package com.tianren.methane.view;

import java.lang.ref.WeakReference;

import com.tianren.methane.R;
import com.tianren.methane.constant.DefMsgConstants;

import android.R.bool;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class WaitDialog extends Dialog {

	private final static String TAG = "WaitDialog";
	private final static int LOAD_IMG_DELAY = 100;
	private Context context;
	private ImageView img_wait;
	private TextView tx_wait_tip;
	private int count_img = 0;
	private onKeyBackListener keyBackListener;//add by changchen
	private TextView tx_time; 
	private int timeCount = 5; 
	
	public WaitDialog(Context context, int theme) {
		super(context, theme);
		Log.i(TAG, "WaitDialog contructor");
		setContentView(R.layout.cc_dialog_wait);
		this.context = context;
		initView();
		
		mHandler.sendEmptyMessage(DefMsgConstants.MSG_LOAD_IMAGE);
	}
	
	public void initView() {
		img_wait = (ImageView)findViewById(R.id.img_wait);
		tx_wait_tip = (TextView)findViewById(R.id.tx_wait_tip);
		tx_time = (TextView)findViewById(R.id.tx_time);
	}
    
    public TextView getTxTime() {
		return tx_time;
	}
    
	public MyHandler mHandler = new MyHandler(this);
	
	private static class MyHandler extends Handler {
		WeakReference<WaitDialog> dialog; 
		MyHandler(WaitDialog dialog){  	            
			this.dialog = new WeakReference<WaitDialog>(dialog);  
	    }
		
		@Override  
	    public void handleMessage(Message msg) {
			switch(msg.what){
			case DefMsgConstants.MSG_LOAD_IMAGE:
				int i = 0;
				if (dialog != null) {
					i = dialog.get().count_img % 10;
				}
				switch (i) {
				case 0:
					dialog.get().img_wait.setImageResource(R.mipmap.loading1);
					break;
				case 1:
					dialog.get().img_wait.setImageResource(R.mipmap.loading2);
					break;
				case 2:
					dialog.get().img_wait.setImageResource(R.mipmap.loading3);
					break;
				case 3:
					dialog.get().img_wait.setImageResource(R.mipmap.loading4);
					break;
				case 4:
					dialog.get().img_wait.setImageResource(R.mipmap.loading5);
					break;
				case 5:
					dialog.get().img_wait.setImageResource(R.mipmap.loading6);
					break;
				case 6:
					dialog.get().img_wait.setImageResource(R.mipmap.loading7);
					break;
				case 7:
					dialog.get().img_wait.setImageResource(R.mipmap.loading8);
					break;
				case 8:
					dialog.get().img_wait.setImageResource(R.mipmap.loading9);
					break;
				case 9:
					dialog.get().img_wait.setImageResource(R.mipmap.loading10);
					break;					
				default:
					break;
				}
				dialog.get().mHandler.sendEmptyMessageDelayed(DefMsgConstants.MSG_LOAD_IMAGE, LOAD_IMG_DELAY);
				dialog.get().count_img++;
				break;
			}
		}
	}	
	
	public TextView getTxWaitTip() {
		return tx_wait_tip;
	}
	
	public void setOnKeyBackListener(onKeyBackListener keyBackListener) {
		this.keyBackListener = keyBackListener;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.i(TAG, "keyCode == KeyEvent.KEYCODE_BACK");
			if (keyBackListener != null) {
				keyBackListener.onKeyBack();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	//返回按键监听, add by changchen
	public interface onKeyBackListener {
		public void onKeyBack();
	}
	
	@Override
	public void dismiss() {
		Log.i(TAG, "dismiss");
		mHandler.removeMessages(DefMsgConstants.MSG_LOAD_IMAGE);
		img_wait = null;
		System.gc();
		super.dismiss();
	}
	
}
