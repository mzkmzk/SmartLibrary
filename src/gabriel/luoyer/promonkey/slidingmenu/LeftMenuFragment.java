package gabriel.luoyer.promonkey.slidingmenu;

import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

public class LeftMenuFragment extends Fragment implements OnClickListener {
	private static final String TAG = "LeftMenuFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_left_menu, container, false);
		getAndSetViews(view);
		return view;
	}

	private void getAndSetViews(View view) {
		view.findViewById(R.id.left_menu_get_source).setOnClickListener(this);
		view.findViewById(R.id.left_menu_recommend).setOnClickListener(this);
		view.findViewById(R.id.left_menu_blog).setOnClickListener(this);
//		view.findViewById(R.id.btn_ma_other_app).setOnClickListener(this);
		view.findViewById(R.id.left_menu_mailto).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.left_menu_get_source:
				mHandle.sendEmptyMessage(MSG_SUPPORT);
				break;
			case R.id.left_menu_mailto:
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String [] {getResources().getString(R.string.str_ma_mail)});
				startActivity(Intent.createChooser(emailIntent, null));
				break;
			case R.id.left_menu_blog:
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.str_ma_hompage)));
				startActivity(intent);
				break;
//			case R.id.btn_ma_other_app:
//				AppConnect.getInstance(getActivity()).showMore(getActivity());
//				break;
			case R.id.left_menu_recommend:
				AppConnect.getInstance(getActivity()).showOffers(getActivity());
				break;
		}
	}
	
	private final static int MSG_SUPPORT = 1;
	private final static int MSG_DOWNLOAD_DIALOG = 2;
	private final static int MSG_SCORE_DIALOG = 3;
	private Handler mHandle = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch(msg.what) {
				case MSG_SUPPORT:
					getSupportDialog();
					break;
				case MSG_DOWNLOAD_DIALOG:
					downloadDialog();
					break;
				case MSG_SCORE_DIALOG:
					scoreDialog();
					break;
			}
			return false;
		}
	});
	
	private void getSupportDialog() {
		showWaitDialog();
		AppConnect.getInstance(getActivity()).getPoints(new UpdatePointsNotifier() {
			@Override
			public void getUpdatePoints(String msg, int score) {
				Utils.logh(TAG, "getUpdatePoints msg: " + msg + " score: " + score);
				if(score > 0) {
					mHandle.sendEmptyMessage(MSG_DOWNLOAD_DIALOG);
				} else {
					mHandle.sendEmptyMessage(MSG_SCORE_DIALOG);
				}
				dismissWaitDialog();
			}

			@Override
			public void getUpdatePointsFailed(String msg) {
				dismissWaitDialog();
				Utils.logh(TAG, "getUpdatePointsFailed msg: " + msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			}
			
		});		
	}
	
	private void downloadDialog() {
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.str_ma_get_source_ok_dialog_title)
			.setMessage(String.format(
					getResources().getString(R.string.str_ma_get_source_ok_dialog_msg), Utils.mkTmpDirs()))
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	copyBackground();
	            	dialog.dismiss();
	            }
			})
	        .setCancelable(false)
			.create()
			.show();
	}
	
	private void copyBackground() {
		showWaitDialog();
		new AsyncTask<Object, Object, String>() {
			@Override
			protected String doInBackground(Object... params) {
				return doAsyncOperations();
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissWaitDialog();
				showConfirmDialog(result);
			}
		}.execute(null, null, null);
	}
	
	private void showConfirmDialog(String result) {
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.str_ma_get_source_dialog_title_ok)
			.setMessage(String.format(
					getResources().getString(R.string.str_ma_get_source_dialog_msg_ok), result))
			.setNegativeButton(R.string.str_ma_get_source_dialog_btn_rego, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
					AppConnect.getInstance(getActivity()).showOffers(getActivity());
	            }
			})
			.setPositiveButton(android.R.string.ok,  new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	;
	            }
			})
	        .setCancelable(false)
			.create()
			.show();
	}
	
	private String doAsyncOperations() {
		String dir = Utils.mkTmpDirs();
		// First clear directory
		File dirs = new File(dir);
		if(dirs.exists()) {
			File[] files = dirs.listFiles();
			if(files.length > 0) {
				for(File f : files) {
					f.delete();
				}
			}
		}
		// Do copy
		File file = new File(dir, Utils.FILE_BASE_NAME + System.currentTimeMillis() + ".rar");
		String origin = Utils.getFilePathName(Utils.FILE_ASSETS_PATH, Utils.FILE_BASE_NAME);
		Utils.logh(TAG, "origin: " + origin);
		try {
			InputStream is = getActivity().getAssets().open(origin);
			String path = saveFileToLocal(file, is);
			Utils.logh(TAG, "out: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
	private String saveFileToLocal(File file, InputStream input) {
        byte[] buffer = new byte[8192];
        int n = 0;
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);     
            while ((n = input.read(buffer)) >= 0) {     
                output.write(buffer, 0, n);
            }                
        } catch (FileNotFoundException e) {
        	Utils.logh(TAG, e.getMessage().toString());
        } catch(IOException e) {
        	try {
        		file.delete();
        	} catch(Exception ex) {}
        	Utils.logh(TAG, e.getMessage().toString());
        } finally {
            if(null != output) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                	Utils.logh(TAG, e.getMessage().toString());
                }
            }
        }
        return file.getAbsolutePath();
	}
	
	private void scoreDialog() {
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.str_ma_get_source_dialog_title)
			.setMessage(R.string.str_ma_get_source_dialog_msg)
			.setPositiveButton(R.string.str_ma_get_source_dialog_btn_go, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
					AppConnect.getInstance(getActivity()).showOffers(getActivity());
	            }
			})
			.setNegativeButton(R.string.str_ma_get_source_dialog_btn_cancel,  new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	;
	            }
			})
	        .setCancelable(false)
			.create()
			.show();
	}
	
	private ProgressDialog mWaitDialog;
	private void showWaitDialog() {
		if(null == mWaitDialog) {
			mWaitDialog = new ProgressDialog(getActivity());
			mWaitDialog.setMessage(getActivity().getResources().getString(R.string.str_loading_wait));
			mWaitDialog.setCancelable(false);
		}
		if(!mWaitDialog.isShowing()) {
			mWaitDialog.show();
		}
	}
	
	public void dismissWaitDialog() {
		if(null != mWaitDialog && mWaitDialog.isShowing()) {
			mWaitDialog.dismiss();
		}
	}
}
