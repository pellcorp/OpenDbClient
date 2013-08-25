package com.pellcorp.android.opendb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemSearchResults implements Serializable {
	private static final long serialVersionUID = 1300251719300627127L;
	
	private final List<Item> itemList = new ArrayList<Item>();
	
	public ItemSearchResults() {
	}
	
	public List<Item> getItemList() {
		return itemList;
	}
}
