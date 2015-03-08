package gabriel.luoyer.promonkey.main;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gabriel.luoyer.promonkey.MainActivity;
import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.base.BaseActivity;
import gabriel.luoyer.promonkey.bean.book;
import gabriel.luoyer.promonkey.main.HomeFragment.queryUser_Book;
import gabriel.luoyer.promonkey.utils.ModelServer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	
	EditText et_Num, et_Pwd;
	private Button mdenglu,mzhuce;// 
	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒 
	public JSONObject json ;
	 CustomProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initViews();
		initEvents();
		
		mdenglu = (Button) findViewById(R.id.login_btn_login);
		mdenglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
		
/*		mzhuce = (Button) findViewById(R.id.login_register);
		mzhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(LoginActivity.this,
						IndexActivity.class));
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

				//overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
			}
		});	*/	
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		this.et_Num = (EditText) this.findViewById(R.id.login_et_account);
		this.et_Pwd = (EditText) this.findViewById(R.id.login_et_pwd);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void login(){
		String name = et_Num.getText().toString();
		String password = et_Pwd.getText().toString();

		if (TextUtils.isEmpty(name)) {
			showCustomToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			showCustomToast(R.string.toast_error_password_null);
			return;
		}
		//开始进行登录 
		fullData();
		 progress = new CustomProgressDialog(LoginActivity.this,"正在登录中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		/**
		new Handler().postDelayed(new Runnable(){    
	          
            @Override   
            public void run() {    
            	progress.dismiss();
				startActivity(new Intent(LoginActivity.this,MainActivity.class));
        		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        		finish();    
            }    
                   
           }, SPLASH_DISPLAY_LENGHT);   
           */
		
	}
	
	private void fullData() {
		Thread androidUser_Login = new Thread(new AndroidUser_Login());
		androidUser_Login.start();
	}
	
	class AndroidUser_Login implements Runnable {
		
		@Override
		public void run() {
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   params.add(new BasicNameValuePair("U_studentID",et_Num.getText().toString()));
			   params.add(new BasicNameValuePair("U_password", et_Pwd.getText().toString()));
			   ModelServer modelHttp =new ModelServer();
			   Map<Boolean,String> result = modelHttp.Model_server(params, "AndroidUser_Login");
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
            		JSONObject jsonobject =json.getJSONObject("u");
					 Editor sharedata = getSharedPreferences("user", 0).edit();  
		                sharedata.putString("U_NO",jsonobject.getString("u_NO"));  
		                sharedata.putString("U_Name",jsonobject.getString("u_Name"));  
		                sharedata.putString("U_studentID",jsonobject.getString("u_studentID"));  
		                sharedata.commit();
		                Log.i("12345","Android 用户名 : "+jsonobject.getString("u_NO"));
		                progress.dismiss();
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
		        		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		        		finish();   
		        		showCustomToast("登录成功");
            			
				} catch (JSONException e) {
					e.printStackTrace();
					 progress.dismiss();
         			showCustomToast("账号/密码错误");
				}
                break;
            
            }
            
        }
    };
}
