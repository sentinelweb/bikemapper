package net.robmunro.gpstest.ui;

import net.robmunro.gpstest.model.CompassData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class CompassView extends View {

	private Paint   mPaint = new Paint();
    private Path    mPath = new Path();
    Bitmap b;
	
    CompassData data = null;//new CompassData(new float[3], new float[3], new float[3], new float[3], new float[9], 0, -1000, -1000, -1);

	public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		// Construct a wedge-shaped path
		mPath.moveTo(0, -30);
        mPath.lineTo(-5, 40);
        mPath.lineTo(0, 30);
        mPath.lineTo(5, 40);
        mPath.close();
        
	}
	@Override 
	protected void onDraw(Canvas canvas) {
        Paint paint = mPaint;
        if (data!=null) {
	        int w = this.getMeasuredWidth();
	        int h = this.getMeasuredHeight();
	        //canvas.drawColor(Color.WHITE);// sets background
	        paint.setAntiAlias(true);
	        int cx = w / 2;
	        int cy = h / 2;
	        paint.setARGB(128, 128, 128,128);
	        paint.setStyle(Style.FILL);
	        canvas.drawCircle(cx, cy, w / 2, paint);
	        paint.setARGB(255, 128, 128,128);
	        paint.setStyle(Style.STROKE);
	        canvas.drawCircle(cx, cy, w / 2, paint);
	        paint.setStyle(Paint.Style.FILL);
	        canvas.translate(cx, cy);
	        //Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw:");
	        //Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw:"+w+":"+h);
	        if (data.oritentationValues != null) {            
	            canvas.rotate(-data.oritentationValues[0]);
	           // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw orient:"+oritentationValues[0]);
	            paint.setARGB(255, 190, 190,190);
	            canvas.drawPath(mPath, mPaint);
	            canvas.rotate(data.oritentationValues[0]);
	        }
	        if (data.magneticFieldValues[0] != 0) {            
	            canvas.rotate(data.north);
	           // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw orient:"+oritentationValues[0]);
	            paint.setARGB(255, 220, 220,220);
	            canvas.drawPath(mPath, mPaint);
	            canvas.rotate(-data.north);
	        }
	        if (data.bearingDirection != -1000) {            
	            canvas.rotate(data.bearingDirection);
	           // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw orient:"+oritentationValues[0]);
	            paint.setARGB(192, 192, 192,220);
	            canvas.drawPath(mPath, mPaint);
	            canvas.rotate(-data.bearingDirection);
	        }
	        if (data.directionToTgt != -1000) {           
	        	float nth = -data.oritentationValues[0];
	            canvas.rotate(nth+data.directionToTgt);
	           // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw orient:"+oritentationValues[0]);
	            paint.setColor(Color.GREEN);
	            canvas.drawPath(mPath, mPaint);
	            canvas.rotate(-(nth+data.directionToTgt));
	        }
	        if (data.currDirection != null) {           
	        	float nth = -data.oritentationValues[0];
	            canvas.rotate(nth+(float)data.currDirectionBearing);
	           // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw orient:"+oritentationValues[0]);
	            paint.setARGB(255, 0, 0, 255);
	            canvas.drawPath(mPath, mPaint);
	            canvas.rotate(-(nth+(float)data.currDirectionBearing));
	        }
	        
	        if (data.nextDirection != null) {           
	        	float nth = -data.oritentationValues[0];
	            canvas.rotate(nth+(float)data.nextDirectionBearing);
	           // Log.d(LocationTestActivity.LOCATION_TEST_TAG, "draw orient:"+oritentationValues[0]);
	            paint.setARGB(192, 128, 128, 255);
	            canvas.drawPath(mPath, mPaint);
	            canvas.rotate(-(nth+(float)data.nextDirectionBearing));
	        }
	        if (data.lastUpdated>-1) {
	        	paint.setColor(Color.CYAN);
	        	canvas.drawRect(w-5,0,w,5,paint);
	        	if (System.currentTimeMillis()-data.lastUpdated>500) {
	        		data.lastUpdated=-1;
	        	}
	        }
        }
        
    }

    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public CompassData getData() {
		return data;
	}

	public void setData(CompassData data) {
		data.setV(this);
        this.data = data;
	}
    
	
	
}
