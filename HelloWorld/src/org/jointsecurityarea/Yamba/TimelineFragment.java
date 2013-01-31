package org.jointsecurityarea.Yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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

	class TimelineBinder implements SimpleCursorAdapter.ViewBinder
	{
		@Override
		public boolean setViewValue(View view, Cursor cursor, int idx)
		{
			switch (view.getId())
			{
			case R.id.timeline_timestamp:
				// We have the time field
				CharSequence prettyDate = "Unknown";
				long utime = cursor.getLong(idx);
				if (utime > 0) prettyDate = DateUtils
						.getRelativeTimeSpanString(utime);
				((TextView) view).setText(prettyDate);
				return true;
			case R.id.timeline_user:
				// We have the user
				TextView tv = (TextView) view;
				tv.setText("Posted by " + cursor.getString(idx));
				return true;
			default:
				return false;
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		timelineAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.timeline_row, null, FROM, TO, 0);
		setListAdapter(timelineAdapter);
		timelineAdapter.setViewBinder(new TimelineBinder());
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = super.onCreateView(inflater, container, savedInstanceState);
		return root;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.d("YAMBA", "Clicked list item" + position);
		Cursor cur = (Cursor)getListAdapter().getItem(position);
		String status = cur.getString(cur.getColumnIndex(YambaContract.Timeline.Columns.STATUS));
		Intent i = new Intent(getActivity(), StatusViewActivity.class);
		i.putExtra("status", status);
		startActivity(i);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
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

	/* TODO: Get user images: http://yamba.marakana.com/hank/avatar/96 */

}
