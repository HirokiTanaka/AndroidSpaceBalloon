package kh.spaceclub.spaceballoon.data;

import kh.spaceclub.spaceballoon.data.dao.PictureDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "space_balloon.db";
	private static final int DB_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = PictureDao.getCreateTableSql();
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	private static SQLiteDatabase db;
	
	public SQLiteDatabase getDatabase() {
		if (db != null)
			return db;
		
		db = this.getWritableDatabase();
		return db;
	}
	
	public void closeDatabase() {
		
		if (db != null) {
			db.close();
			db = null;
		}
	}
}
