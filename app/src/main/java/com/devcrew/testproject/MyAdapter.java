package com.devcrew.testproject;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
	
	ArrayList<Record> mNameList;
	public class ViewHolder extends RecyclerView.ViewHolder {
	    // each data item is just a string in this case
	    public TextView name_tv;

	    public ViewHolder(View v) {
	      super(v);
	      name_tv = (TextView) v.findViewById(R.id.NameTv);
	    }
	  }
	
	public void add(int position, Record item) {
	    mNameList.add(position, item);
	    notifyItemInserted(position);
	  }
	
	public MyAdapter(ArrayList<Record> namelist) {
	    mNameList = namelist;
	  }

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mNameList.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		String first = mNameList.get(position).getfName();
		String second = mNameList.get(position).getlName();
		holder.name_tv.setText(first +","+second);
	}
	
	// Create new views (invoked by the layout manager)
	  @Override
	  public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
	      int viewType) {
	    // create a new view
	    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
	    // set the view's size, margins, paddings and layout parameters
	    ViewHolder vh = new ViewHolder(v);
	    return vh;
	  }


}
