package com.sprint22.apps.android.hydration.help;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sprint22.apps.android.hydration.R;

public class HelpActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();

		int informationLayoutKey = extras.getInt("helpLayoutKey");		
		setContentView(informationLayoutKey);
		
		Button closeButton = (Button)findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				close();
			}
		});
	}

	public void close(){
		this.finish();
	}
}
