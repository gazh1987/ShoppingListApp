package com.example.shoppinglist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Checkout extends Activity
{
	ArrayList<Albums> shoppingCart;
	TextView artist, album, price, preVat, vat, totalPrice;
	float tempTotal, tempPrice, tempVat, vatCalc = (float) 0.21;
	
	
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.checkout_screen);
		
		Bundle bundle = getIntent().getExtras();
        shoppingCart = bundle.getParcelableArrayList("shoppingCart");
		
        artist = (TextView)findViewById(R.id.artist);
        album = (TextView)findViewById(R.id.album);
        price = (TextView)findViewById(R.id.price);
        
        for (int i = 0; i < shoppingCart.size(); i ++)
        {
        	artist.append("\n\t\t" + shoppingCart.get(i).GetArtist());
        	album.append("\n\t\t" + shoppingCart.get(i).GetAlbum());
        	price.append("\n\t\t�" + shoppingCart.get(i).GetPrice());
        }
        
        for (int i = 0; i < shoppingCart.size(); i++)
        {
        	tempTotal += shoppingCart.get(i).GetPrice();
        }
        
        tempVat = tempTotal * vatCalc;
        vat = (TextView)findViewById(R.id.vat);
        vat.setText("\t\t�" + String.format("%.2f", tempVat));
        
        preVat = (TextView)findViewById(R.id.preVat);
        tempTotal = tempTotal - tempVat;
        preVat.setText("\t\t�" + String.format("%.2f", tempTotal));
        
        totalPrice = (TextView)findViewById(R.id.totalPrice);
        tempPrice = tempTotal + tempVat;
        totalPrice.setText("\t\t�" + String.format("%.2f", tempPrice));
	}
}