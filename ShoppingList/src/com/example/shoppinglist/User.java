package com.example.shoppinglist;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable
{
	private String name;
	private float spendingMoney;
	private String emailAddress;
	
	public User(String name, String spendingMoney, String emailAddress)
	{
		this.name = name;
		this.spendingMoney = Float.parseFloat(spendingMoney);
		this.emailAddress = emailAddress;
	}
	
	//REFERENCE: This code is taken from 
	//http://blog.denevell.org/android-parcelable.html
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() 
    {
		public User createFromParcel(Parcel in) 
		{
			return new User(in);
		}
		
		public User[] newArray(int size) 
		{
			return new User[size];
		}
    };

    private User(Parcel in) 
    {
        this.name = in.readString();
        this.spendingMoney = in.readFloat();
        this.emailAddress = in.readString();
    }
    
    //REFERENCE complete
	
	public String GetName()
	{
		return name;
	}
	
	public float GetSpendingMoney()
	{
		return spendingMoney;
	}
	
	public String GetEmailAddress()
	{
		return emailAddress;
	}
	
	public void DebitSpendingMoney(float sale)
	{
		spendingMoney -= sale;
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}
	
	//REFERENCE: This code is taken from 
	//http://blog.denevell.org/android-parcelable.html
	
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(name);
		dest.writeFloat(spendingMoney);
		dest.writeString(emailAddress);
		
	}
	
	//REFERENCE complete
}
