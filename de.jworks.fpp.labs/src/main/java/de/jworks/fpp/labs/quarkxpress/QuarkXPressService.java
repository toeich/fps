package de.jworks.fpp.labs.quarkxpress;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class QuarkXPressService {

	public static void main(String[] args) throws Exception {
		HttpClient httpClient = HttpClients.createDefault();
		HttpHost httpHost = new HttpHost("dtp-server", 9090);
		HttpRequest httpRequest = new HttpPost("/getserverinfo");
		HttpResponse httpResponse = httpClient.execute(httpHost, httpRequest);
		String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		
		System.out.println(result);
	}
	
}
