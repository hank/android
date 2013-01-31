package org.jointsecurityarea.Yamba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TimelineActivity extends BaseActivity
{
	private static final String DETAIL_FRAGMENT = "Timeline.DETAIL";
	private boolean usingFragments;

	@Override
	public void startActivityFromFragment(Fragment fragment, Intent intent,
			int requestCode)
	{
		Log.d("YAMBA", "In startActivityFromFragment");
		// If we're using fragments (landscape), just fill the box!
		if (usingFragments)
		{
			launchDetailFragment(intent.getExtras());
		}
		// Otherwise, start the activity up
		else startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d("YAMBA", "In TimelineActivity onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		// Set whether we're using fragments (should be true in landscape only)
		usingFragments = null != findViewById(R.id.timeline_details);

		// If we are, go ahead and fill the box with the empty fragment
		if (usingFragments)
		{
			addDetailFragment();
		}

	}

	private void addDetailFragment()
	{
		Log.d("YAMBA", "In addDetailFragment");
		FragmentManager mgr = getFragmentManager();
		// Try to find an existing fragment and return if found (do not
		// duplicate)
		if (mgr.findFragmentByTag(DETAIL_FRAGMENT) != null)
		{
			return;
		}
		// Create a new transaction
		FragmentTransaction trans = mgr.beginTransaction();
		// Add the fragment to the details view and tag it
		trans.add(R.id.timeline_details, StatusViewFragment.newInstance(null),
				DETAIL_FRAGMENT);
		trans.commit();
	}

	private void launchDetailFragment(Bundle b)
	{
		Log.d("YAMBA", "In launchDetailFragment");
		FragmentManager mgr = getFragmentManager();
		// Create a new transaction
		FragmentTransaction trans = mgr.beginTransaction();
		// Replace the existing fragment with a new one containing the status in
		// the bundle
		trans.replace(R.id.timeline_details, StatusViewFragment.newInstance(b),
				DETAIL_FRAGMENT);
		trans.commit();
	}

}
