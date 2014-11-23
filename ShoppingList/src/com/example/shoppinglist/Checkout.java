package com.example.shoppinglist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Checkout extends Activity implements OnClickListener
{
	ArrayList<Albums> shoppingCart;
	TextView artist, album, price, preVat, vat, totalPrice;
	float tempTotal, tempPrice, tempVat, vatCalc = (float) 0.21;
	User user;
	String preVatForEmail, vatForEmail, totalPriceForEmail;
	
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.checkout_screen);
		
		//REFERENCE: This code is taken from 
		//http://androidideasblog.blogspot.ie/2010/02/passing-list-of-objects-between.html
		
		Bundle bundle = getIntent().getExtras();
        shoppingCart = bundle.getParcelableArrayList("shoppingCart");
        
        //REFERENCE Complete
        
        //REFERENCE: This code is from 
        //http://stackoverflow.com/questions/10107442/android-how-put-parcelable-object-to-intent-and-use-getparcelable-method-of-bu
        
        user = (User)getIntent().getExtras().getParcelable("user");
        
        //REFERENCE Complete
		
        artist = (TextView)findViewById(R.id.artist);
        album = (TextView)findViewById(R.id.album);
        price = (TextView)findViewById(R.id.price);
        
        for (int i = 0; i < shoppingCart.size(); i ++)
        {
        	artist.append("\n" + shoppingCart.get(i).GetArtist());
        	album.append("\n\t" + shoppingCart.get(i).GetAlbum());
        	price.append("\n\t€" + shoppingCart.get(i).GetPrice());
        }
        
        for (int i = 0; i < shoppingCart.size(); i++)
        {
        	tempTotal += shoppingCart.get(i).GetPrice();
        }
        
        tempVat = tempTotal * vatCalc;
        vat = (TextView)findViewById(R.id.vat);
        vat.setText("€" + String.format("%.2f", tempVat));
        vat.setTextColor(Color.parseColor("#8f044a"));
        
        preVat = (TextView)findViewById(R.id.preVat);
        tempTotal = tempTotal - tempVat;
        preVat.setText("€" + String.format("%.2f", tempTotal));
        preVat.setTextColor(Color.parseColor("#8f044a"));
        
        totalPrice = (TextView)findViewById(R.id.totalPrice);
        tempPrice = tempTotal + tempVat;
        totalPrice.setText("€" + String.format("%.2f", tempPrice));
        preVat.setTextColor(Color.parseColor("#8f044a"));
        
        preVatForEmail = preVat.getText().toString();
        vatForEmail = vat.getText().toString();
        totalPriceForEmail = totalPrice.getText().toString();
        
        Button purchaseItems = (Button)findViewById(R.id.purchase);
        purchaseItems.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) 
	{
		//REFERENCE: This code is from 
		//http://stackoverflow.com/questions/14374578/using-asynctask-to-send-android-email
		
		final GMailSender sender = new GMailSender("cdworldapp@gmail.com", "cdworldpassword");
	    new AsyncTask<Void, Void, Void>() 
	    {
	        @Override public Void doInBackground(Void... arg)
	        {
	            try 
	            {   
	                sender.sendMail("CD World: Reciept",   
	                    "Hello " + user.GetName() + ".\n\nPlease find your receipt shown below\n\n"
	                    		+ "Before Vat: " + preVatForEmail
	                    		+ "\nVAT 21% : " + vatForEmail
	                    		+ "\nTotal Price: " + totalPriceForEmail
	                    		+ "\n\nThank you for using the CD World App!",
	                    		
	                    "cdworldapp@gmail.com",   
	                    "gazh1987@gmail.com");   
	            } 
	            catch (Exception e) 
	            {   
	                Log.e("SendMail", e.getMessage(), e);   
	            }
				return null; 
	        }
	    }.execute();
	    
	    //REFERENCE COMPLETE
	    
	    Intent intent = new Intent (Checkout.this, FinalScreen.class);
		startActivity(intent);
	}
}
