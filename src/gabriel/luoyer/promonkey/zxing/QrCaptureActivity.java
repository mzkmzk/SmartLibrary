package gabriel.luoyer.promonkey.zxing;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.android.FinishListener;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.PreferencesActivity;
import com.google.zxing.client.android.camera.CameraManager;

import gabriel.luoyer.promonkey.R;
import gabriel.luoyer.promonkey.zxing.decode.CaptureActivityHandler;
import gabriel.luoyer.promonkey.zxing.decode.DecodeFormatManager;
import gabriel.luoyer.promonkey.zxing.decode.DecodeHintManager;
import gabriel.luoyer.promonkey.zxing.manage.AmbientLightManager;
import gabriel.luoyer.promonkey.zxing.manage.BeepManager;
import gabriel.luoyer.promonkey.zxing.manage.InactivityTimer;
import gabriel.luoyer.promonkey.zxing.view.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class QrCaptureActivity extends Activity implements SurfaceHolder.Callback, OnClickListener {
	private static final String TAG = QrCaptureActivity.class.getSimpleName();
	
	private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;
	private AmbientLightManager ambientLightManager;
	private ViewfinderView viewfinderView;
	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private Collection<BarcodeFormat> decodeFormats;
	private Map<DecodeHintType,?> decodeHints;
	private String characterSet;	
	private boolean hasSurface;
	
	private Button torchBtn;
	
	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}
	
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Window window = getWindow();
	    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_qr_capture);
		
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	    beepManager = new BeepManager(this);
	    ambientLightManager = new AmbientLightManager(this);
	    
	    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	}

	@Override
	protected void onResume() {
		super.onResume();
	    // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
	    // want to open the camera driver and measure the screen size if we're going to show the help on
	    // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
	    // off screen.
	    cameraManager = new CameraManager(getApplication());
	
	    viewfinderView = (ViewfinderView) findViewById(R.id.qr_capture_viewfinder_view);
	    viewfinderView.setCameraManager(cameraManager);
	    
	    torchBtn = (Button) findViewById(R.id.qr_capture_torch);
	    	torchBtn.setOnClickListener(null);
	    	torchBtn.setVisibility(View.GONE);

	    handler = null;
	    
	    if (hasSurface) {
	      // The activity was paused but not stopped, so the surface still exists. Therefore
	      // surfaceCreated() won't be called, so init the camera here.
	      initCamera(getSurfaceViewHolder());
	    } else {
	      // Install the callback and wait for surfaceCreated() to init the camera.
	    	getSurfaceViewHolder().addCallback(this);
	    }
	    
	    beepManager.updatePrefs();
	    ambientLightManager.start(cameraManager);
	    inactivityTimer.onResume();
	    
	    Intent intent = getIntent();
	    decodeFormats = null;
	    characterSet = null;
	    
	    if (intent != null) {
	        String action = intent.getAction();
	        String dataString = intent.getDataString();
	        Log.v(TAG, "=== dataString: " + dataString + " action: " + action);
	        if (Intents.Scan.ACTION.equals(action)) {
	            decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
	            decodeHints = DecodeHintManager.parseDecodeHints(intent);
	            if (intent.hasExtra(Intents.Scan.WIDTH) && intent.hasExtra(Intents.Scan.HEIGHT)) {
	            	int width = intent.getIntExtra(Intents.Scan.WIDTH, 0);
	            	int height = intent.getIntExtra(Intents.Scan.HEIGHT, 0);
	            	if (width > 0 && height > 0) {
	            		cameraManager.setManualFramingRect(width, height);
	            	}
	            }
	
	            if (intent.hasExtra(Intents.Scan.CAMERA_ID)) {
	            	int cameraId = intent.getIntExtra(Intents.Scan.CAMERA_ID, -1);
	            	if (cameraId >= 0) {
	            		cameraManager.setManualCameraId(cameraId);
	            	}
	            }	            
	        }
	    	characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
	    }
	}
	
	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
	    inactivityTimer.onPause();
	    ambientLightManager.stop();
	    beepManager.close();
	    cameraManager.closeDriver();
	    if (!hasSurface) {
	      getSurfaceViewHolder().removeCallback(this);
	    }
	    super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
