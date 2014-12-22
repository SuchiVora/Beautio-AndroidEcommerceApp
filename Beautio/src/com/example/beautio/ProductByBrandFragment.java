package com.example.beautio;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.beautio.ProductByBrandFragment.LoadBrandData;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ProductByBrandFragment extends Fragment {


	ProgressDialog progressdialog;
	String brand;
	LinearLayout categorylinearlayout;
	ListView listview;
	

	public ProductByBrandFragment(){
		
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View fragview = inflater.inflate(R.layout.productbycategoryfragment,container,false);
		brand  = getArguments().getString("brandname");
		
		new LoadBrandData().execute(brand);
		
		
		return fragview;
	}
	
	public class LoadBrandData extends AsyncTask<String, Void, Void>{
		
		@Override
		protected void onPreExecute(){
			progressdialog = new ProgressDialog(getActivity(), 0);
			progressdialog.setMessage("Loading...");
			progressdialog.setIndeterminate(true);
			//progressdialog.show();
			
			
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			// categorylinearlayout = (LinearLayout) getView().findViewById(R.id.categorylinearlayout1);
			 listview = (ListView) getView().findViewById(R.id.categorylistview);
			final TextView lblcategory = (TextView) getView().findViewById(R.id.lblcategory1);
			final Context context;
			final String value = params[0];
			
			ParseQuery<ParseObject> brandquery = ParseQuery.getQuery("Brand");
			brandquery.whereEqualTo("name", value);
			ParseQuery<ParseObject> productquery = ParseQuery.getQuery("Product");
			productquery.whereMatchesKeyInQuery("brandid", "objectId", brandquery);
			productquery.findInBackground(new FindCallback<ParseObject>(){

				private ProductCategoryAdapter adapter;

				public void done(List<ParseObject> objects, ParseException e) {
					// TODO Auto-generated method stub
					if(e ==null){
							
								
							adapter = new ProductCategoryAdapter(getView().getContext(),objects);
							//int i=0;
							listview.setAdapter(adapter);
							lblcategory.setText(value);
						/*	for(ParseObject c:objects){
								View v = adapter.getView(i, null, null);
								lblcategory.setText(Integer.toString(i));
								
				        		//categorylinearlayout.addView(v);
				        		
				        		i++;
							}*/
							
							
						}
						
					}

				


				
			});
			listview.setOnItemClickListener(new OnItemClickListener(){

				private Activity context;

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					ParseObject p = (ParseObject) listview.getItemAtPosition(position);
					Bundle args = new Bundle();
					FragmentManager fragmanager = getFragmentManager();
					Fragment productdetail = new ProductDetailFragment();
					FragmentTransaction fragtrans = fragmanager.beginTransaction();
					args.putString("productid",p.getObjectId());
					productdetail.setArguments(args);
					fragtrans.replace(R.id.content_frame,productdetail).addToBackStack(null).commit();
					
				}
				
			});
			
			return null;
		}
		@Override
		protected void onPostExecute(Void args){
			progressdialog.dismiss();
		}
		
	}	
}