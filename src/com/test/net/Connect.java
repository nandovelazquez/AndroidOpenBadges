package com.test.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.test.models.Badge;
import com.test.models.Group;
import com.test.models.User;

public class Connect {

	public Connect() { }
	
	public String getJSONFrom(String url) throws IOException{
		String json;
		HttpConnection conn = new HttpConnection(url);
		conn.openConnection();
		
		Stream stream = new Stream(conn.getConnection());
		json = stream.getData();
		
//		Log.e("JSON", "response: " + json);
		
		return json;
	}
	
	public String getJSONUsingPost(String url, List<NameValuePair> params) throws IOException{
		HttpConnection conn = new HttpConnection(url);
		conn.openConnection();
 
		String response = conn.post(params);
		
		conn.close();
		return response;
	}

	public User logIn(String url, List<NameValuePair> params) throws IOException{
		
		String json = getJSONUsingPost(url, params);
		ByteArrayInputStream bai = new ByteArrayInputStream(json.getBytes());
		JsonReader reader = new JsonReader( new InputStreamReader(bai, "UTF-8"));

		reader.beginObject();

		User user = new User();
		
		while (reader.hasNext()) {
			String name = reader.nextName();
	    	if (name.equals("userId"))
	    		user.setUserId(reader.nextInt());
	    	else if (name.equals("email"))
	    		user.setEmail(reader.nextString());
	    	else
	    		reader.skipValue();
		}	
		
		reader.endObject();
		reader.close();

		return user;
	}
	
	public List<Badge> getBadges(String url) throws IOException {
		List<Badge> list = null;
		String response = getJSONFrom(url);
	
		if (!response.contains("[]")) {
			list = new ArrayList<Badge>();;
			JsonParser parser = new JsonParser();
			JsonObject result = parser.parse(response).getAsJsonObject();
			JsonArray badgesArray = result.getAsJsonArray("badges");
			
			for (JsonElement element: badgesArray)
				list.add(readBadge(element));
			
		} 
		
		return list;
	}

	public List<Group> getGroups(String url) throws IOException {
		List<Group> list = null;
		String response = getJSONFrom(url);
	
		if (!response.contains("[]")) {
			ByteArrayInputStream bai = new ByteArrayInputStream(response.getBytes());
			JsonReader reader = new JsonReader( new InputStreamReader(bai, "UTF-8"));
			list = new ArrayList<Group>();
			reader.beginObject();
			
			while (reader.hasNext()) {
				Group group = null;
				String name = reader.nextName();
				
				if (name.equals("groups")) {
					group = readGroup(reader);
					list.add(group);
				} else
					reader.skipValue();
				
			}
			reader.endObject();
			reader.close();
		} 
		
		return list;
	}
	
	private Badge readBadge(JsonElement element) throws IOException {
		JsonObject jAssertion = (JsonObject) element.getAsJsonObject().get("assertion");
		JsonObject jBadge = (JsonObject) jAssertion.getAsJsonObject().get("badge");
		
		String badgeName = jBadge.getAsJsonObject().get("name").getAsString();
		String badgeDescription = jBadge.getAsJsonObject().get("description").getAsString();
		String imagUrl = element.getAsJsonObject().get("imageUrl").getAsString();
		
		Badge badge = new Badge();
		badge.setName(badgeName);
		badge.setDescription(badgeDescription);
		badge.setImageUrl(imagUrl);
		return badge;
	}
	
	private Group readGroup(JsonReader reader) throws IOException {
		Gson gson = new GsonBuilder().create();
		reader.beginArray();
		Group group = gson.fromJson(reader, Group.class);
		reader.endArray();
		
		return group;
	}

}
