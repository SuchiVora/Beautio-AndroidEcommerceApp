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
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class CartProductAdapter extends BaseAdapter{
	
	private Context context;
	LayoutInflater inflater;
	LinearLayout imagecell;
	List<Cart> data;
	SharedPreferences sharedpreferences ;
	Editor editor;
	Cart c;
	
	public CartProductAdapter(Context context, List<Cart> objects){
		
		this.context = context;
		this.data = objects;
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View cartcell = convertView;
	
		if(cartcell == null){
			
			cartcell=inflater.inflate(R.layout.cartcell, null);
			final ParseImageView imgView = (ParseImageView) cartcell.findViewById(R.id._image);
			final TextView txtproductname = (TextView) cartcell.findViewById(R.id.lblproductname);
			final TextView txtproductcost = (TextView) cartcell.findViewById(R.id.lblprice);
			final TextView txtproductstock = (TextView) cartcell.findViewById(R.id.lblstock);
			final NumberPicker numberpicker = (NumberPicker) cartcell.findViewById(R.id.numpicker1);
			Button btndelete = (Button) cartcell.findViewById(R.id.btndelete);
			numberpicker.setMaxValue(10);
			numberpicker.setMinValue(1);
			
			
			c = data.get(position);
			Product p =  c.getProduct();
			try {
				
				txtproductname.setText(p.fetchIfNeeded().getString("name"));
				txtproductcost.setText(Double.toString(p.fetchIfNeeded().getDouble("price")));
				int stock = p.fetchIfNeeded().getInt("stock");
				if(stock >0){
					txtproductstock.setText("In Stock");
				}
				else{
					txtproductstock.setText("Out of Stock");
				}
				ParseFile image = (ParseFile) p.fetchIfNeeded().getParseFile("image");
				imgView.setParseFile(image);
				imgView.loadInBackground(new GetDataCallback(){
					@Override
					public void done(byte[] data, ParseException e) {
						// TODO Auto-generated method stub	
					}
					
				});	 
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			numberpicker.setValue(c.getQuantity());
			
			
		btndelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				String prodname = txtproductname.getText().toString();
				ParseQuery<ParseObject> queryproduct = ParseQuery.getQuery("Product");
				queryproduct.whereEqualTo("name",prodname);
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Cart");
				query.whereEqualTo("userid", ParseUser.getCurrentUser());
				query.whereMatchesKeyInQuery("productid", "objectId", queryproduct);
				query.findInBackground(new FindCallback<ParseObject>(){
					
					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						// TODO Auto-generated method stub
						for(ParseObject p : objects){
							p.deleteInBackground();
						}
						
						
					}
				});
					
					FragmentManager fragmanager = ((Activity) context).getFragmentManager();
					Fragment cartfragment = new CartFragment();
					FragmentTransaction fragtrans = fragmanager.beginTransaction();
					fragtrans.replace(R.id.content_frame,cartfragment).commit();
					
				}
				
			
		});
		numberpicker.setOnValueChangedListener(new OnValueChangeListener(){
			String productid = null;
			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					final int newVal) {
				// TODO Auto-generated method stub
				
				final String name = txtproductname.getText().toString();
				
				ParseQuery<ParseObject> queryproduct = ParseQuery.getQuery("Product");
				queryproduct.whereEqualTo("name",name);
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Cart");
				query.whereEqualTo("userid", ParseUser.getCurrentUser());
				query.whereMatchesKeyInQuery("productid", "objectId", queryproduct);
				query.findInBackground(new FindCallback<ParseObject>(){
					
					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						// TODO Auto-generated method stub
						
							for(ParseObject p : objects){
								p.put("productquantity", newVal);
								p.saveInBackground();
								numberpicker.setOnScrollListener(new OnScrollListener(){

									@Override
									public void onScrollStateChange(
											NumberPicker view, int scrollState) {
										// TODO Auto-generated method stub
										if(scrollState == 0){
											FragmentManager fragmanager = ((Activity) context).getFragmentManager();
											Fragment cartfragment = new CartFragment();
											FragmentTransaction fragtrans = fragmanager.beginTransaction();
											fragtrans.replace(R.id.content_frame,cartfragment).commit();
											
											
										}
										
									}
									
								});
								
								
							}
								
						
						
					}
					
				});
				
			
				
				
			}
			
		});

			//linearlayout1.addView(cartcell);
			
		}
		
		
		
		return cartcell;
	}

}
