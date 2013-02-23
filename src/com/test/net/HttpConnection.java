package com.test.net;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpConnection {

	private String path;
	private HttpURLConnection urlCon;
	
	public HttpConnection(String path){
		this.path = path;
	}
	
	/**
	 * Open the connection to the URL.
	 * If the result it«s OK CONNECTION will be true
	 * else will be false.
	 */
	public boolean openConnection() throws IOException {
		URL	url = new URL(path);
		urlCon =  (HttpURLConnection) url.openConnection();
	
		if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) 
			return true;
		else 
			return false;
	}
	
	/**
	 * Close the current connection.
	 */
	public void close() {
		urlCon.disconnect();
	}
	
	/**
	 * Get the object URL Connection.
	 * @return object URL Connection.
	 * @throws IOException
	 */
	public HttpURLConnection getConnection() throws IOException{
		return  urlCon;
	}
	
	/**
	 * Gets the URL.
	 * @return The URL.
	 */
	public String getUrl(){
		return path;
	}
	
	/**
	 * Send a json to the surver.
	 * @param json - The json.
	 * @return The web service response.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String postJSON(String json) throws ClientProtocolException, IOException {
		HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        
        HttpClient client = new DefaultHttpClient();  
        HttpPost post = new HttpPost(path); 
        post.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
        post.setHeader("json", json.toString());
        
        HttpResponse responsePOST = client.execute(post);  
        HttpEntity resEntity = responsePOST.getEntity();  
        
        return EntityUtils.toString(resEntity);
	}
	
	/**
	 * Send a post request to the given URL.
	 * @param params - The Parameters
	 * @return The response.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String post(List<NameValuePair> params) throws ClientProtocolException, IOException  {
		//String postURL = "http://10.0.2.2/curso/get_post.php"; This is the localhost url if you testing from a emulator.
        HttpClient client = new DefaultHttpClient();  
        HttpPost post = new HttpPost(path); 
        
        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
        post.setEntity(ent);
        HttpResponse responsePOST = client.execute(post);  
        HttpEntity resEntity = responsePOST.getEntity();  
        
        return EntityUtils.toString(resEntity);
	}
}
