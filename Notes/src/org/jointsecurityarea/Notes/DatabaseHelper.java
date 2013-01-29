package org.jointsecurityarea.Notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "notes";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS notes(" +
				 "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				 "title TEXT, " +
				 "content TEXT)";
		db.execSQL(sql);
		Log.d("DB", "Create done");
		
		ContentValues values = new ContentValues();
		
		values.put("title", "Hmm");
		values.put("content", "Manatees love forbs.");
		db.insert("notes", "title", values);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
	}
}
