package com.example.beautio;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	 int counter=0;
	FragmentManager fragmanager = getFragmentManager();
	private HorizontalScrollView h1;
	private LinearLayout scrollLayout1,scrollLayout2,scrollLayout3,imagelayout;
	private int[] images = {R.drawable.caudalie_divineoil_900x900,R.drawable.soak_handmaid_aquae_900x900,R.drawable.tocca_cremademano_2oz_cleopatra_900x900_1};
	String productname;	
	
	GridView gridview;
	ProductDetailsAdapter adapter;
	View fragview ;
	private List<Product> imagearraylist = null;
	
	public HomeFragment(){
		
	}
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			
		
		 fragview = inflater.inflate(R.layout.home_fragment,container,false);
	
            scrollLayout1= (LinearLayout) fragview.findViewById(R.id.homelinearlayout1);
    		scrollLayout2= (LinearLayout) fragview.findViewById(R.id._linearlayout2);
    		scrollLayout3= (LinearLayout) fragview.findViewById(R.id._linearlayout3); 
    		
    		
    		ParseQuery<Product> query = ParseQuery.getQuery("Product");
    		query.whereEqualTo("whatsnew", true);
    		query.findInBackground(new FindCallback<Product>(){
				public void done(List<Product> objects, ParseException e) {
					// TODO Auto-generated method stub
					
					if (objects != null){
						int i=0;
						adapter = new ProductDetailsAdapter(fragview.getContext(),objects);
							for(Product product:objects){
								View v = adapter.getView(i, null,null);
								
							
				        		scrollLayout1.addView(v);
				        	
				        		i++;
				        		
				        	
							}
						}
					
				}
				
    		});  
    		
    		ParseQuery<Product> query1 = ParseQuery.getQuery("Product");
    		query1.whereEqualTo("bestseller", true);
    		query1.findInBackground(new FindCallback<Product>(){
				public void done(List<Product> objects, ParseException e) {
					// TODO Auto-generated method stub
					
					if (objects != null){
						int i=0;
						adapter = new ProductDetailsAdapter(fragview.getContext(),objects);
							for(Product product:objects){
								
								View v1 = adapter.getView(i, null,null);
				        		scrollLayout2.addView(v1);
				        		
				        		i++;
				        		
				        	
							}
						}
					
				}
				
    		});  
    		
    	
    		ParseQuery<Brand> query2 = ParseQuery.getQuery("Brand");
    		query2.findInBackground(new FindCallback<Brand>(){
				public void done(List<Brand> objects, ParseException e) {
					// TODO Auto-generated method stub
					
					if (objects != null){
						int i=0;
						BrandAdapter brandadapter = new BrandAdapter(fragview.getContext(),objects);
							for(Brand b:objects){
								
								View v3 = brandadapter.getView(i, null,null);
				        		scrollLayout3.addView(v3);
				        		
				        		i++;
				        		
				        	
							}
						}
					
				}
				
    		});  
    		
    		
    	/*	for(int i=0;i<3;i++)
    		{
    			imagecell=inflater.inflate(R.layout.imagecell, null);
    			ImageView imgView = (ImageView) imagecell.findViewById(R.id._image);
    			imgView.setTag("Image#"+(i+1));
    			txtView = (TextView) imagecell.findViewById(R.id._productName);
    			txtproductcost = (TextView) imagecell.findViewById(R.id._productcost);
    			imgView.setImageResource(images[i]);
    			txtView.setText("Image#"+(i+1));
    			txtproductcost.setText("$"+(i+10));
    			scrollLayout1.addView(imagecell);
    			
    		
    		} 
    		for(int i1=0;i1<3;i1++)
    		{
    			imagecell=inflater.inflate(R.layout.imagecell, null);
    			final ImageView imgView = (ImageView) imagecell.findViewById(R.id._image);
    			imgView.setTag("Image#"+(i1+1));
    			txtView = (TextView) imagecell.findViewById(R.id._productName);
    			txtproductcost = (TextView) imagecell.findViewById(R.id._productcost);
    			imgView.setImageResource(images[i1]);
    			txtView.setText("Image#"+(i1+1));
    			txtproductcost.setText("$"+(i1+15));
    			scrollLayout2.addView(imagecell);
    			
    		
    		}
    		for(int i1=0;i1<3;i1++)
    		{
    			imagecell=inflater.inflate(R.layout.imagecell, null);
    			final ImageView imgView = (ImageView) imagecell.findViewById(R.id._image);
    			imgView.setTag("Image#"+(i1+1));
    			txtView = (TextView) imagecell.findViewById(R.id._productName);
    			txtproductcost = (TextView) imagecell.findViewById(R.id._productcost);
    			imgView.setImageResource(images[i1]);
    			txtView.setText("Image#"+(i1+1));
    			txtproductcost.setText("$"+(i1+20));
    			scrollLayout3.addView(imagecell);
    			
    		
    		} */
    		return fragview;
    		
    	}
	
	
	

	

}
