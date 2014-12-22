package com.example.beautio;

import java.util.List;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductCategoryAdapter extends BaseAdapter {
	
	LayoutInflater inflater;
	List<ParseObject> data;
	Context context;

	public ProductCategoryAdapter(Context context, List<ParseObject> objects) {
		// TODO Auto-generated constructor stub
		this.context = context;
		data = objects;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public ParseObject getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = null;
		ParseObject p;
		if(row == null){
			row = inflater.inflate(R.layout.listrow,null);
			
			ParseImageView imgView = (ParseImageView) row.findViewById(R.id.categoryimage);
			TextView lblproductname = (TextView) row.findViewById(R.id.lblcategoryproductname);
			TextView lblprice = (TextView) row.findViewById(R.id.lblcategoryprice);
			TextView lblstock = (TextView) row.findViewById(R.id.lblcatgeorystock);

			try {
			p =  data.get(position);
			
			ParseFile image = (ParseFile) p.fetchIfNeeded().getParseFile("image");
			imgView.setParseFile(image);
			imgView.loadInBackground(new GetDataCallback(){

				@Override
				public void done(byte[] data, ParseException e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			lblproductname.setText(p.fetchIfNeeded().getString("name"));
			lblprice.setText("$ "+ Double.toString(p.getDouble("price")));
			int stock;
			
				stock = p.fetchIfNeeded().getInt("stock");
			
			if(stock >0){
				lblstock.setText("In Stock");
			}
			else{
				lblstock.setText("Out of Stock");
			} 
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return row;
	}

}
