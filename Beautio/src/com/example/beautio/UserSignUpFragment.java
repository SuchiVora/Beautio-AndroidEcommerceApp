package com.example.beautio;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserSignUpFragment extends Fragment{
	String username;
	String password;
	String name;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View fragview = inflater.inflate(R.layout.usersignupfragment,container,false);
		
		final Button btnsignup =  (Button)fragview.findViewById(R.id.btnsignup);
		final EditText txtname = (EditText) fragview.findViewById(R.id.txtnamesp);
		final EditText txtusername = (EditText) fragview.findViewById(R.id.txtemailsp);
		final EditText txtpassword = (EditText) fragview.findViewById(R.id.txtpasswordsp);
		btnsignup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//onLoginButtonClicked();
				username = (String) txtusername.getText().toString();
				password = (String) txtpassword.getText().toString();
				name = (String) txtname.getText().toString();
				if(txtusername.equals("") && txtpassword.equals("")){
					Toast.makeText(getActivity(), "Please enter the email and password", Toast.LENGTH_SHORT).show();

				}else{
		
				ParseUser user = new ParseUser();
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(username);
				user.put("name", name);
				//user.put("name", name);
				
				user.signUpInBackground(new SignUpCallback(){

					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if(e==null){
							Toast.makeText(getActivity(), "Account Scuccessfully created", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
							
						}
						
					}
				
				});
				}
				
			}
		});
		
		
		
		return fragview;
		
	}
}
