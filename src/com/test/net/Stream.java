package com.test.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import android.util.Log;

public class Stream {

	private HttpURLConnection urlConnection;
	private InputStream inputStream;
	
	public Stream(HttpURLConnection urlConnection){
		this.urlConnection = urlConnection;
	}
	
	/**
	 * Get the data from the URL.
	 * @return - The a stream with the data.
	 * @throws IOException
	 */
	public InputStream getStream() throws IOException{
		return urlConnection.getInputStream();
	}
	
	/**
	 * Download the file data from the URL stream.
	 * @return An input stream from URL.
	 * @throws IOException
	 */
	public InputStream downloadFile() throws IOException{
		long time = System.currentTimeMillis();

		inputStream = new BufferedInputStream(getStream());
		
		long completedIn = System.currentTimeMillis() - time;
		Log.e("InputStream", "time: " + completedIn);
		
//		byte data[] = new byte[1024];
//		do {} while (stream.read(data) != -1);
//		stream.close();
		return inputStream;
	}
	
	public String getData() throws IOException{
		String stream = "";
		String line;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(getStream()) );
		
		while ( (line = reader.readLine()) != null){
			stream += line;
		}
		
		reader.close();
		return stream;
	}
	
	/**
	 * Close the stream.
	 * @throws IOException
	 */
	public void close() throws IOException{
		inputStream.close();
	}

	
}
