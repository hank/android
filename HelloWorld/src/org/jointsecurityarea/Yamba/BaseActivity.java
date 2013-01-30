package org.jointsecurityarea.Yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity
{
	public static final String ABOUT_TEXT = "Yamba by Erik Gregg, 2013";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setHomeButtonEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		switch (id)
		{
		case android.R.id.home:
		case R.id.menu_timeline:
			startActivity(new Intent(this, TimelineActivity.class));
			return true;
		case R.id.menu_status:
			startActivity(new Intent(this, StatusActivity.class));
			return true;
		case R.id.menu_about:
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getApplicationContext(), ABOUT_TEXT, duration);
			toast.show();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
