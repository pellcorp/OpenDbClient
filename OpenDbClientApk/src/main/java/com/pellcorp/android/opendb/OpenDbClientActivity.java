package com.pellcorp.android.opendb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.pellcorp.android.opendb.OpenDbClientReceiver.Receiver;

public class OpenDbClientActivity extends Activity implements Receiver {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private OpenDbClientReceiver receiver;
	private ProgressDialog progressDialog;
	private Preferences preferences;
	private TableLayout resultsLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = Preferences.getPreferences(this);
		
		logger.info("Starting onCreate");

		setContentView(R.layout.main);

		resultsLayout = (TableLayout) findViewById(R.id.results_table);

		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(
		LayoutParams.FILL_PARENT,
		LayoutParams.WRAP_CONTENT));
		resultsLayout.addView(tr_head);

		IntentFilter filter = new IntentFilter(OpenDbClientReceiver.ACTION_ITEM_SEARCH);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new OpenDbClientReceiver(this);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onStart() {
		super.onStart();

		logger.info("Starting onStart");
	}

	private void itemSearch(String title) {
		if (preferences.isConfigured()) {
			//progressDialog = ProgressDialog.show(this, getString(R.string.loading_usage), getString(R.string.please_wait));
		
			Intent intent = new Intent(this, OpenDbClientService.class);
			intent.putExtra(ItemSearch.TITLE_PARAM, title);
			startService(intent);
		} else {
			Dialog dialog = createSettingsMissingDialog(getString(R.string.missing_connection_details));
			dialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		}
		return false;
	}

	private AlertDialog createErrorDialog(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setCancelable(false)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		return builder.create();
	}

	private AlertDialog createSettingsMissingDialog(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(message)
				.setCancelable(true)
				.setPositiveButton(R.string.settings_label,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								startActivity(new Intent(
										OpenDbClientActivity.this,
										PrefsActivity.class));
							}
						});
		return builder.create();
	}

	private void populateSearchResults(DownloadResult<ItemSearchResults> result) {
		resultsLayout.removeAllViews();
		for(Item item : result.getResult().getItemList()) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			
			TextView type = new TextView(this);
			type.setText(item.getType());
			type.setTextColor(Color.WHITE);
			tr.addView(type);
			
			TextView title = new TextView(this);
			type.setText(item.getTitle());
			type.setTextColor(Color.WHITE);
			tr.addView(title);
			
			resultsLayout.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
		}
	}
	
	@Override
	public void onReceive(DownloadResult<ItemSearchResults> results) {
		progressDialog.dismiss();
		
		if (results.getResult() != null) {
			populateSearchResults(results);
		} else if (results.isInvalidCredentials()) {
			Dialog dialog = createSettingsMissingDialog(getString(R.string.missing_connection_details));
			dialog.show();
		} else {
			AlertDialog dialog = createErrorDialog(results.getErrorMessage());
			dialog.show();
		}
	}
}
