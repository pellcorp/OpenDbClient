package com.pellcorp.android.opendb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSearch {
	private static final String METHOD = "itemsearch.titleSearch";
	public static final String TITLE_PARAM = "title";
	
	private static final String RESULT_TYPE = "s_item_type";
	private static final String RESULT_TITLE = "title";
	private static final String RESULT_ITEM_ID = "item_id";
	private static final String RESULT_INSTANCE_NO = "instance_no";
	
	private final OpenDbClient client;
	
	public ItemSearch(OpenDbClient client) {
		this.client = client;
	}
	
	public ItemSearchResults titleSearch(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(TITLE_PARAM, title);
		List<Map<String, Object>> results = client.send(METHOD, params);
		
		ItemSearchResults resultObj = new ItemSearchResults();
		
		for (Map<String, Object> result : results) {
			Item item = new Item(
					(String) result.get(RESULT_TYPE),
					Integer.parseInt((String) result.get(RESULT_ITEM_ID)),
					Integer.parseInt((String) result.get(RESULT_INSTANCE_NO)),
					(String) result.get(RESULT_TITLE));
			
			resultObj.getItemList().add(item);
		}
		
		return resultObj;
	}
}
