package com.example.shoppinglist;

import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EnterDetails extends Activity implements View.OnClickListener
{	
	//String arrays for storing spinner information
	String[] jobs;
	String[] ages;
	
	//Button to allow the user continue to the next screen
	Button cont;
	
	//EditText fields to store input information from the user 
	EditText userInputName, userInputSpendingMoney, userInputEmailAddress;
	
	//Convert the EditText fields into these variables to pass as extras with the intent 
	String name, emailAddress, spendingMoney;
	
	
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.enter_details_layout);
		
		//import the users input information
		userInputName = (EditText)findViewById(R.id.name_input);
		userInputSpendingMoney = (EditText)findViewById(R.id.spendingMoneyInput);
		userInputEmailAddress = (EditText)findViewById(R.id.emailAddrInput);
		
		//import array data for spinners
		Resources res = getResources();
		jobs = res.getStringArray(R.array.jobs_array);
		ages = res.getStringArray(R.array.ages_array);
		
		//Create Spinners
		Spinner spinJobs = (Spinner)findViewById(R.id.jobSpinner);
		ArrayAdapter<String> aaJobs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobs);
		
		Spinner spinAges = (Spinner)findViewById(R.id.ageSpinner);
		ArrayAdapter<String> aaAges = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
		
		aaJobs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinJobs.setAdapter(aaJobs);
		
		aaAges.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinAges.setAdapter(aaAges);
		
		//Set on click listener to continue button
		cont=(Button)findViewById(R.id.continueButton);
		cont.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v)
	{
		//convert EditText fields into strings
		name = userInputName.getText().toString();
		spendingMoney = userInputSpendingMoney.getText().toString();
		emailAddress = userInputEmailAddress.getText().toString();
		
		//Check if correct details have been entered
		if (name.trim().equals("") || spendingMoney.trim().equals("") || emailAddress.trim().equals(""))
		{
			//If correct details have not been entered, display a toast message saying so
			Context context = getApplicationContext();
			CharSequence text = "You have not entered the correct details";
			int duration = Toast.LENGTH_SHORT;
			Toast t = Toast.makeText(context, text, duration);
			t.show();
		}
		else
		{
			//create a new intent
			Intent intent = new Intent (EnterDetails.this, ListScreen.class);
			
			//Add the user object to the intent
			intent.putExtra("user", new User (name, spendingMoney, emailAddress));
			
			//Run the intent
			startActivity(intent);
			
			//Take this activity off the stack as it will not be used again.
			finish();
		}
	}
}
