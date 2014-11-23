package com.example.shoppinglist;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListScreen extends ListActivity implements View.OnClickListener
{
	//String arrays containing album information
	String[] artist;
	String[] album;
	String[] p;
	
	Boolean canProgressToCheckout = false;
	
	//For displaying current user information
	TextView name, amount, email;
	
	//ArrayList to store the users album selections
	ArrayList<Albums> shoppingCart; 
	
	//A Button to continue to the checkout page
	ImageButton checkOut;
	
	//A user object to store information about the user
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_screen_layout);
		
		//get the user information entered in the enter details screen
		user = (User)getIntent().getExtras().getParcelable("user");
		
		//Create a new Text View
		name = (TextView)findViewById(R.id.name);
		amount = (TextView)findViewById(R.id.amount);
		email = (TextView)findViewById(R.id.email);
		
		amount.setTextColor(Color.parseColor("#8f044a"));
		
		//Set the text of the TextView to be that of the current user
		name.setText("Hello, " + user.GetName());
		amount.setText("Budget Remaining: €" + String.format("%.2f", user.GetSpendingMoney()));
		email.setText("Email: " + user.GetEmailAddress());
		
		//create a shopping cart
		shoppingCart = new ArrayList<Albums>();
		
		//get the information for the albums list
		Resources res = getResources();
		artist = res.getStringArray(R.array.artist);
		album = res.getStringArray(R.array.album);
		p = res.getStringArray(R.array.price);
		
		//attach a listener to the checkout button
		checkOut=(ImageButton)findViewById(R.id.button1);
		checkOut.setOnClickListener(this);
		
		//Set the list adapter
		setListAdapter(new MyCustomAdapter());
	}
	
	//Override the onBackPressed method so the user can not go back to the enter details screen
	@Override
	public void onBackPressed() 
	{
		//Toast message telling the user he/she can not return to the enter details screen
		//I do not want users to be able to go back because if they can go back they could 
		//easily change the amount of spending money they have.
		Context context = getApplicationContext();
		CharSequence text = "You can not go back to the \"Enter Details\" screen";
		int duration = Toast.LENGTH_SHORT;
		Toast t = Toast.makeText(context, text, duration);
		t.show();
	}
	
	@Override
	public void onClick(View v) 
	{
		if (canProgressToCheckout == true)
		{
			Intent intent = new Intent (ListScreen.this, Checkout.class);
			intent.putExtra("shoppingCart", shoppingCart);
			intent.putExtra("user", user);
			startActivity(intent);
		}
		else
		{
			Context context = getApplicationContext();
			CharSequence text = "You can not continue to the checkout as you have not selected any items";
			int duration = Toast.LENGTH_SHORT;
			Toast t = Toast.makeText(context, text, duration);
			t.show();
		}
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id)
	{	
		canProgressToCheckout = true;
		
		if (user.GetSpendingMoney() - Float.parseFloat(p[position]) >= 0)
		{
			///Display a toast message saying that the user added a particular album to his/her shopping cart
			Context context = getApplicationContext();
			CharSequence text = artist[position] + " \"" + album[position] + "\" has been added to your shopping cart";
			int duration = Toast.LENGTH_SHORT;
			Toast t = Toast.makeText(context, text, duration);
			t.show();
		
			//Create a new Album object based on the users selection
			Albums a = new Albums(artist[position], album[position], Float.parseFloat(p[position]));
			
			//Add the album object created to the shopping cart list
			shoppingCart.add(a);
			
			//Debit the user for the album purchase
			user.DebitSpendingMoney(Float.parseFloat(p[position]));
			
			//Display the new user details (updated spendingMoney)
			name.setText("Hello, " + user.GetName());
			email.setText("Email: " + user.GetEmailAddress());
			amount.setText("Budget Remaining: €" + String.format("%.2f", user.GetSpendingMoney()));
		}
		else
		{
			//Display a toast message to let the user know he/she cannot afford the item
			Context context = getApplicationContext();
			CharSequence text = "You do not have enough money for this purchase";
			int duration = Toast.LENGTH_SHORT;
			Toast t = Toast.makeText(context, text, duration);
			t.show();
		}
	}

	@SuppressWarnings("rawtypes")
	class MyCustomAdapter extends ArrayAdapter
	{
		@SuppressWarnings("unchecked")
		MyCustomAdapter() 
		{
			super(ListScreen.this, R.layout.row, artist);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View row = convertView;
			if(row==null)
			{
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}
			
			TextView label = (TextView)row.findViewById(R.id.label);
			TextView description = (TextView)row.findViewById(R.id.description);
			TextView price = (TextView)row.findViewById(R.id.price);
			ImageView icon = (ImageView)row.findViewById(R.id.icon);
			//EditText quantity = (EditText)row.findViewById(R.id.quantity);
			
			//Set the text of the album appropriately
			label.setText(artist[position]);
			description.setText(album[position]);
			price.setText("€" + p[position]);
			
			//Display the appropriate album cover images
			if(artist[position].equals("The Beatles"))
			{
				icon.setImageResource(R.drawable.abbeyroad);
			}
			else if(artist[position].equals("David Bowie"))
			{
				icon.setImageResource(R.drawable.thenextday);
			}
			else if(artist[position].equals("Peter Gabriel"))
			{
				icon.setImageResource(R.drawable.so);
			}
			else if(artist[position].equals("Jimi Hendrix"))
			{
				icon.setImageResource(R.drawable.axisboldaslove);
			}
			else if(artist[position].equals("Interpol"))
			{
				icon.setImageResource(R.drawable.antics);
			}
			else if(artist[position].equals("REM"))
			{
				icon.setImageResource(R.drawable.monster);
			}
			else if(artist[position].equals("Kurt Vile"))
			{
				icon.setImageResource(R.drawable.wakingonaprettydaze);
			}
			else if(artist[position].equals("Led Zeppelin"))
			{
				icon.setImageResource(R.drawable.three);
			}
			else if(artist[position].equals("Pearl Jam"))
			{
				icon.setImageResource(R.drawable.vs);
			}
			else
			{
				icon.setImageResource(R.drawable.blonndeonblonde);
			}
			
			return(row);
		}
	}
}