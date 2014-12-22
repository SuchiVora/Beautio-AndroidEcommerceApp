package com.example.beautio;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Brand")
public class Brand extends ParseObject{
	
	public Brand(){
		
	}
	public String getName(){
		return getString("name");
	}
	
	public void setName(String name){
		put("name",name);
	}
	
	public String getDescription(){
		return getString("description");
	}
	
	public void setDescription(String description){
		put("description",description);
	}
	public ParseFile getImage(){
		return getParseFile("logo");
	}
	
	public void setImage(ParseFile image){
		put("logo",image);
	}
	

}
