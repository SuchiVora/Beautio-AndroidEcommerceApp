package com.example.beautio;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {

	public static final String MyPreferences = "MyPrefs" ;
	
	public SharedPreference(){
		
	}
	
	public void save(Context context, String text){
		SharedPreferences products;
		Editor editor;
		products = context.getSharedPreferences(MyPreferences, 0);
		editor = products.edit();
		editor.putString("productid",text);
		editor.commit();
	}
	
	public String getValue(Context context){
		
		SharedPreferences products;
		String text;
		products = context.getSharedPreferences(MyPreferences, 0);
		text = products.getString("productid", null);
		return text;
		
	}
	
	public void clearSharedPreferences(Context context){
		
		SharedPreferences products;
		Editor editor;
		products = context.getSharedPreferences(MyPreferences, 0);
		editor = products.edit();
		editor.clear();
		editor.commit();
		
	}
	
	public void removeValue(Context context){
		
		SharedPreferences products;
		Editor editor;
		products = context.getSharedPreferences(MyPreferences, 0);
		editor = products.edit();
		editor.remove("productid");
		editor.commit();
		
	}
	
	

}
