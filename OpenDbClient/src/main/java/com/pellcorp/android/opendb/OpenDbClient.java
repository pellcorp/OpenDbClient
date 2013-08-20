package com.pellcorp.android.opendb;

import java.util.List;
import java.util.Map;

public interface OpenDbClient {
	List<Map<String, Object>> send(String method, Map<String, Object> params) throws OpenDbClientException;
}
