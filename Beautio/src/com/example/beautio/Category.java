package com.example.beautio;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Catgeory")
public class Category extends ParseObject {
	public Category(){
		
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
	
}
