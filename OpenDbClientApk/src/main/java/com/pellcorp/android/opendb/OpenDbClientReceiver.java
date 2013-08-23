package com.pellcorp.android.opendb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OpenDbClientReceiver extends BroadcastReceiver {
	public static final String ACTION_ITEM_SEARCH =
		      "com.pellcorp.android.opendb.action.ACTION_ITEM_SEARCH";
	
	private final Receiver receiver;
	
	public OpenDbClientReceiver(final Receiver receiver) {
		super();
		
		this.receiver = receiver;
	}
	
	public interface Receiver {
		void onReceive(DownloadResult<ItemSearchResults> results);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onReceive(Context context, Intent intent) {
		DownloadResult<ItemSearchResults> results = 
				(DownloadResult<ItemSearchResults>) intent.getSerializableExtra(OpenDbClientService.ITEM_SEARCH);
		
		receiver.onReceive(results);
	}
}
