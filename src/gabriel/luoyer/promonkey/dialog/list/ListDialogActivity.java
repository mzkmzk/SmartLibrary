package gabriel.luoyer.promonkey.dialog.list;

import gabriel.luoyer.promonkey.MainApp;
import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.dialog.list.AgeSelectActivity.onAgeSelectListener;
import gabriel.luoyer.promonkey.dialog.list.RegionInfoSelectActivity.onRegionSelectListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class ListDialogActivity extends Activity
								implements View.OnClickListener,
								onAgeSelectListener,
								onRegionSelectListener {
	
	private int curAge;
	private String curRegion;
	private TextView ldTxt, eldTxt;
	
	public static void startListDialogActivity(Context context) {
		context.startActivity(
			new Intent(context, ListDialogActivity.class)
		);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_dialog);
		curAge = 0;
		curRegion = "";
		getViews();
	}

	private void getViews() {
		findViewById(R.id.list_dialog_listview).setOnClickListener(this);
		ldTxt = (TextView) findViewById(R.id.list_dialog_listview_txt);
		findViewById(R.id.list_dialog_expandable_listview).setOnClickListener(this);
		eldTxt = (TextView) findViewById(R.id.list_dialog_expandable_listview_txt);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.list_dialog_listview:
				AgeSelectActivity.startAgeSelect(ListDialogActivity.this, curAge);
				break;
			case R.id.list_dialog_expandable_listview:
				RegionInfoSelectActivity.startRegionSelect(ListDialogActivity.this, curRegion);
				break;
		}
	}

	@Override
	public void onAgeSelect(int newAge) {
		curAge = newAge;
		ldTxt.setText(String.format(getString(R.string.str_age_wrap), newAge));
	}

	@Override
	public void onRegionSelect(String newRegion) {
		curRegion = newRegion;
		eldTxt.setText(MainApp.getInstance().getRegionName(newRegion));
	}
	
	
}
