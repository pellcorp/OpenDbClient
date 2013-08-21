package com.pellcorp.android.opendb;

import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class OpenDbClientException extends RuntimeException {
	public OpenDbClientException(JSONRPC2SessionException e) {
		super(e);
	}
}
