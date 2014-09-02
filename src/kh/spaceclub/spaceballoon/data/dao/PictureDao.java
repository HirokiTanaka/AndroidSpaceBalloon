package kh.spaceclub.spaceballoon.data.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kh.spaceclub.spaceballoon.SpaceballoonConst;
import kh.spaceclub.spaceballoon.data.DatabaseHelper;
import kh.spaceclub.spaceballoon.data.dto.PictureDto;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PictureDao {

	public static String getCreateTableSql() {
		return "create table picture ("
				+ "id integer primary key autoincrement"
				+ ", file_path text not null"
				+ ", latitude real not null"
				+ ", longitude real not null"
				+ ", altitude real not null"
				+ ", created text not null"
				+ ")";
	}
	
	private DatabaseHelper dbHelper;
	protected SQLiteDatabase getDatabase() {
		return dbHelper.getDatabase();
	}
	
	public PictureDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}	
	
	public void insert(PictureDto dto) {
		SQLiteDatabase db = getDatabase();
		String sql = "INSERT INTO picture (file_path, latitude, longitude, altitude, created) VALUES (?, ?, ?, ?, datetime('now', 'localtime'));";
		db.execSQL(sql, new Object[] { dto.getFilePath(), dto.getLatitude(), dto.getLongitude(), dto.getAltitude() });
	}
	
	public PictureDto getDataById(int id) {
		SQLiteDatabase db = getDatabase();
		String sql = "SELECT * FROM picture WHERE id = ?";
		SQLiteCursor c = (SQLiteCursor)db.rawQuery(sql, new String[] { String.valueOf(id) });
		int rowcount = c.getCount();
		if (rowcount < 1)
			return null;
		
		if (!c.moveToFirst()) {
			return null;
		}
		
		return createDto(c);
	}
	
	public List<PictureDto> getData() {
		List<PictureDto> ret = new ArrayList<PictureDto>();
		SQLiteDatabase db = getDatabase();
		String sql = "SELECT * FROM picture ORDER BY id ASC";
		SQLiteCursor c = (SQLiteCursor)db.rawQuery(sql, null);
		int rowcount = c.getCount();
		if (rowcount < 1)
			return ret;
		
		if (c.moveToFirst()) {
			do {
				ret.add(createDto(c));
			} while (c.moveToNext());
		}
		return ret;
	}
	
	private PictureDto createDto(SQLiteCursor c) {
		PictureDto row = new PictureDto();
		row.setId(c.getInt(c.getColumnIndex("id")));
		row.setFilePath(c.getString(c.getColumnIndex("file_path")));
		row.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
		row.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
		row.setAltitude(c.getDouble(c.getColumnIndex("altitude")));
		String strCreateAt = c.getString(c.getColumnIndex("created"));
		Date created = null;
		try {
			created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", SpaceballoonConst.APP_LOCALE).parse(strCreateAt);
		} catch (ParseException e) {
			Log.e("Debug", "date parse error:" + strCreateAt);
		}
		row.setCreated(created);
		return row;
	}
}
