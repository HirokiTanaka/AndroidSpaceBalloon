package kh.spaceclub.spaceballoon.data.dao;

import kh.spaceclub.spaceballoon.data.DatabaseHelper;
import kh.spaceclub.spaceballoon.data.dto.PictureDto;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}
