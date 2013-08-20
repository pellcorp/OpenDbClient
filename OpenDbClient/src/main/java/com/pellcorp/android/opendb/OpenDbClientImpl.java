package com.pellcorp.android.opendb;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.binary.Base64;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.ConnectionConfigurator;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class OpenDbClientImpl implements OpenDbClient {
	private final AtomicLong requestIdGen = new AtomicLong();
	private final JSONRPC2Session session;

	public OpenDbClientImpl(String url, final String username, final String password)
			throws MalformedURLException {
		this.session = new JSONRPC2Session(new URL(url + "/jsonrpc.php"));
		session.setConnectionConfigurator(new ConnectionConfigurator() {
			@Override
			public void configure(HttpURLConnection connection) {
				try {
					String encoded = new String(
							Base64.encodeBase64((username+":"+password).getBytes("UTF-8")), "UTF-8");
					connection.setRequestProperty("Authorization", "Basic "+encoded);
				} catch (UnsupportedEncodingException e) {
				} 
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> send(String method, Map<String, Object> params)
			throws OpenDbClientException {
		String requestId = requestIdGen.incrementAndGet() + "";
		JSONRPC2Request request = new JSONRPC2Request(method, requestId);
		request.setNamedParams(params);

		try {
			JSONRPC2Response response = session.send(request);
			return (List<Map<String, Object>>) response.getResult();
		} catch (JSONRPC2SessionException e) {
			throw new OpenDbClientException(e);
		}
	}
}
