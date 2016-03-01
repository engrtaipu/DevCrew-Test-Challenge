package com.devcrew.testproject;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.devcrew.testproject.MyAdapter.ViewHolder;
import com.devcrew.testproject.TestDailogFragment.onSubmitListener;

public class MainActivity extends AppCompatActivity implements
		onSubmitListener, AsyncTaskCompleteListener {
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter<ViewHolder> mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	ArrayList<Record> myNameList = new ArrayList<Record>();
	FloatingActionButton floatingBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		floatingBtn = (FloatingActionButton) findViewById(R.id.fab);
		mRecyclerView = (RecyclerView) findViewById(R.id.mRecylcerView);
		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);
		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		initValues();
		
		floatingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TestDailogFragment fm = new TestDailogFragment();
				fm.mListener = MainActivity.this;
				fm.show(getSupportFragmentManager(), "");
			}
		});

	}

	private void initValues() {
		// TODO Auto-generated method stub
		if (StaticService.isInternetOn(MainActivity.this)) {
			new MyTask(MainActivity.this).execute("");
		} else {
			Toast.makeText(MainActivity.this, "Not connected to internet",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void setOnSubmitListener(String arg) {
		// TODO Auto-generated method stub
		if (!arg.equals("") && !arg.equals(null)) {
			String[] result = arg.split(",");
			((MyAdapter) mAdapter).add(myNameList.size(), new Record(result[0],
					result[1]));
		} else {
			// Toast.makeText(MainActivity.this, "No record added",
			// Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onTaskComplete(ArrayList<Record> result) {
		// TODO Auto-generated method stub
		if(result.size()>0){
			myNameList = result;
			mAdapter = new MyAdapter(myNameList);
			mRecyclerView.setAdapter(mAdapter);
		}
	}

}
