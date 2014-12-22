package com.example.beautio;


import com.parse.Parse;
import com.parse.ParseUser;

import android.widget.*;

import com.parse.ParseObject;

import android.view.*;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;

public class BaseActivity extends Activity implements OnItemClickListener {
	
	protected DrawerLayout drawerlayout;
	private ActionBarDrawerToggle drawerlistener;
    protected ListView drawerlist;
    private String[] app_menu;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ArrayAdapter adapter;
    String username;
    String []signin_app_menu;
    SharedPreferences sharedpreferences;
    Editor editor;
    
   
    FragmentManager fragmanager = getFragmentManager();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean connected = isConnected();
		if(connected == true){
			
		
		
		ParseObject.registerSubclass(User.class);
		ParseObject.registerSubclass(Product.class);
		ParseObject.registerSubclass(Cart.class);
		ParseObject.registerSubclass(Category.class);
		ParseObject.registerSubclass(Brand.class);
		
		Parse.initialize(this, "Wn3MAbSDHYboPqzOtcTrP8BKJ6VbjPISsMbZXTON", "vBNh50vJByNcHouTmPafIgyQAGDuVwiosEQt5Ktr");
		
		setContentView(R.layout.activity_main);	

		Fragment fragment = new HomeFragment();
		fragmanager.beginTransaction().add(R.id.content_frame, fragment).commit();
		
		/*final int actionbartitle = getResources().getIdentifier("action_bar_title", "id", "android");
		findViewById(actionbartitle).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		
		});*/
		
		mTitle = mDrawerTitle = getTitle();
		drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    drawerlist = (ListView) findViewById(R.id.left_drawer);
	    app_menu = getResources().getStringArray(R.array.app_menu);
		signin_app_menu = getResources().getStringArray(R.array.app_menu);
		
		drawerlistener = new ActionBarDrawerToggle(this, drawerlayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close ){
	    	 @Override
	    	 public void onDrawerClosed(View drawerView){
	    		 getActionBar().setTitle(mTitle);
	    		// Toast.makeText(BaseActivity.this, "Drawer is Closed", Toast.LENGTH_SHORT).show();
	    	 }
	    	 @Override
	    	 public void onDrawerOpened(View drawerView){
	    		 getActionBar().setTitle(mDrawerTitle);
	    		 //Toast.makeText(BaseActivity.this, "Drawer is Opened", Toast.LENGTH_SHORT).show();
	    	 }
	     };
		
		ParseUser user = ParseUser.getCurrentUser();
		
		if(user != null){
			username = user.getString("name");
			if(username == null)
			{
				
			}
			else{
				signin_app_menu[0]="Not "+username+"? Sign Out";
				
				
				adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,signin_app_menu);
			}
			
			
		}else{
			 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,app_menu);
			
		}
		
	    drawerlist.setAdapter(adapter);
	    drawerlist.setOnItemClickListener(this);
	    
	     drawerlayout.setDrawerListener(drawerlistener);
	    
	
	//  getActionBar().setDisplayShowTitleEnabled(true);
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	  getActionBar().setHomeButtonEnabled(true);
		}
		else{
			Toast.makeText(BaseActivity.this, "Bummer no network connection. ", Toast.LENGTH_SHORT).show();
		}
	  //  
	   
	}  
	
	public boolean isConnected(){
		
		ConnectivityManager cmanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = cmanager.getActiveNetworkInfo();
		if(networkinfo != null && networkinfo.isAvailable() == true && networkinfo.isConnected() == true ){
			return true;
		}
		
		return false;
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	   // drawerlistener.onConfigurationChanged(newConfig);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		//drawerlistener.syncState();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		if(drawerlistener.onOptionsItemSelected(item))
		{
			return true;
		}
		int id = item.getItemId();
		switch(id){
		case R.id.action_cart:
			CartFragment cartfrag = new CartFragment();
			//cartfrag.updatevalues();
			fragmanager.beginTransaction().replace(R.id.content_frame, cartfrag).addToBackStack(null).commit();
			break;
			
	//	case R.id.home:
		//	NavUtils.navigateUpFromSameTask(this);
		//	HomeFragment homefrag = new HomeFragment();
		//	fragmanager.beginTransaction().replace(R.id.content_frame, homefrag).commit();
		//	return true;
			
			
		}
	
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		
	   // Toast.makeText(this, app_menu[position]+" was selected", Toast.LENGTH_LONG ).show();
	   selectItem(position);
	}
	
	
	
/*	public void productdetails(View v, String productname){
		Bundle args = new Bundle();
		Fragment productdetail = new ProductDetailFragment();
		FragmentTransaction fragtrans = fragmanager.beginTransaction();
		args.putString("productname", productname);
		productdetail.setArguments(args);
		fragtrans.replace(R.id.content_frame,productdetail).addToBackStack(null).commit();
		
	
	}*/
	
	public void signup(View v){
		Fragment signup = new UserSignUpFragment();
		FragmentTransaction fragtrans = fragmanager.beginTransaction();
		
		fragtrans.replace(R.id.content_frame,signup).addToBackStack(null).commit(); 
		
	}
	
/*public void addtocart(View v){
	Fragment cartfragment = new CartFragment();
	FragmentTransaction fragtrans = fragmanager.beginTransaction();
	fragtrans.replace(R.id.content_frame,cartfragment).commit(); 
		
	}*/
    
	public void selectItem(int position)
     {
    	 Fragment fragment = null;
    	 Bundle args = new Bundle();
    	 drawerlist.setItemChecked(position, true);
    	 switch(position){
    	 case 0:
    		 String value = (String) drawerlist.getItemAtPosition(position);
    		 if( value.equals(app_menu[0].toString())){
    			 fragment = new UserLogInFragment();
    				
    		 }else
    		 {
    			 ParseUser user = ParseUser.getCurrentUser();
    			 if (user != null){
    				 ParseUser.logOut();
    				 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,app_menu);
    				 drawerlist.setAdapter(adapter);
    				 drawerlayout.closeDrawers();
    				 Toast.makeText(this, "You are signed out", Toast.LENGTH_SHORT ).show();
    				 fragment = new HomeFragment();
    				 
    			 }
    			 
    			 
    		 }
    		 break;
    		
    		// fragment = new UserLogInFragment();   		 
    	 
    	case 1:
    		
    		args.putString("category", "Bath and Body");
    		 fragment = new ProductByCategoryFragment();
    		 fragment.setArguments(args);
    		 break;
    		 
    	case 2:
    		 
    		args.putString("category", "Fragrances");
    		 fragment = new ProductByCategoryFragment();
    		 fragment.setArguments(args);
    		 break;
    		 
    	 case 3:
    		 
     		args.putString("category", "Hair");
     		 fragment = new ProductByCategoryFragment();
     		 fragment.setArguments(args);
     		 break;
    	 case 4:
     		args.putString("category", "Makeup");
     		 fragment = new ProductByCategoryFragment();
     		 fragment.setArguments(args);
     		 break;
    	 case 5:
     		args.putString("category", "Nails");
     		 fragment = new ProductByCategoryFragment();
     		 fragment.setArguments(args);
     		 break;
    	 case 6:
     		args.putString("category", "Skincare");
     		 fragment = new ProductByCategoryFragment();
     		 fragment.setArguments(args);
     		 break;
    	 }
    	 
    		 
    	
    	fragmanager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    	 drawerlayout.closeDrawers();
 
    	 
     }
	

}
