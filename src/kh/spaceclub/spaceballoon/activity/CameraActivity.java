package kh.spaceclub.spaceballoon.activity;

import kh.spaceclub.spaceballoon.R;
import kh.spaceclub.spaceballoon.camera.CameraPreview;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.ListView;

public class CameraActivity extends AbstractBaseActivity {

	CameraPreview cameraPreview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		FrameLayout preview = (FrameLayout)findViewById(R.id.camera_preview);
		cameraPreview = new CameraPreview(this);
		preview.addView(cameraPreview);
		super.initActionBar();
	}
	
	@Override
	protected DrawerLayout getDrawerLayoutCore() {
		
		return (DrawerLayout)findViewById(R.id.drawer_layout);
	}

	@Override
	protected ListView getDrawerListCore() {
		
		return (ListView)findViewById(R.id.left_drawer);
	}
}
