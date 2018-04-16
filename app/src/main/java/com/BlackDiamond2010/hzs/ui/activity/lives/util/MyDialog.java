package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;


public class MyDialog {

	public static void getDialogAndshow(Activity activity,final DialogCallBack callback,String content ,String sureStr,String cancleStr,String tipStr) {
		final Dialog dialog1 = new Dialog(activity, R.style.MyDialogDelect);
		LayoutInflater mInflater = LayoutInflater.from(activity); 
		View v = mInflater.inflate(R.layout.delect_dialog, null);
		
		LinearLayout lin = (LinearLayout) v.findViewById(R.id.content_cont);
		dialog1.setContentView(lin, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		dialog1.show();
//		bbs_report.setContentView(dialog_report, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.WRAP_CONTENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		TextView tip = (TextView) dialog1.findViewById(R.id.tip);
		TextView contentText = (TextView) dialog1.findViewById(R.id.dialog_content);
		TextView sureText = (TextView) dialog1.findViewById(R.id.sure);
		TextView delectText = (TextView) dialog1.findViewById(R.id.cancle);
		tip.setText(tipStr);
		contentText.setText(content);
		delectText.setText(cancleStr);
		sureText.setText(sureStr);
		delectText.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog1.dismiss();
						callback.dialogCancle();
					}
				});
		sureText.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog1.dismiss();
						callback.dialogSure();
					}
				});
	}
	
	public interface DialogCallBack{
		void dialogSure();
		void dialogCancle();
	}

}
