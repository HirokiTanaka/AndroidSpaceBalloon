package kh.spaceclub.spaceballoon.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kh.spaceclub.spaceballoon.R;
import kh.spaceclub.spaceballoon.data.dto.PictureDto;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DatalistActivity extends AbstractBaseActivity {

	private ListView mDatalist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datalist);
		
		mDatalist = (ListView)findViewById(R.id.datalist);
		TextView emptyTextView = (TextView)findViewById(R.id.emptyTextView);
		mDatalist.setEmptyView(emptyTextView);
		final List<PictureDto> data = getPictureDao().getData();
		mDatalist.setAdapter(new BaseAdapter() {

			@Override
			public int getCount() {
				return data.size();
			}

			@Override
			public Object getItem(int position) {
				return data.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@SuppressLint("InflateParams")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
			    TextView textView1;
			    TextView textView2;
			    TextView textView3;
			    View v = convertView;
			
			    if (v == null) {
			        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			        v = inflater.inflate(R.layout.datalist_row, null);
			    }
			    PictureDto row = (PictureDto)getItem(position);
			    if(row != null) {
					textView1 = (TextView)v.findViewById(R.id.row_id);
					textView2 = (TextView)v.findViewById(R.id.row_created);
					textView3 = (TextView)v.findViewById(R.id.row_gps);
					
					textView1.setText(String.valueOf(row.getId()));
					
					Date created = row.getCreated();
					String strCreated;
					if (created == null) {
						strCreated = "";
					} else {
						strCreated = new SimpleDateFormat("dd/MM HH:mm:ss", Locale.TAIWAN).format(created);
					}
					textView2.setText(strCreated);
					textView3.setText(String.format("La/Lo/Al:%f/%f/%f", row.getLatitude(), row.getLongitude(), row.getAltitude()));
			    }
			    return v;
			}		
		});
		
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