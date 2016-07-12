package com.sprint22.apps.android.hydration.information;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sprint22.apps.android.hydration.R;

public class InformationMain extends ListActivity {

	private static final String TAG = "hydration";

	private String[] helpTopics = {"Why water is important",
			"How much water do we need?",
			"Can water help me lose weight?",
			"How do I increase my water intake?",
			"10 Interesting water facts",
			"Warnings", 
		    "Disclaimer"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Hide the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.information_main_list);

		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

		for (int i =0; i < helpTopics.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", helpTopics[i]);
			list.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, list, 
				R.layout.information_row, new String[] {"title"},
				new int[] { R.id.row_title }   );

		setListAdapter(adapter);
	}



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//Log.d("iHydration", "Position is (" + position + ") Id is (" + id + ")");

		int layoutId = R.layout.why_water_is_important;

		switch (position)
		{	
		case 0 : {
			layoutId = R.layout.why_water_is_important;
			break;
		}
		case 1 : {
			layoutId = R.layout.how_much_water_do_i_need;
			break;
		}
		case 2: {
			layoutId = R.layout.can_water_help_me_lose_weight;
			break;
		}
		case 3: {
			layoutId = R.layout.how_do_i_increase_my_water_intake;
			break;
		}
		case 4 : {
			layoutId = R.layout.water_facts;
			break;
		}
		case 5 : {
			layoutId = R.layout.warnings;
			break;
		}
		case 6 : {
			layoutId = R.layout.disclaimer;

		}
		}

		Intent i = new Intent(this, HydrationTips.class);
		i.putExtra("informationLayoutKey", layoutId);
		startActivity(i);
	}


	private class HelpListAdapter extends BaseAdapter
	{
		Context context = null;
		LayoutInflater mLayoutInflater;

		HelpListAdapter(Context context, LayoutInflater layoutInflator)
		{
			this.context = context;
			this.mLayoutInflater = layoutInflator;
		}
		private String[] helpTopics = {"Why water is important",
				"How much water do we need?",
				"Can water help me lose weight?",
				"How do I increase my water intake?",
		"Warnings"};
		public int getCount() {
			return helpTopics.length;
		}

		public Object getItem(int position) {
			return helpTopics[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup arg2) {

			View view = null;
			if (convertView == null)
			{
				//inflate from the quote row source
				view =  mLayoutInflater.inflate(R.layout.information_row, null);	

				TextView textView = (TextView) view.findViewById(R.id.row_title);
				textView.setText(helpTopics[position]);
			}
			else
			{
				view = convertView;

			}
			return view;
		}		
	}
}
