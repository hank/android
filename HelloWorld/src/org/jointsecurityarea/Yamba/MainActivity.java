package org.jointsecurityarea.Yamba;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class MainActivity extends Activity implements TextWatcher {

	public static String starting_char_count;
	protected static TextView char_count;
	protected static EditText status_text_area;
	protected static Button submit_button;
	protected static YambaClient yc;

	static class Poster extends AsyncTask<String, Void, Integer> {
		private Context ctx;
		private YambaClient yc;

		Poster(Context ctx, YambaClient yc) {
			this.ctx = ctx;
			this.yc = yc;
		}

		/** Returns 0 on success, 1 on failure */
		@Override
		protected Integer doInBackground(String... args) {
			int ret = 0;
			// Send the message
			try {
				yc.postStatus(args[0]);
			} catch (YambaClientException ye) {
				Log.d("YAMBA", "Failed: " + ye.getLocalizedMessage());
				ret = 1;
			}
			return Integer.valueOf(ret);
		}

		@Override
		protected void onPostExecute(Integer result) {
			int duration = Toast.LENGTH_SHORT;
			Toast toast;
			if (result == 0) {
				// Success!
				toast = Toast.makeText(ctx, "Success", duration);
				status_text_area.setText("");
			} else {
				toast = Toast.makeText(ctx, "Failure", duration);
			}
			toast.show();
		}
	}

	/**
	 * onCreate Creates the activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MAIN", "in onCreate");

		// Display the layout
		setContentView(R.layout.activity_main);

		// Get the starting character count
		starting_char_count = (String) getString(R.string.starting_char_count);
		// Find our status text area
		status_text_area = (EditText) findViewById(R.id.status_text_area);
		// Add a listener to the status text area
		status_text_area.addTextChangedListener(this);
		// Find our character count field
		char_count = (TextView) findViewById(R.id.status_char_count);
		// Initialize the value in character count and color green
		char_count.setText(starting_char_count);
		char_count.setTextColor(0xFF00FF00);
		// Find our submit button
		submit_button = (Button) findViewById(R.id.status_button_submit);
		// Set up click action
		submit_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});
		
		yc = new YambaClient("hank", "manatees",
				"http://yamba.marakana.com/api");
	}

	protected void submit() {
		// Send the message
		new Poster(getApplicationContext(), yc).execute(
				status_text_area.getText().toString()
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		Log.d("MAIN", "in onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d("MAIN", "in onResume");
		super.onResume();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// If we're in bounds, set the box correctly.
		// Text box size should be enforced in XML
		Integer scc = Integer.valueOf(starting_char_count);
		if (s.length() >= 0 && s.length() <= scc) {
			char_count.setText(Integer.toString(scc - s.length()));
			// Set the color
			if (scc - s.length() <= 10)
				char_count.setTextColor(0xFFFF0000);
			else
				char_count.setTextColor(0xFF00FF00);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int before,
			int after) {

	}
}
