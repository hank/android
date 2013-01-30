package org.jointsecurityarea.Yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.marakana.android.yamba.svc.YambaContract;

public class TimelineFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor>
{
	private SimpleCursorAdapter timelineAdapter;
	public static final int LOADER_ID = 133701;

	private static String[] PROJECTION = new String[] {
			YambaContract.Timeline.Columns.ID,
			YambaContract.Timeline.Columns.STATUS,
			YambaContract.Timeline.Columns.USER,
			YambaContract.Timeline.Columns.TIMESTAMP };
	private static String[] FROM = new String[PROJECTION.length - 1];
	static
	{
		System.arraycopy(PROJECTION, 1, FROM, 0, FROM.length);
	}
	private static int[] TO = new int[] { R.id.timeline_status,
			R.id.timeline_user, R.id.timeline_timestamp };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = super.onCreateView(inflater, container, savedInstanceState);

		timelineAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.timeline_row, null, FROM, TO, 0);
		setListAdapter(timelineAdapter);
		getLoaderManager().initLoader(LOADER_ID, null, this);
		return root;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(), YambaContract.Timeline.URI,
				PROJECTION, null, null,
				YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		timelineAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		timelineAdapter.swapCursor(null);

	}

}
