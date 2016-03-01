package com.devcrew.testproject;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Integer, ArrayList<Record>> {

	private Activity activity;
	private ProgressDialog dialog;
	private AsyncTaskCompleteListener callback;
	public MyTask(Activity act) {
		this.activity = act;
		this.callback = (AsyncTaskCompleteListener)act;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(activity);
		dialog.setMessage("Loading Please Wait...");
		dialog.setCancelable(false);
		dialog.show();
	}
	
	@Override
	protected ArrayList<Record> doInBackground(String... urls) {
		// TODO Auto-generated method stub
		ArrayList<Record> myNameList = new ArrayList<Record>();
		Record item1 = new Record("Kamran", "Ghani");
		Record item2 = new Record("abdul", "Wahab");
		Record item3 = new Record("abid", "Nawaz");
		Record item4 = new Record("fida", "khan");
		Record item5 = new Record("Shams", "alam");
		myNameList.add(item1);
		myNameList.add(item2);
		myNameList.add(item3);
		myNameList.add(item4);
		myNameList.add(item5);
	         return myNameList;
	     
	}

	@Override
	protected void onPostExecute(ArrayList<Record> result) {
		super.onPostExecute(result);
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
		callback.onTaskComplete(result);
	}

}
