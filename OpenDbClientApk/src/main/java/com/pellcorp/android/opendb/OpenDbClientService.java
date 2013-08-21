package com.pellcorp.android.opendb;

import android.app.IntentService;
import android.content.Intent;

public class OpenDbClientService extends IntentService {
	public static final String ITEM_SEARCH = "com.pellcorp.android.opendb.ITEM_SEARCH";
	
	private Preferences preferences;

	public OpenDbClientService() {
		super("OpenDbClientService");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		preferences = Preferences.getPreferences(this);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		DownloadResult<ItemSearchResults> results = null;
		
		try {
			if (preferences.isConfigured()) {
				OpenDbClient client = new OpenDbClientImpl(
						preferences.getHost(),
						preferences.getUsername(), 
						preferences.getPassword());
		
				String title = intent.getStringExtra(ItemSearch.TITLE_PARAM);
				ItemSearch itemSearch = new ItemSearch(client);
				results = new DownloadResult<ItemSearchResults>(itemSearch.titleSearch("%" + title + "%"));
			} else {
				results = new DownloadResult<ItemSearchResults>(true);
			}
			
		} catch (Exception e) {
			results = new DownloadResult<ItemSearchResults>(e.getMessage());
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(OpenDbClientReceiver.ACTION_ITEM_SEARCH);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(ITEM_SEARCH, results);
		sendBroadcast(broadcastIntent);
	}
}
