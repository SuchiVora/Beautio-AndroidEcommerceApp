package com.example.beautio;

import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class UserLogInFragment extends Fragment {
	
	
	String username;
	String password;
	String []signin_app_menu;
	FragmentManager fragmanager;
	FragmentTransaction fragtrans;
	public UserLogInFragment()
	{
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
		View fragview = inflater.inflate(R.layout.userloginfragment,container,false);
		
		final Button butsignin =  (Button)fragview.findViewById(R.id.butsignin);
		final Button butsingup = (Button) fragview.findViewById(R.id.butregister);
		final EditText txtusername = (EditText) fragview.findViewById(R.id.etxtemail);
		final EditText txtpassword = (EditText) fragview.findViewById(R.id.etxtpassword);
		
		signin_app_menu = getResources().getStringArray(R.array.app_menu);
		
		
		butsignin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//onLoginButtonClicked();
				username = (String) txtusername.getText().toString();
				password = (String) txtpassword.getText().toString();
				
				ParseUser.logInInBackground(username,password,new LogInCallback(){
					public void done(ParseUser user, ParseException e){
						if(user != null){
							Toast.makeText(getActivity(), "Successfully Logged in", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getActivity(), BaseActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							
							
						}else{
							Toast.makeText(getActivity(), "Your Account doesn not exist. Please register", Toast.LENGTH_SHORT).show();
						}
					}
				});
				
				
			}
		} );
	    

		return fragview;
		
	}
	protected void onLoginButtonClicked() {
		// TODO Auto-generated method stub
		
		
	}

}
