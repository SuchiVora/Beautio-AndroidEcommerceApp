package com.example.beautio;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProductByCategoryFragment extends Fragment {
	
	ProgressDialog progressdialog;
	String category;
	LinearLayout categorylinearlayout;
	ListView listview;
	

	public ProductByCategoryFragment(){
		
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View fragview = inflater.inflate(R.layout.productbycategoryfragment,container,false);
		
		category  = getArguments().getString("category");
		
		new LoadCategoryData().execute(category);
		return fragview;
	}
	
	private class LoadCategoryData extends AsyncTask<String, Void, Void>{
		
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
			
			ParseQuery<ParseObject> categoryquery = ParseQuery.getQuery("Category");
			categoryquery.whereEqualTo("name", value);
			ParseQuery<ParseObject> productquery = ParseQuery.getQuery("Product");
			productquery.whereMatchesKeyInQuery("categoryid", "objectId", categoryquery);
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
