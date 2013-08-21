package com.pellcorp.android.opendb;

import java.io.Serializable;

public class DownloadResult<Results> implements Serializable {
	private static final long serialVersionUID = -8445426032593156662L;
	
	private Results result;
	private boolean invalidCredentials;
	private String errorMessage;
	
	public DownloadResult(Results result) {
		this.result = result;
	}
	
	public DownloadResult(boolean invalidCredentials) {
		this.invalidCredentials = invalidCredentials;
	}
	
	public DownloadResult(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Results getResult() {
		return result;
	}

	public boolean isInvalidCredentials() {
		return invalidCredentials;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
