package com.example.beautio;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class CartFragment extends Fragment {
	
	private LinearLayout linearlayout1;
	
	Button btndelete;
	
	ParseImageView imgView;
	SharedPreferences sharedpreferences;
	CartProductAdapter adapter;
	ProgressDialog progressdialog;
	int totalcount=0;
	Double totalcartprice=0.0;

	public void CartFragment(){
		
	}
	@Override 
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
		
		
		 final View fragview = inflater.inflate(R.layout.usercartfragment,container,false);
		new LoadCartData().execute();
		return fragview;
		
	}
	private class LoadCartData extends AsyncTask<Void, Void, Void>{
	
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
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 linearlayout1 = (LinearLayout) getView().findViewById(R.id.cartlinearlayout1);
				
				final TextView lblcarttotal = (TextView) getView().findViewById(R.id.lblcarttotal);
				final TextView lblcartprice = (TextView) getView().findViewById(R.id.lblcartprice);
				final TextView lblcart = (TextView) getView().findViewById(R.id.lblcart);
				
				final Button btncheckout = (Button) getView().findViewById(R.id.btncheckout);
			final Context context;
			
			ParseQuery<Cart> query = ParseQuery.getQuery("Cart");
			query.whereEqualTo("userid", ParseUser.getCurrentUser());
			query.findInBackground(new FindCallback<Cart>(){

				@Override
				public void done(List<Cart> objects, ParseException e) {
					// TODO Auto-generated method stub
					if(e ==null){
						if(objects.size() == 0){
							FragmentManager fragmanager = getFragmentManager();
							Fragment frag = new HomeFragment();
							FragmentTransaction fragtrans = fragmanager.beginTransaction();
							fragtrans.replace(R.id.content_frame,frag).commit();
							Toast.makeText((Activity) getView().getContext() , "Your Shopping Cart is empty.", Toast.LENGTH_LONG ).show();
							
							
						}else{
								
							int i=0;
							adapter = new CartProductAdapter(getView().getContext(),objects);
							for(Cart c:objects){
								View v = adapter.getView(i, null, null);
								
				        		linearlayout1.addView(v);
				        		
				        		totalcount = totalcount + (int) c.getQuantity();
				        		totalcartprice = totalcartprice + (c.getQuantity()*c.getPrice()); 
				        		i++;
							}
							lblcart.setText("Cart SubTotal ");
							lblcarttotal.setText("("+Integer.toString(totalcount)+" items) :");
							lblcartprice.setText("$ "+Double.toString(totalcartprice));
							btncheckout.setVisibility(View.VISIBLE);
							
							
						}
						
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
	public void updatevalues(){
		
	}
	

}
