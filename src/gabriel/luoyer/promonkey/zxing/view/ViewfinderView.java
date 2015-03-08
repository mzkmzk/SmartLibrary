/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gabriel.luoyer.promonkey.zxing.view;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraConfigurationManager;
import com.google.zxing.client.android.camera.CameraManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import gabriel.luoyer.promonkey.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 80L;
  private static final int CURRENT_POINT_OPACITY = 0xA0;
  private static final int MAX_RESULT_POINTS = 20;
  private static final int POINT_SIZE = 6;

  private CameraManager cameraManager;
  private final Paint paint;
  private Bitmap resultBitmap;
  private final int maskColor;
  private final int resultColor;
  private int laserColor;
  private final int resultPointColor;
  private int scannerAlpha;
  private List<ResultPoint> possibleResultPoints;
  private List<ResultPoint> lastPossibleResultPoints;
  
	private int textSize; // 字体大小
	private float textMarginTop; //字体距离扫描框下面的距离
	private Bitmap lineBitmap; // 扫描线图片
	private int lineBitmapHeight; // 扫描线图片高度
	private int speedDistance; // 扫描线移动距离
	private int ScreenRate; // 四个边角对应的长度
	private float cornerWidth; // 四个边角对应的宽度
	private float cornerSpace; // 边角间距
	private float boderWidth; // 边框宽度
	private int slideTop; // 中间滑动线的最顶端位置
	boolean isFirst;
	
  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);

    // Initialize these once for performance rather than calling them every time in onDraw().
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Resources resources = getResources();
    maskColor = resources.getColor(R.color.viewfinder_mask);
    resultColor = resources.getColor(R.color.result_view);
    if(CameraConfigurationManager.isPortrait()) {
    	isFirst = true;
		textSize = resources.getDimensionPixelSize(R.dimen.zxing_hint_text_size);
		textMarginTop = resources.getDimension(R.dimen.zxing_hint_text_margin);
		lineBitmapHeight = resources.getDimensionPixelOffset(R.dimen.zxing_scan_line_height);
		speedDistance = resources.getDimensionPixelOffset(R.dimen.zxing_scan_line_speed);
		ScreenRate = resources.getDimensionPixelOffset(R.dimen.zxing_scan_screen_rate);
		cornerSpace = resources.getDimension(R.dimen.zxing_scan_corner_space);
		cornerWidth = resources.getDimension(R.dimen.zxing_scan_corner_width);
		boderWidth = resources.getDimension(R.dimen.zxing_scan_boder_width);
		lineBitmap = ((BitmapDrawable)(resources.getDrawable(R.drawable.qrcode_scan_line))).getBitmap();
    } else {
    	laserColor = resources.getColor(R.color.viewfinder_laser);
    }
    // 扫描可能取值点展示
    resultPointColor = resources.getColor(R.color.possible_result_points);
    scannerAlpha = 0;
    possibleResultPoints = new ArrayList<>(5);
    lastPossibleResultPoints = null;
  }

  public void setCameraManager(CameraManager cameraManager) {
    this.cameraManager = cameraManager;
  }

  @SuppressLint("DrawAllocation")
  @Override
  public void onDraw(Canvas canvas) {
    if (cameraManager == null) {
      return; // not ready yet, early draw before done configuring
    }
    Rect frame = cameraManager.getFramingRect();
    Rect previewFrame = cameraManager.getFramingRectInPreview();    
    if (frame == null || previewFrame == null) {
      return;
    }

    paint.setColor(resultBitmap != null ? resultColor : maskColor);

    // 绘制周边半透明区域
    drawShadow(canvas, paint, frame);

    if (resultBitmap != null) {
      // Draw the opaque result bitmap over the scanning rectangle
      paint.setAlpha(CURRENT_POINT_OPACITY);
      canvas.drawBitmap(resultBitmap, null, frame, paint);
    } else {
    	drawScanFrame(canvas, paint, frame);
      
      float scaleX = frame.width() / (float) previewFrame.width();
      float scaleY = frame.height() / (float) previewFrame.height();

      List<ResultPoint> currentPossible = possibleResultPoints;
      List<ResultPoint> currentLast = lastPossibleResultPoints;
      int frameLeft = frame.left;
      int frameTop = frame.top;
      if (currentPossible.isEmpty()) {
        lastPossibleResultPoints = null;
      } else {
        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = currentPossible;
        paint.setAlpha(CURRENT_POINT_OPACITY);
        paint.setColor(resultPointColor);
        synchronized (currentPossible) {
          for (ResultPoint point : currentPossible) {
            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                              frameTop + (int) (point.getY() * scaleY),
                              POINT_SIZE, paint);
          }
        }
      }
      if (currentLast != null) {
        paint.setAlpha(CURRENT_POINT_OPACITY / 2);
        paint.setColor(resultPointColor);
        synchronized (currentLast) {
          float radius = POINT_SIZE / 2.0f;
          for (ResultPoint point : currentLast) {
            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                              frameTop + (int) (point.getY() * scaleY),
                              radius, paint);
          }
        }
      }

      refreshScanFrameDelayed(frame);

    }
  }
  
  private void drawShadow(Canvas canvas, Paint paint, Rect frame) {
	    int width = canvas.getWidth();
	    int height = canvas.getHeight();
	    if(CameraConfigurationManager.isPortrait()) {
			//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
			//扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
			canvas.drawRect(0, 0, width, frame.top, paint);
			canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
			canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
			canvas.drawRect(0, frame.bottom, width, height, paint);
	    } else {
	        // Draw the exterior (i.e. outside the framing rect) darkened
	        canvas.drawRect(0, 0, width, frame.top, paint);
	        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
	        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
	        canvas.drawRect(0, frame.bottom + 1, width, height, paint);    	
	    }
  }
  
  private void drawScanFrame(Canvas canvas, Paint paint, Rect frame) {
	  if(CameraConfigurationManager.isPortrait()) {
		  float space = boderWidth + cornerSpace;
		  	if(isFirst){
				isFirst = false;
				slideTop = (int) (frame.top + space);
			}
			paint.setColor(Color.rgb(203, 203, 203));
			//画扫描线框
			canvas.drawRect(frame.left, frame.top, frame.right, frame.top + boderWidth, paint); // top
			canvas.drawRect(frame.left, frame.top + boderWidth, frame.left + boderWidth, frame.bottom - boderWidth, paint); // left
			canvas.drawRect(frame.left, frame.bottom - boderWidth, frame.right, frame.bottom, paint); // bottom
			canvas.drawRect(frame.right - boderWidth, frame.top + boderWidth, frame.right, frame.bottom - boderWidth, paint); // right
			//画扫描框边上的角，总共8个部分
			
			// 左上 －
			canvas.drawRect(frame.left + space, frame.top + space,
					frame.left + space + ScreenRate, frame.top + space + cornerWidth, paint);
			// 左上 丨
			canvas.drawRect(frame.left + space, frame.top + space,
					frame.left + space + cornerWidth, frame.top + space + ScreenRate, paint);
			// 右上 －
			canvas.drawRect(frame.right - space - ScreenRate, frame.top + space,
					frame.right - space, frame.top + space + cornerWidth, paint);
			// 右上 丨
			canvas.drawRect(frame.right - space - cornerWidth, frame.top + space,
					frame.right - space, frame.top + space + ScreenRate, paint);
			// 左下 －
			canvas.drawRect(frame.left + space, frame.bottom - space - cornerWidth,
					frame.left + space + ScreenRate, frame.bottom - space, paint);
			// 左下 丨
			canvas.drawRect(frame.left + space, frame.bottom - space - ScreenRate,
					frame.left + space + cornerWidth, frame.bottom - space, paint);
			// 右下 －
			canvas.drawRect(frame.right - space - ScreenRate, frame.bottom - space - cornerWidth,
					frame.right - space, frame.bottom - space, paint);
			// 右下 丨
			canvas.drawRect(frame.right - space - cornerWidth, frame.bottom - space - ScreenRate,
					frame.right - space, frame.bottom - space, paint);
			
			//绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
			slideTop += speedDistance;
			if(slideTop >= frame.bottom - lineBitmapHeight - space){
				slideTop = (int) (frame.top + space);
			}
			Rect lineRect = new Rect();  
            lineRect.left = (int) (frame.left + space);  
            lineRect.right = (int) (frame.right - space);  
            lineRect.top = slideTop;  
            lineRect.bottom = slideTop + lineBitmapHeight;  
            canvas.drawBitmap(lineBitmap, null, lineRect, paint); 
            
        	//画扫描框下面的字
            paint.setTextSize(textSize);    
//            paint.setAlpha(0xa0);    
//            paint.setTypeface(Typeface.create("System", Typeface.BOLD));   
            String text = getResources().getString(R.string.str_zxing_scan_text);  
            float textWidth = paint.measureText(text);  
            canvas.drawText(text, (canvas.getWidth() - textWidth)/2, (float) (frame.bottom + textMarginTop), paint); 
	  } else {
	      // Draw a red "laser scanner" line through the middle to show decoding is active
	      paint.setColor(laserColor);
	      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
	      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
	      int middle = frame.height() / 2 + frame.top;
	      canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);    	
	  }

  }

  private void refreshScanFrameDelayed(Rect frame) {
	  if(CameraConfigurationManager.isPortrait()) {
			//只刷新扫描框的内容，其他地方不刷新
			postInvalidateDelayed(40, frame.left, frame.top,
					frame.right, frame.bottom);
	  } else {
	      // Request another update at the animation interval, but only repaint the laser line,
	      // not the entire viewfinder mask.
	      postInvalidateDelayed(ANIMATION_DELAY,
	                            frame.left - POINT_SIZE,
	                            frame.top - POINT_SIZE,
	                            frame.right + POINT_SIZE,
	                            frame.bottom + POINT_SIZE);
	  }
  }

  public void drawViewfinder() {
    Bitmap resultBitmap = this.resultBitmap;
    this.resultBitmap = null;
    if (resultBitmap != null) {
      resultBitmap.recycle();
    }
    invalidate();
  }

  /**
   * Draw a bitmap with the result points highlighted instead of the live scanning display.
   *
   * @param barcode An image of the decoded barcode.
   */
  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

  public void addPossibleResultPoint(ResultPoint point) {
    List<ResultPoint> points = possibleResultPoints;
    synchronized (points) {
      points.add(point);
      int size = points.size();
      if (size > MAX_RESULT_POINTS) {
        // trim it
        points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
      }
    }
  }

}
