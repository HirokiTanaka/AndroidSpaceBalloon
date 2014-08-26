package kh.spaceclub.spaceballoon.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kh.spaceclub.spaceballoon.R;
import kh.spaceclub.spaceballoon.camera.CameraPreview;
import kh.spaceclub.spaceballoon.data.dto.PictureDto;
import kh.spaceclub.spaceballoon.location.LocationHelper;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ListView;

public class CameraActivity extends AbstractBaseActivity {

	private CameraPreview mCameraPreview;
	private boolean mIsTakingPicture = false;
	
	private LocationHelper mLocationHelper;
	private LocationHelper getLocationHelper() {
		if (mLocationHelper != null)
			return mLocationHelper;
		mLocationHelper = new LocationHelper((LocationManager)getSystemService(LOCATION_SERVICE));
		return mLocationHelper;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		FrameLayout preview = (FrameLayout)findViewById(R.id.camera_preview);
		mCameraPreview = new CameraPreview(this);
		preview.addView(mCameraPreview);
		preview.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!mIsTakingPicture)
				{
					mIsTakingPicture = true;
					mCameraPreview.takePicture(mPicJpgListener);
				}
				return true;
			}
		});
		getLocationHelper().start();
		super.initActionBar();
	}
	
	private PictureCallback mPicJpgListener = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			if (data == null)
				return;
			
            String saveDir = Environment.getExternalStorageDirectory().getPath() + "/space_balloon";

            // SD カードフォルダを取得
            File file = new File(saveDir);

            // フォルダ作成
            if (!file.exists() && !file.mkdir()) {
            	Log.e("Debug", "Make Dir Error");
            }

            // 画像保存パス
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.TAIWAN);
            String imgPath = saveDir + "/" + sf.format(cal.getTime()) + ".jpg";

            // ファイル保存
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(imgPath, true);
                fos.write(data);

                // アンドロイドのデータベースへ登録
                // (登録しないとギャラリーなどにすぐに反映されないため)
                registAndroidDB(imgPath);
            } catch (Exception e) {
                Log.e("Debug", e.getMessage());
            } finally {
            	if (fos != null) {
            		try {
            			fos.close();            			
            		} catch (IOException ioe) {
            			
            		}
            	    fos = null;	
            	}
            }
            
            // insert into picture
            PictureDto picture = new PictureDto();
            picture.setFilePath(imgPath);
            Location cl = getLocationHelper().getCurrentLocation();
            if (cl != null) {
            	picture.setLatitude(cl.getLatitude());
            	picture.setLongitude(cl.getLongitude());
            	picture.setAltitude(cl.getAltitude());
            }
            getPictureDao().insert(picture);
            
            // takePicture するとプレビューが停止するので、再度プレビュースタート
            mCameraPreview.startPreview();

            mIsTakingPicture = false;
		}
	};
	
    /** アンドロイドのデータベースへ画像のパスを登録 */
    private void registAndroidDB(String path) {
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = CameraActivity.this.getContentResolver();
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
	
	@Override
	protected DrawerLayout getDrawerLayoutCore() {
		
		return (DrawerLayout)findViewById(R.id.drawer_layout);
	}

	@Override
	protected ListView getDrawerListCore() {
		
		return (ListView)findViewById(R.id.left_drawer);
	}
	
	@Override
	protected void onResume() {
		getLocationHelper().start();
		super.onResume();
	}

	@Override
	protected void onPause() {
		getLocationHelper().stop();
		super.onPause();
	}
}
