package com.example.beautio;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Cart")
public class Cart extends ParseObject{
	
	public Cart(){
		
	}
	
	public ParseObject getUser(){
		return getParseObject("userid");
	}
	
	public void setUser(ParseObject userid){
		put("userid",userid);
	}
	public Product getProduct(){
		return (Product) getParseObject("productid");
	}
	
	public void setProduct(ParseObject productid){
		put("productid",productid);
	}
	
	public double getPrice(){
		return getDouble("productprice");
	}
	
	public void setPrice(double price){
		put("productprice",price);
	}
	
	public int getQuantity(){
		return getInt("productquantity");
	}
	
	public void setQuantity(int quantity){
		put("productquantity",quantity);
	}
	
	
	public boolean getSaveForLater(){
		return getBoolean("saveforlater");
	}
	
	public void setSaveForLater(boolean saveforlater){
		put("saveforlater",saveforlater);
	}
	
	
	
}