package gabriel.luoyer.promonkey.dialog.list;

import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.utils.Utils;
import java.util.ArrayList;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class AgeSelectActivity extends Activity {
	private static final String TAG = "ListViewActivity";
	private static onAgeSelectListener listener = null;
	private final static String BUNDLE_CUR_AGE = "current_age";
	private int curAge;

	public static void startAgeSelect(Context context, int curAge) {
		try {
			listener = (onAgeSelectListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + 
					" must implements onAgeSelectListener");
		}
		Intent intent = new Intent(context, AgeSelectActivity.class);
		intent.putExtra(BUNDLE_CUR_AGE, curAge);
		context.startActivity(intent);
	}

	public static void startAgeSelect(Fragment fragment, int curAge) {
		try {
			listener = (onAgeSelectListener) fragment;
		} catch (ClassCastException e) {
			throw new ClassCastException(fragment.toString() + 
					" must implements onAgeSelectListener");
		}
		Intent intent = new Intent(fragment.getActivity(), AgeSelectActivity.class);
		intent.putExtra(BUNDLE_CUR_AGE, curAge);
		fragment.startActivity(intent);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_ac_list_select);
		setFinishOnTouchOutside(true);
		getData();
		getViews();
	}

	private void getData() {
		curAge = getIntent().getExtras().getInt(BUNDLE_CUR_AGE);
		Utils.logh(TAG, " curAge: " + curAge);
	}

	private void getViews() {
		TextView title = (TextView) findViewById(R.id.comm_dialog_title);
		title.setText(R.string.str_dialog_select_title_age);
		ListView lv = (ListView) findViewById(R.id.comm_dialog_list);
		final AgeAdapter adapter = new AgeAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos,long id) {
				int value = (Integer) adapter.getItem(pos);
				Utils.logh(TAG, " ------- pos : " + pos + " value: " + value + " curAge: " + curAge);
				if(curAge != value) {
					listener.onAgeSelect(value);				
				}
				finish();
			}
		});
	}
	
	public void onLayoutSpaceClick(View view) {
		Utils.logh(TAG, "onLayoutSpaceClick");
		finish();
	}
	
	public void onCancleBtnClick(View view) {
		Utils.logh(TAG, "onCancleBtnClick");
		finish();
	}
	
	private class AgeAdapter extends BaseAdapter {
		private ArrayList<Integer> sexs;
		
		public AgeAdapter() {
			sexs = new ArrayList<Integer>();
			for(int i=20; i<=80; i++) {
				sexs.add(i);
			}
		}
		
		@Override
		public int getCount() {
			return sexs.size();
		}

		@Override
		public Object getItem(int position) {
			return sexs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SexViewHolder holder;
			if(null == convertView) {
				convertView = View.inflate(AgeSelectActivity.this, R.layout.common_select_list_item, null);
				holder = new SexViewHolder();
				holder.nameTv = (TextView) convertView.findViewById(R.id.common_select_item_name);
				convertView.setTag(holder);
			} else {
				holder = (SexViewHolder) convertView.getTag();
			}
			holder.nameTv.setText(String.format(getString(R.string.str_age_wrap), sexs.get(position)));
			
			return convertView;
		}
		
		private class SexViewHolder {
			protected TextView nameTv;
		}
	}
	
	public interface onAgeSelectListener {
		public abstract void onAgeSelect(int newAge);
	}
}
