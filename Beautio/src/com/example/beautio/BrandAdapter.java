
package com.example.beautio;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BrandAdapter extends BaseAdapter {
	
	
	LayoutInflater inflater;
	private Context context;
	
	LinearLayout imagecell;
	List<Brand> data;
	
	
	protected int position1;
	Brand p;
	protected String brandid;
	
	public BrandAdapter(Context context, List<Brand> objects){
	
     this.context = context;
     inflater =  LayoutInflater.from(context);
        this.data = objects;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView( int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = view;
		if(row == null){
			row = inflater.inflate(R.layout.imagecellbrand, null);
		
			final TextView txtbrandname = (TextView) row.findViewById(R.id.brandname);
			ParseImageView imgView = (ParseImageView) row.findViewById(R.id.brandimage);

			p = data.get(position);
			
			ParseFile image = (ParseFile) p.getImage();
			imgView.setParseFile(image);
			imgView.loadInBackground(new GetDataCallback(){

				@Override
				public void done(byte[] data, ParseException e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			//txtproductid.setText(p.getObjectId());
			txtbrandname.setText(p.getName());
			
			imgView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
				/*	ParseQuery<ParseObject> brandquery = ParseQuery.getQuery("Brand");
					brandquery.whereEqualTo("name", txtbrandname.getText().toString());
					brandquery.findInBackground(new FindCallback<ParseObject>(){

						@Override
						public void done(List<ParseObject> objects,
								ParseException e) {
							// TODO Auto-generated method stub
							
							if(e == null){
								for(ParseObject o: objects){
									brandid = o.getObjectId();
								}
							}
						}
						
						
					});*/

					
					Bundle args = new Bundle();
					FragmentManager fragmanager = ((Activity) context).getFragmentManager();
					Fragment prodbybrand = new ProductByBrandFragment();
					FragmentTransaction fragtrans = fragmanager.beginTransaction();
					args.putString("brandname",txtbrandname.getText().toString());
					prodbybrand.setArguments(args);
					fragtrans.replace(R.id.content_frame,prodbybrand).addToBackStack(null).commit();
					
				}
				
			});
			
			
		}
		
		
						
		return row;
	}
	
	
	

}
