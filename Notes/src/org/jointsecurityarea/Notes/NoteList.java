package org.jointsecurityarea.Notes;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NoteList extends Activity {
	protected SQLiteDatabase db;
	protected ListView noteList;
	protected ListAdapter adapter;
	protected Cursor cursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_index);
		db = (new DatabaseHelper(this)).getWritableDatabase();
		noteList = (ListView) findViewById(R.id.list);

		// Populate the list
		Cursor cursor = db.query("notes", new String[] { "title" }, 
				null, null, null, null, null, null);
		
		adapter = new SimpleCursorAdapter(this, 
				R.layout.note_list_item,
				cursor, 
				new String[] { "title" }, 
				new int[] { R.id.list_title }, 
				0);
		noteList.setAdapter(adapter);
	}
}