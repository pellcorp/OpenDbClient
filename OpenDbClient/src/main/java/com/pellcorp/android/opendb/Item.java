package com.pellcorp.android.opendb;

import java.io.Serializable;

public class Item implements Serializable {
	private String title;
	private int itemId;
	private int instanceNo;
	
	public Item(int itemId, int instanceNo, String title) {
		this.itemId = itemId;
		this.instanceNo = instanceNo;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getItemId() {
		return itemId;
	}

	public int getInstanceNo() {
		return instanceNo;
	}
}
