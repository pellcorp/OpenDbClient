package com.pellcorp.android.opendb;

import java.util.ArrayList;
import java.util.List;

public class ItemSearchResults {
	private final List<Item> itemList = new ArrayList<Item>();
	
	public ItemSearchResults() {
	}
	
	public List<Item> getItemList() {
		return itemList;
	}
}
