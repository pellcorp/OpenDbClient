package com.pellcorp.android.opendb;

import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class OpenDbClientException extends RuntimeException {
	private static final long serialVersionUID = -8504987330420192297L;

	public OpenDbClientException(JSONRPC2SessionException e) {
		super(e);
	}
}
