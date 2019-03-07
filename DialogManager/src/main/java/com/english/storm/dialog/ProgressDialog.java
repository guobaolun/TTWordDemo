package com.english.storm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


public class ProgressDialog extends Dialog {

	OnDispatchListener listener;
	private final TextView textview;


	public ProgressDialog(Context context ) {
		super(context, R.style.ProgressDialog);
		View view = View.inflate(context, R.layout.dialog_progress,null);
		textview =  view.findViewById(R.id.textview);
		setContentView(view);
	}




	public void setText(String text) {
		textview.setText(text);
		textview.setVisibility(View.VISIBLE);
	}

	public void setDispatchListener(OnDispatchListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (listener !=null){
			listener.onDispatch();
		}
		return super.dispatchKeyEvent(event);
	}


	public interface OnDispatchListener {

		void onDispatch();
	}

}
