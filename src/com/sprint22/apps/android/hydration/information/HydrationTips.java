package com.sprint22.apps.android.hydration.information;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.sprint22.apps.android.hydration.AbstractActivity;
import com.sprint22.apps.android.hydration.R;

public class HydrationTips extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialise();
	}
	
	protected void initialise() {
		
		Bundle extras = getIntent().getExtras();

		int informationLayoutKey = extras.getInt("informationLayoutKey");
		
		setContentView(informationLayoutKey);
		
		Button button = (Button) findViewById(R.id.button_back);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
					finishThis();
			}
		});
	}

	private void finishThis() {
		this.finish();
	}

	public int getHelpLayout() {
		return 0;
	}
	
}

