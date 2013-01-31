package org.jointsecurityarea.Yamba;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class StatusViewActivity extends Activity
{
	private String mMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d("YAMBA", "In StatusViewActivity onCreate");

		// Go away if we're in landscape
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			finish();
			return;
		}
		
		if (savedInstanceState == null) savedInstanceState = getIntent().getExtras();
		if(savedInstanceState == null) 	mMessage = "No Status"; 
		else mMessage = savedInstanceState.getString("status");

		setContentView(R.layout.statusview_wrapper);	
		
		FragmentManager mgr = getFragmentManager();
		FragmentTransaction trans = mgr.beginTransaction();
		StatusViewFragment frag = StatusViewFragment.newInstance(savedInstanceState);
		trans.add(R.id.statusview_details, frag);
		trans.commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		Log.d("YAMBA", "In StatusViewActivity onSaveInstance");
		super.onSaveInstanceState(outState);
		outState.putString("status", mMessage);
	}

}
