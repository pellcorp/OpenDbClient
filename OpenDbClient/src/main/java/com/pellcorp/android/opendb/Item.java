package com.pellcorp.android.opendb;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 7511520671674740062L;
	
	private final String type;
	private final String title;
	private final int itemId;
	private final int instanceNo;
	
	public Item(String type, int itemId, int instanceNo, String title) {
		this.type = type;
		this.itemId = itemId;
		this.instanceNo = instanceNo;
		this.title = title;
	}

	public String getType() {
		return type;
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
