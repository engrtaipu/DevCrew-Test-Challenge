package com.devcrew.testproject;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class TestDailogFragment extends DialogFragment {

	Button mButton;
	EditText mFNameEdt, mLNameEdt;
	onSubmitListener mListener;
	String text = "";

	interface onSubmitListener {
		void setOnSubmitListener(String arg);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.test_alert_dialog);
		/*
		 * dialog.getWindow().setBackgroundDrawable( new
		 * ColorDrawable(Color.TRANSPARENT));
		 */
		dialog.show();
		mButton = (Button) dialog.findViewById(R.id.SubmitBtn);
		mFNameEdt = (EditText) dialog.findViewById(R.id.FNameEdt);
		mLNameEdt = (EditText) dialog.findViewById(R.id.LNameEdt);
		mFNameEdt.setText(text);
		mLNameEdt.setText(text);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String fn = mFNameEdt.getText().toString();
				String ln = mLNameEdt.getText().toString();
				if (!fn.equals(" ") && !fn.equals("") && !ln.equals(" ")
						&& !ln.equals("")) {
					mListener.setOnSubmitListener(fn + "," + ln);
				}
				dismiss();
			}
		});
		return dialog;
	}

}
