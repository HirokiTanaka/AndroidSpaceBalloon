package kh.spaceclub.spaceballoon.activity;

import kh.spaceclub.spaceballoon.R;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

public class MainActivity extends AbstractBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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