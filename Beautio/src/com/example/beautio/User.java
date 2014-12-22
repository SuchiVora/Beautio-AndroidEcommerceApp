package com.example.beautio;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User  extends ParseUser{
	public User(){
		
	}
	public String getName(){
		return getString("name");
	}
	
	public void setName(String name){
		put("name",name);
		
	}
	
	
	public String getUsername(){
		return getString("username");
	}
	public void setUsername(String username){
		put("username",username);
		
	}
	
	public String getPassword(){
		return getString("password");
	}
	
	public void setPassword(String password){
		put("password",password);
	}
	public String getEmail(){
		return getString("email");
	}
	public void setEmail(String email){
		put("email",email);
	}
	public boolean isemailVerified()
	{
		return getBoolean("emailVerified");
	}
	public void setemailVerified(boolean emailVerified)
	{
		put("emailVerified", emailVerified);
	}
}
