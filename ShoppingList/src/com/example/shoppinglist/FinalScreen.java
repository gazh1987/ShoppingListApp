package com.example.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FinalScreen extends Activity implements OnClickListener
{	
	Intent myIntent;
	Button _return;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_screen);
		
		_return = (Button)findViewById(R.id._return);
		_return.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) 
	{
		//REFERENCE: The following code is from 
		//http://stackoverflow.com/questions/4732184/how-to-finish-an-android-application
		
		myIntent = new Intent(getApplicationContext(), EnterDetails.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myIntent);
		
		//Reference complete
	}
}