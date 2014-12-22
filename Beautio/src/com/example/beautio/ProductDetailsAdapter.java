package com.example.beautio;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
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

public class ProductDetailsAdapter extends BaseAdapter {
	
	
	LayoutInflater inflater;
	private Context context;
	
	LinearLayout imagecell;
	List<Product> data;
	
	ArrayList<Product> imagelist = null;
	protected int position1;
	Product p;
	
	public ProductDetailsAdapter(Context context, List<Product> objects){
	
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
		position1 = position;
		
		String productname;
		if(row == null){
			row = inflater.inflate(R.layout.imagecell, null);
			
			final TextView txtproductid = (TextView) row.findViewById(R.id._productid);
			TextView txtView = (TextView) row.findViewById(R.id._productName);
			TextView txtproductcost = (TextView) row.findViewById(R.id._productcost);
			ParseImageView imgView = (ParseImageView) row.findViewById(R.id._image);

			p = data.get(position);
			
			ParseFile image = (ParseFile) p.getImage();
			imgView.setParseFile(image);
			imgView.loadInBackground(new GetDataCallback(){

				@Override
				public void done(byte[] data, ParseException e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			txtproductid.setText(p.getObjectId());
			txtView.setText(p.getName());
			txtproductcost.setText("$ "+Double.toString(p.getPrice()));
			imgView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle args = new Bundle();
					FragmentManager fragmanager = ((Activity) context).getFragmentManager();
					Fragment productdetail = new ProductDetailFragment();
					FragmentTransaction fragtrans = fragmanager.beginTransaction();
					args.putString("productid",txtproductid.getText().toString());
					productdetail.setArguments(args);
					fragtrans.replace(R.id.content_frame,productdetail).addToBackStack(null).commit();
					
				}
				
			});
			
			
		}
		
		
						
		return row;
	}
	
	
	

}
