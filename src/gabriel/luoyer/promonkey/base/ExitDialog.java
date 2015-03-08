package gabriel.luoyer.promonkey.base;

import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.utils.Utils;
import cn.waps.AppConnect;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExitDialog {
	private static ExitDialog instance = null;
	
	public static ExitDialog getInstance() {
		if(null == instance) {
			instance = new ExitDialog();
		}
		return instance;
	}
	
	private Dialog exitDialog = null;
	public void displayExitDialog(final Context context) {
		if(null == exitDialog) {
			Resources res = context.getResources();
			exitDialog = new Dialog(context, R.style.alert_dialog_style);
			View view = View.inflate(context, R.layout.dialog_exit, null);
			TextView msg = (TextView) view.findViewById(R.id.exit_dialog_title);
			msg.setText(String.format(res.getString(R.string.str_dialog_exit_msg),
					res.getString(R.string.app_name)));
			view.findViewById(R.id.exit_dialog_btn_exit)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(exitDialog != null){
							exitDialog.cancel();
						}
						System.exit(0);
					}
				});
			view.findViewById(R.id.exit_dialog_btn_cancel)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(exitDialog != null){
							exitDialog.cancel();
						}
					}
				});
			exitDialog.setContentView(view);
			// pop adview
			Button moreBtn = (Button) view.findViewById(R.id.exit_dialog_btn_more);
			final LinearLayout adView = (LinearLayout) view.findViewById(R.id.exit_dialog_pop_adview);
			Utils.setGone(moreBtn, adView);
			if(AppConnect.getInstance(context).hasPopAd(context)) {
				final LinearLayout pop_layout = AppConnect.getInstance(context).getPopAdView(context);
				if(pop_layout != null){
					Utils.setVisible(moreBtn, adView);
					moreBtn.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							AppConnect.getInstance(context).showOffers(context);
							if(exitDialog != null){
								exitDialog.cancel();
							}
						}
					});
					adView.addView(pop_layout, 0);
					exitDialog.setOnCancelListener(new OnCancelListener(){
						@Override
						public void onCancel(DialogInterface dialog) {
//							adView.removeViewAt(0);
						}
					});
				}
			}
//			exitDialog.setCancelable(false);
		}
		if(!exitDialog.isShowing()) {
			exitDialog.show();
		}
	}
}
