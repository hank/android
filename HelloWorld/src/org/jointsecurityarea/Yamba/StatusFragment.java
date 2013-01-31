package org.jointsecurityarea.Yamba;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.android.yamba.svc.YambaContract;

public class StatusFragment extends Fragment implements TextWatcher
{

	public static String starting_char_count;
	protected static TextView char_count;
	protected static EditText status_text_area;
	protected static Button submit_button;
	static Poster poster;

	static class Poster extends AsyncTask<String, Void, Void>
	{
		private final ContentResolver resolver;

		Poster(ContentResolver resolver)
		{
			this.resolver = resolver;
		}

		/** Returns 0 on success, 1 on failure */
		@Override
		protected Void doInBackground(String... args)
		{
			String status = args[0]; // Send the message

			ContentValues vals = new ContentValues();
			vals.put(YambaContract.Posts.Columns.STATUS, status);
			resolver.insert(YambaContract.Posts.URI, vals);
			return null;
		}

		@Override
		protected void onCancelled()
		{
			poster = null;
		}

		@Override
		protected void onCancelled(Void result)
		{
			poster = null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			poster = null;
		}
	}

	/**
	 * onCreate Creates the activity
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater
				.inflate(R.layout.status_fragment, container, false);

		// Get the starting character count
		starting_char_count = (String) getString(R.string.starting_char_count);
		// Find our status text area
		status_text_area = (EditText) root.findViewById(R.id.status_text_area);
		// Add a listener to the status text area
		status_text_area.addTextChangedListener(this);
		// Find our character count field
		char_count = (TextView) root.findViewById(R.id.status_char_count);
		// Initialize the value in character count and color green
		char_count.setText(starting_char_count);
		char_count.setTextColor(0xFF00FF00);
		// Find our submit button
		submit_button = (Button) root.findViewById(R.id.status_button_submit);
		// Set up click action
		submit_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				submit();
			}
		});

		return root;
	}

	protected void submit()
	{
		// Send the message
		if (poster != null)
		{
			Log.d("YAMBA", "Poster is not NULL, aborting post.");
			return;
		}
		new Poster(getActivity().getContentResolver()).execute(status_text_area
				.getText().toString());
		status_text_area.setText("");
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onResume()
	{
		Log.d("MAIN", "in onResume");
		super.onResume();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// If we're in bounds, set the box correctly.
		// Text box size should be enforced in XML
		Integer scc = Integer.valueOf(starting_char_count);
		if (s.length() >= 0 && s.length() <= scc)
		{
			char_count.setText(Integer.toString(scc - s.length()));
			// Set the color
			if (scc - s.length() <= 10)
				char_count.setTextColor(0xFFFF0000);
			else
				char_count.setTextColor(0xFF00FF00);
		}
	}

	@Override
	public void afterTextChanged(Editable s)
	{

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int before,
			int after)
	{

	}
}
