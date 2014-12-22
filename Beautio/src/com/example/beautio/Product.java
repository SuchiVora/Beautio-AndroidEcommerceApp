package com.example.beautio;

import com.parse.*;

@ParseClassName("Product")
public class Product extends ParseObject{
	public Product(){
		
	}
	
	public ParseObject getBrand(){
		return getParseObject("brandid");
	}
	
	public void setBrand(ParseObject brandid){
		put("brandid",brandid);
	}
	
	public ParseObject getCategory(){
		return getParseObject("categoryid");
	}
	
	public void setCategory(ParseObject categoryid){
		put("categoryid",categoryid);
	}
	
	public ParseObject getOption(){
		return getParseObject("optionid");
	}
	
	public void setOption(ParseObject optionid){
		put("optionid",optionid);
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
	
	public double getPrice(){
		return getDouble("price");
	}
	
	public void setPrice(double price){
		put("price",price);
	}
	
	public String getHowToUse(){
		return getString("howtouse");
	}
	
	public void setHowToUse(String howtouse){
		put("howtouse",howtouse);
	}
	
	public int getStock(){
		return (int) getInt("stock");
	}
	
	public void setStock(int stock){
		put("stock",stock);
	}
	
	public void setSize(String size){
		put("size",size);
	}
	
	public String getSize(){
		return getString("size");
	}
	
	public ParseFile getImage(){
		return getParseFile("image");
	}
	
	public void setImage(ParseFile image){
		put("image",image);
	}
	

	

}
