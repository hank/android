package org.jointsecurityarea.Yamba;

import android.app.Fragment;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatusViewFragment extends Fragment
{
	public static StatusViewFragment newInstance(Bundle b)
	{
		StatusViewFragment frag = new StatusViewFragment();
		frag.setArguments(b);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.d("YAMBA", "In StatusViewFragment onCreateView");
		super.onCreateView(inflater, container, savedInstanceState);

		if (savedInstanceState == null)
		{
			savedInstanceState = getArguments();
		}
		String message = (savedInstanceState == null) ? "Select a status on the left"
				: savedInstanceState.getString("status");
		TextView v = (TextView) inflater.inflate(R.layout.statusview_fragment, container, false);
		v.setText(message);
		// Linkify!
		Linkify.addLinks(v, Linkify.ALL);
		return v;
	}

}