//	      case KeyEvent.KEYCODE_BACK:
//	        if (source == IntentSource.NATIVE_APP_INTENT) {
//	          setResult(RESULT_CANCELED);
//	          finish();
//	          return true;
//	        }
//	        if ((source == IntentSource.NONE || source == IntentSource.ZXING_LINK) && lastResult != null) {
//	          restartPreviewAfterDelay(0L);
//	          return true;
//	        }
//	        break;
	      case KeyEvent.KEYCODE_FOCUS:
	      case KeyEvent.KEYCODE_CAMERA:
	        // Handle these events so they don't launch the Camera app
	        return true;
	      // Use volume up/down to turn on light
	      case KeyEvent.KEYCODE_VOLUME_DOWN:
	        cameraManager.setTorch(false);
	        return true;
	      case KeyEvent.KEYCODE_VOLUME_UP:
	        cameraManager.setTorch(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private SurfaceHolder getSurfaceViewHolder() {
		return (SurfaceHolder) ((SurfaceView) findViewById(R.id.qr_capture_preview_view)).getHolder();
	}
	
	public void onBackButtonClick(View view) {
		finish();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;		
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			// Creating the handler starts the preview, which can also throw a RuntimeException.
			if (handler == null) {
				handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
			}
			// 成功打开，设置灯光按钮
		    if(cameraManager.isTorchSupport()) {
		    	torchBtn.setVisibility(View.VISIBLE);
		    	torchBtn.setOnClickListener(this);
		    }
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			// Barcode Scanner has seen crashes in the wild of this variety:
			// java.?lang.?RuntimeException: Fail to connect to camera service
			Log.w(TAG, "Unexpected error initializing camera", e);
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(getString(R.string.app_name));
	    builder.setMessage(getString(R.string.msg_camera_framework_bug));
	    builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
	    builder.setOnCancelListener(new FinishListener(this));
	    builder.show();
	}

	/**
	 * A valid barcode has been found, so give an indication of R.id.decode_succeeded and show the results.
	 * 扫描成功处理
	 * @param rawResult The contents of the barcode.
	 * @param scaleFactor amount by which thumbnail was scaled
	 * @param barcode   A greyscale bitmap of the camera data which was decoded.
	 */
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
		inactivityTimer.onActivity();
//	    lastResult = rawResult;
//	    ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);

	    Log.d(TAG, "rawResult: " + rawResult.getText() + " \n barcode: " + barcode + " \n scaleFactor: " + scaleFactor);
	    boolean fromLiveScan = barcode != null;
	    if (fromLiveScan) {
//	      historyManager.addHistoryItem(rawResult, resultHandler);
	      // Then not from history, so beep/vibrate and we have an image to draw on
	      beepManager.playBeepSoundAndVibrate();
//	      drawResultPoints(barcode, scaleFactor, rawResult);
	    }

//	    switch (source) {
//	      case NATIVE_APP_INTENT:
//	      case PRODUCT_SEARCH_LINK:
//	        handleDecodeExternally(rawResult, resultHandler, barcode);
//	        break;
//	      case ZXING_LINK:
//	        if (scanFromWebPageManager == null || !scanFromWebPageManager.isScanFromWebPage()) {
//	          handleDecodeInternally(rawResult, resultHandler, barcode);
//	        } else {
//	          handleDecodeExternally(rawResult, resultHandler, barcode);
//	        }
//	        break;
//	      case NONE:
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	        if (fromLiveScan && prefs.getBoolean(PreferencesActivity.KEY_BULK_MODE, false)) {
	        	// 支持批量时使用,进行扫描恢复：可在xml中配置，或者自己提供设置功能
	          Toast.makeText(getApplicationContext(),
	                         getResources().getString(R.string.msg_bulk_mode_scanned) + " (" + rawResult.getText() + ')',
	                         Toast.LENGTH_SHORT).show();
	          // Wait a moment or else it will scan the same barcode continuously about 3 times
	          restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
	        } else {
//	          handleDecodeInternally(rawResult, resultHandler, barcode);
	          Toast.makeText(getApplicationContext(),
                         " (" + rawResult.getText() + ')', Toast.LENGTH_SHORT).show();
	          // 获取到所需信息，可根据需求，当前也显示、传递到新页面显示，或直接打开链接等操作
	          
	        }
//	        break;
//	    }
	  }
	
	  public void restartPreviewAfterDelay(long delayMS) {
		    if (handler != null) {
		      handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		    }
	  }
	  
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.qr_capture_torch:
				cameraManager.torchToggle();
				break;
		}
	}
}
