package gabriel.luoyer.promonkey.dialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import gabriel.luoyer.promonkey.MainActivity;
import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.base.BaseActivity;
import gabriel.luoyer.promonkey.main.LoginActivity;
import gabriel.luoyer.promonkey.main.ThirdLibsFragment;
import gabriel.luoyer.promonkey.utils.ModelServer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TianKongDialog extends DialogFragment{
	private static String U_studentID = "U_studentID";
	public static int update_result =0;//用作存储是否修改成功的结果
	private EditText dialog_edit;
	public JSONObject json ;
	public static TianKongDialog fragment_QuanJu;
	public static FragmentActivity fragmentActivity_Now;
	
	public static TianKongDialog newInstance(String u_studentID,FragmentActivity fragmentActivity){
		//重置修改结果状态 
		update_result=0;
		U_studentID=u_studentID;
		//创建一个新的带有参数的Fragment实例
		TianKongDialog fragment = new TianKongDialog();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		fragment_QuanJu=fragment;
		fragmentActivity_Now=fragmentActivity;
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater factory = (LayoutInflater)  getActivity().getSystemService( getActivity().LAYOUT_INFLATER_SERVICE);
		final View customViewnick = factory.inflate(R.layout.alert_dialog_nick, null);
		dialog_edit = (EditText)customViewnick.findViewById(R.id.dialog_edit);
		final Dialog ad2 = new Dialog(getActivity(), R.style.Theme_CustomDialog2);
		ad2.setContentView(customViewnick);
		ad2.setCancelable(false);//有可能你调不出这个方法，可以通过ad.setOnKeyListener()方法来实现屏蔽back键
		ad2.setCanceledOnTouchOutside(false);//当点击除对话框外屏幕其他区域时对话框不消失，默认是消失的

		Button buttonnickyes = (Button)customViewnick.findViewById(R.id.yes);
		buttonnickyes.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Thread androidUser_updatePassword = new Thread(new AndroidUser_updatePassword());
				androidUser_updatePassword.start();
				update_result=1;
				ad2.dismiss();
			}
		});
		Button buttonnickno = (Button)customViewnick.findViewById(R.id.no);
		buttonnickno.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ad2.dismiss();
			}
		});
		return ad2;			
	}
	
class AndroidUser_updatePassword implements Runnable {
		
		@Override
		public void run() {
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
			   params.add(new BasicNameValuePair("U_studentID",U_studentID));
			   params.add(new BasicNameValuePair("U_password",dialog_edit.getText().toString()));
			   ModelServer modelHttp =new ModelServer();
			   Map<Boolean,String> result = modelHttp.Model_server(params, "AndroidUser_updatePassword");
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
	
Toast mToast;
	//Handler
    Handler queryMaxhandler = new Handler()
    {//showCustomerToast(R.drawable.btn_check_buttonless_on,"加载成功");
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
            case 0:
            	ThirdLibsFragment thirdLibsFragment =new ThirdLibsFragment();
            	thirdLibsFragment.showCustomerToast("修改成功",fragmentActivity_Now);
            		
            	break;
            
            }
            
        }
    };

	

}