package com.robertboloc.presence;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.robertboloc.presence.pojo.Interval;

public class ActivityListAdapter extends ArrayAdapter<Interval> {

	private final Context ctx;
	private final ArrayList<Interval> values;
	
	public ActivityListAdapter(Context context, ArrayList<Interval> values){
		super(context, R.layout.activity_list_item, values);
		this.ctx = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_list_item, parent, false);
		TextView startView = (TextView) rowView.findViewById(R.id.timestart);
		TextView stopView = (TextView) rowView.findViewById(R.id.timestop);
		TextView diffView = (TextView) rowView.findViewById(R.id.timediff);
		
		Interval i = values.get(position);
		Date startDate = new Date(i.getTimestart()*1000); 
		startView.setText(startDate.toGMTString());
		
		Date stopDate = new Date(i.getTimestop()*1000);
		stopView.setText(stopDate.toGMTString());
		
		int timediff = i.getTimediff();
		diffView.setText(doubleDigit((timediff / 3600))+":"+doubleDigit(((timediff % 3600) / 60))+":"+doubleDigit((timediff % 60)));
		
		return rowView;
	}
	
	private String doubleDigit(int digit){
		if(digit < 10){
			return "0"+digit;
		}else{
			return String.valueOf(digit);
		}
	}
}
