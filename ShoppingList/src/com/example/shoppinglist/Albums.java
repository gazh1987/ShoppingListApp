package com.example.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

public class Albums implements Parcelable
{
	private String artist;
	private String album;
	private float price;

	public Albums(String art, String alb, float p)
	{
		this.artist = art;
		this.album = alb;
		this.price = p;
	}
	
	public static final Parcelable.Creator<Albums> CREATOR = new Parcelable.Creator<Albums>() 
	{
		public Albums createFromParcel(Parcel in) 
		{
			return new Albums(in);
		}
		
		public Albums[] newArray(int size) 
		{
			return new Albums[size];
		}
	};

	private Albums(Parcel in) 
	{
		this.artist = in.readString();
		this.album = in.readString();
		this.price = in.readFloat();
	}
	
	//Getters
	public String GetArtist()
	{
		return artist;
	}
	public String GetAlbum()
	{
		return album;
	}
	public float GetPrice()
	{
		return price;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(artist);
		dest.writeString(album);
		dest.writeFloat(price);
	}
}
