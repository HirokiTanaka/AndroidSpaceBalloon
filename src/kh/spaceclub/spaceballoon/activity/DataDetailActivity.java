package kh.spaceclub.spaceballoon.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import kh.spaceclub.spaceballoon.R;
import kh.spaceclub.spaceballoon.SpaceballoonConst;
import kh.spaceclub.spaceballoon.data.dto.PictureDto;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DataDetailActivity extends AbstractBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datadetail);
		
		// get data to display
		PictureDto dto = null;
		Intent intent = getIntent();
        String intentVal = intent.getStringExtra(SpaceballoonConst.IntentKey.DATADETAIL);
        int id = Integer.parseInt(intentVal);
        dto = getPictureDao().getDataById(id);
		
		// show date when picture was taken.
		TextView createdTxtView = (TextView)findViewById(R.id.datadetail_created);
		String strCreated = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", SpaceballoonConst.APP_LOCALE)
			.format(dto.getCreated());
		createdTxtView.setText(strCreated);
		
		// show GPS values
		TextView latitudeTxtView = (TextView)findViewById(R.id.datadetail_latitude);
		TextView longitudeTxtView = (TextView)findViewById(R.id.datadetail_longitude);
		TextView altitudeTxtView = (TextView)findViewById(R.id.datadetail_altitude);
		latitudeTxtView.setText(String.format("%f", dto.getLatitude()));
		longitudeTxtView.setText(String.format("%f", dto.getLongitude()));
		altitudeTxtView.setText(String.format("%f", dto.getAltitude()));
		
		// show picture
		ImageView imgView = (ImageView)findViewById(R.id.datadetail_image);
		File file = new File(dto.getFilePath());
		if (file.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				Bitmap img = BitmapFactory.decodeStream(in);
				// 選択した画像を表示
				imgView.setImageBitmap(img);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						
					}
				}
			}	
		}
		
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
