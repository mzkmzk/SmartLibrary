package gabriel.luoyer.promonkey.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.charon.pulltorefreshlistview.PullToRefreshListView;
import com.charon.pulltorefreshlistview.PullToRefreshListView.OnRefreshListener;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.base.BaseFragment;
import gabriel.luoyer.promonkey.bean.book;
import gabriel.luoyer.promonkey.utils.ModelServer;

public class WorkNotesFragment extends BaseFragment   {
	
	private PullToRefreshListView mPullToRefreshListView;
	private MyAdapter adapter;
	private ArrayList<book> data;
	public LayoutInflater mInflater;
	public JSONObject json ;
	private SharedPreferences sharedata;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater=inflater;
		View view = inflater.inflate(R.layout.main_frg_work_notes, container, false);
		findView(view);
		initView(view);
		fullData(view);
		return view;
	}

	private void findView(View view) {
		mPullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.mfw_s);
		sharedata = getActivity().getSharedPreferences("user", 0); 
	}

	private void initView(View view) {
		
		data = new ArrayList<book>();
		
		book b =new book();
		
		b.setB_name("书籍名称");
		b.setB_accessTime("还书时间");
		
		data.add(b);

		adapter = new MyAdapter();

		mPullToRefreshListView.setAdapter(adapter);

		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						
						SystemClock.sleep(1000);
						Thread updateShop = new Thread(new queryUser_Book());
						updateShop.start();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						adapter.notifyDataSetChanged();
						mPullToRefreshListView.onRefreshComplete();
					}
				}.execute();
			}
		});

		mPullToRefreshListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// If you call addHeaderView, the position will contains
						// the count of header view.
						// int realPosition = position
						// - mPullToRefreshListView.getHeaderViewsCount();
						// Toast.makeText(PullToRefreshActivity.this,
						// data.get(realPosition), Toast.LENGTH_SHORT).show();

						Toast.makeText(
								getActivity().getApplicationContext(),
								(String) mPullToRefreshListView.getAdapter()
										.getItem(position), Toast.LENGTH_SHORT)
								.show();

					}

				});

	}
	
	private void fullData(View view) {
		Thread updateShop = new Thread(new queryUser_Book());
		 updateShop.start();
	}
	
	class queryUser_Book implements Runnable {

		@Override
		public void run() {
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   //添加查询学生账号和书籍条件
			   params.add(new BasicNameValuePair("U_NO",sharedata.getString("U_NO", null)));
			   params.add(new BasicNameValuePair("checkReturn", "1"));
			   ModelServer modelHttp =new ModelServer();
			   Map<Boolean,String> result = modelHttp.Model_server(params, "AndroidVisitUser_Book");
			   try {
				  if(!result.isEmpty()){
				 json= new JSONObject(result.get(true));
				 Log.i("12345",result.get(true));
				//status =json.getString("status");
				//给Message传递消息
				 Message msg = queryMaxhandler.obtainMessage();
				 msg.what=0;
				 queryMaxhandler.sendMessage(msg);
				 }
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	//Handler
    Handler queryMaxhandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
            case 0:
            	try {
					JSONArray array= json.getJSONArray("lb");
					addbookFor :for(int i=0;i<array.length();i++){
	        			JSONObject jsonObject =array.getJSONObject(i);
	        		    book b =new book();
	        		    b.setB_NO(jsonObject.getString("b_NO"));
	        		    b.setB_address(jsonObject.getString("b_address"));
	        		    b.setB_name(jsonObject.getString("b_name"));
	        		    b.setB_press(jsonObject.getString("b_press"));
	        		    b.setB_accessTime(jsonObject.getString("b_returnBookTime"));
	        		    //检测之前有没有重复的书籍
	        		    for(int index=0;index<data.size();index++){
	        		    	if(b.getB_NO().equals(data.get(index).getB_NO())){
	        		    		continue addbookFor;
	        		    	}
	        		    }
	        		    data.add(b);
	        		    Log.i("12345","android 查找到得书籍 :" +jsonObject.getString("b_name"));
					}
					
					adapter = new MyAdapter();
					mPullToRefreshListView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
                break;
            
            }
            
        }
    };

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 ViewHolder holder = null;
             if (convertView == null) {                    
                 holder=new ViewHolder();                   
                 convertView = mInflater.inflate(R.layout.main_frg_home_lv, null);
                 holder.book_name = (TextView)convertView.findViewById(R.id.book_name);
                 holder.book_accessTime = (TextView)convertView.findViewById(R.id.book_accessTime);
                 convertView.setTag(holder);                    
             }else {                   
                 holder = (ViewHolder)convertView.getTag();
             }                           
             holder.book_name.setText(data.get(position).getB_name());
             holder.book_accessTime.setText(data.get(position).getB_accessTime());                
             return convertView;
		}
	}
	
	public final class ViewHolder{
        public TextView book_name;
        public TextView book_accessTime;
    }
}
