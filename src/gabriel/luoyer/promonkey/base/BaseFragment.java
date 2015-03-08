package gabriel.luoyer.promonkey.base;

import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.utils.Utils;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseFragment extends DialogFragment {
	private final String TAG = this.getClass().getSimpleName();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Utils.logh(TAG, " >>> onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.logh(TAG, " > onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Utils.logh(TAG, " - onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Utils.logh(TAG, " --- onDetach");
	}

	@Override
	public void onStart() {
		super.onStart();
		Utils.logh(TAG, " >> onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Utils.logh(TAG, " -- onStop");
	}
	
	/** 长时间显示Toast提示(来自String) **/
	public void showLongToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
	// 自定义Toast信息显示
	public void showCustomerToast(int icon, String message){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_customer, (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
		
		ImageView toastIcon = (ImageView) layout.findViewById(R.id.toastIcon);
		toastIcon.setBackgroundResource(icon);
		
		TextView toastMessage = (TextView) layout.findViewById(R.id.toastMessage);
		toastMessage.setText(message);
		Toast toast = new Toast(getActivity().getApplicationContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	
	
	Toast mToast;
	public void showCustomToast(final int resId) {
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast == null) {
					mToast = Toast.makeText(getActivity().getApplicationContext(), resId,
							Toast.LENGTH_SHORT);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
		
	}
//	/** 显示自定义Toast提示(来自res) **/
//	protected void showCustomToast(int resId) {
//		View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(
//				R.layout.common_toast, null);
//		((HandyTextView) toastRoot.findViewById(R.id.toast_text))
//				.setText(getString(resId));
//		Toast toast = new Toast(BaseActivity.this);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.setView(toastRoot);
//		toast.show();
//	}
//
	/** 显示自定义Toast提示(来自String) **/
	public void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(getActivity()).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(getActivity());
		//第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠下  
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移  
        //第三个参数：同的第二个参数道理一样  
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
		toast.setGravity(Gravity.CENTER, 0, 0);
		//屏幕居中显示，X轴和Y轴偏移量都是0  
        //toast.setGravity(Gravity.CENTER, 0, 0); 
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
	public void showCustomerToast(String message,FragmentActivity fragmentActivity){
		View toastRoot = LayoutInflater.from(fragmentActivity).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(message);
		Toast toast = new Toast(fragmentActivity);
		//第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠下  
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移  
        //第三个参数：同的第二个参数道理一样  
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
		toast.setGravity(Gravity.CENTER, 0, 0);
		//屏幕居中显示，X轴和Y轴偏移量都是0  
        //toast.setGravity(Gravity.CENTER, 0, 0); 
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	
	/** 显示自定义网络错误Toast提示(来自String) **/
	public void showCustomErrorEthnetToast(String text) {
		View toastRoot = LayoutInflater.from(getActivity()).inflate(
				R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText("错误");
		Toast toast = new Toast(getActivity());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	
	
	
}
