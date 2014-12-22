package com.example.beautio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailFragment extends Fragment {
	
	TextView txtbrandname, txtproductname, txtproductprice, txtproductsize, txtproducthowtouse,lblhowtouse,lblproductsize;
	Button btnaddtocart;
	ParseImageView imgView ;
	SharedPreferences sharedpreferences;
	String strproductid;
	ProgressDialog progressdialog;
	

	public ProductDetailFragment(){
		
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View fragview = inflater.inflate(R.layout.productdetailfragment,container,false);
		txtproductname = (TextView) fragview.findViewById(R.id.txtproductname);
		txtbrandname = (TextView) fragview.findViewById(R.id.txtbrandname);
		txtproductprice = (TextView) fragview.findViewById(R.id.txtproductprice);
		lblproductsize = (TextView) fragview.findViewById(R.id.txtsize);
		txtproductsize = (TextView) fragview.findViewById(R.id.txtproductsize);
		lblhowtouse = (TextView) fragview.findViewById(R.id.txthowtouse);
		txtproducthowtouse = (TextView) fragview.findViewById(R.id.txtproducthowtouse);
		imgView = (ParseImageView) fragview.findViewById(R.id.productimage1);
		btnaddtocart = (Button) fragview.findViewById(R.id.btnaddtocart);
		
		new LoadData().execute();
	   
		
		return fragview;
	}
	private class LoadData extends AsyncTask<Void, Void, Void>{
		@Override
		protected void onPreExecute(){
			progressdialog = new ProgressDialog(getActivity(), 0);
			progressdialog.setMessage("Loading...");
			progressdialog.setIndeterminate(true);
			progressdialog.show();
			
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			strproductid = getArguments().getString("productid");
			ParseQuery<Product> query = ParseQuery.getQuery("Product");
			query.include("Brand");
			query.getInBackground(strproductid,new GetCallback<Product>(){

				@Override
				public void done(final Product p, ParseException e) {
					// TODO Auto-generated method stub
					if (e==null){
						ParseFile image = (ParseFile) p.getImage();
						imgView.setParseFile(image);
						imgView.loadInBackground(new GetDataCallback(){

							@Override
							public void done(byte[] data, ParseException e) {
								// TODO Auto-generated method stub
								
							}
							
						});
						ParseObject brand = p.getBrand();
						try {
							txtbrandname.setText(brand.fetchIfNeeded().getString("name"));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						txtproductname.setText(p.getName());
						lblproductsize.setText("Size:");
						txtproductsize.setText(p.getSize());
						txtproductprice.setText("$ "+ Double.toString(p.getPrice()));
						lblhowtouse.setText("How To Use: ");
						txtproducthowtouse.setText(p.getHowToUse());
						btnaddtocart.setVisibility(View.VISIBLE);
						btnaddtocart.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								boolean signedin = checkuser();
								if (signedin == true){
									savetocart(p);
									
									FragmentManager fragmanager = getFragmentManager();
									
									CartFragment cartfrag = new CartFragment();
									//cartfrag.updatevalues();
									fragmanager.beginTransaction().replace(R.id.content_frame,cartfrag).commit();
								}
								else{
									Toast.makeText(getActivity(), "Please Sign in to add products to cart", Toast.LENGTH_SHORT ).show();
									FragmentManager fragmanager = getFragmentManager();
									Fragment frag = new UserLogInFragment();
									FragmentTransaction fragtrans = fragmanager.beginTransaction();
									fragtrans.replace(R.id.content_frame,frag).commit();
								}
								
							}
							
						});
						
					}
					
				}

				
				
			});
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void args){
			progressdialog.dismiss();
		}
		
	}
 	public boolean checkuser(){
		boolean signedin;
		ParseUser user = ParseUser.getCurrentUser();
		if(user == null)
		{
			signedin=false;
		}else{
			signedin = true;
		}
		return signedin;
		
	}
	public void savetocart(final ParseObject p){
		
	 
		ParseQuery<Cart> cartquery = ParseQuery.getQuery("Cart");
		cartquery.whereEqualTo("productid", p);
		cartquery.findInBackground(new FindCallback<Cart>(){

			@Override
			public void done(List<Cart> objects, ParseException e) {
				// TODO Auto-generated method stub
				int count;
				if(objects.size() >0){
					for(Cart c1 :objects){
						count = c1.getQuantity();
						count = count +1;
						c1.setQuantity(count);
						c1.saveInBackground();
					}
				
				}else{
					final Cart cart = new Cart();
					ParseQuery<Product> query = ParseQuery.getQuery("Product");
					//query.include("User");
					query.getInBackground(p.getObjectId(),new GetCallback<Product>(){

						@Override
						public void done(Product object, ParseException e) {
							// TODO Auto-generated method stub
							cart.setProduct(object);
							cart.setUser(ParseUser.getCurrentUser());
							cart.setPrice(object.getPrice());
							cart.setQuantity(1);
							cart.setSaveForLater(false);
							cart.saveInBackground();
							
							
						}
						
					});
					
				}
				
			}
			
		});
		
		
	}

}
