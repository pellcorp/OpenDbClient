package com.pellcorp.android.opendb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OpenDbClientImplTest extends Assert {
	private OpenDbClient client;
	
	@Before
	public void setup() throws Exception {
		client = new OpenDbClientImpl("http://localhost/opendb", "test", "test");
	}
	
	@Test
	public void testLoadResults() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", "%");
		List<Map<String, Object>> results = client.send("itemsearch.titleSearch", params);
		
		for (Map<String, Object> result : results) {
			System.out.println("---- new row -----");
			for (Map.Entry<String, Object> entry : result.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		}
	}
	
	@Test
	public void testItemSearch() throws Exception {
		ItemSearch itemSearch = new ItemSearch(client);
		ItemSearchResults results = itemSearch.titleSearch("%");
		assertTrue(results.getItemList().size() > 2);
	}
}
