package kh.spaceclub.spaceballoon.camera;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraAutoPreview extends SurfaceView implements Runnable, SurfaceHolder.Callback {
	
	private static final long DEFAULT_INTERVAL = 10000L;
	
	private Camera mCamera;
	private SurfaceHolder mHolder;
	
	private Timer mTimer;
	private long mInterval;
	private PictureCallback mJpeg;
	
	public CameraAutoPreview(Context context, PictureCallback jpeg) {
		this(context, jpeg, DEFAULT_INTERVAL);
	}
	
	@SuppressWarnings("deprecation")
	public CameraAutoPreview(Context context, PictureCallback jpeg, long interval) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mJpeg = jpeg;
		mInterval = interval;
	}
	
	public void start() {
		stop();
		if (mCamera == null) {
			activateCamera();
		}
		
		if (mTimer == null) {
			mTimer = new Timer(true);
			final Handler handler = new Handler();
			final CameraAutoPreview self = this;
			
			Log.e("Debug", "timer schedule");
			
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					handler.post(self);
				}
			}, 0, mInterval);	
		}
	}
	
	private void takePicture() {
		mCamera.takePicture(null, null, mJpeg);
		/*
		mCamera.autoFocus(new AutoFocusCallback() {

			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				mCamera.takePicture(null, null, jpeg);
			}
		});
		//*/
	}
	
	public void stop() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
		}
		
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void run() {
		takePicture();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// activateCamera();
	}
	
	private void activateCamera() {
		if (mCamera == null) {
			mCamera = Camera.open();
		}
 		
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
			e.printStackTrace();
		}
	}
	
	public void startPreview() {
		if (mCamera != null)
			mCamera.startPreview();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stop();
	}
}
