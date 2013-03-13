package votes.client.oauth;

import java.util.ArrayList;

import votes.shared.UserAccountInfo;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class VKAPI implements SocialAPI{
	private static final String VK_AUTH_URL = "http://api.vkontakte.ru/oauth/authorize";
	private static final String VK_CLIENT_ID = "2412721";
	private static final String VK_FRIENDS_SCOPE = "friends";
	private static Integer service = UserAccountInfo.VKUSER;

	public VKAPI() {
		
	}
	
	public void getProfileFromServer(final AuthManager authManager) {
		String url= URL.encode("https://api.vkontakte.ru/method/users.get?uids="
				+ authManager.getCurrentUserID() 
				+ "&fields=uid, first_name, last_name, photo"
				+ "&access_token="
				+ authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url,
			     new AsyncCallback<Response>() {
			       public void onFailure(Throwable throwable) {
			    	   Window.alert("Error: " + throwable);
			    	   
			       }

			       public void onSuccess(Response feed) {
			    	   //String text = feed.toString();
			    	   //Window.alert(text);
			    	// String ids = feed.getIds();
			    	   //CurrentUser currentUser = authManager.getCurrentUser();
			    	   JsArray<Profile> entries = feed.getEntries();
			    	   String firstName ="";
			    	   String lastName = "";
			    	   String photo = "";
			    	   for (int i = 0; i < entries.length(); i++) {
				        	 Profile entry = entries.get(i);
				        	 firstName = entry.getFirstName();
				        	 lastName = entry.getLastName();
				        	 photo = entry.getPhoto();
				         }
				         authManager.setUserFirstName(firstName);
				         authManager.setUserLastName(lastName);
				         authManager.setUserPhoto(photo);
			       } 
			     });
	}

	public void getFriendsFromServer(final AuthManager authManager) {
		String url= URL.encode("https://api.vkontakte.ru/method/friends.get?uid="
				+ authManager.getCurrentUserID() 
				+ "&fields=uid, first_name, last_name, photo"
				+ "&access_token="
				+ authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url,
			     new AsyncCallback<Response>() {
			       public void onFailure(Throwable throwable) {
			    	   Window.alert("Error: " + throwable);
			    	   
			       }

			       public void onSuccess(Response feed) {
			    	   //String text = feed.toString();
			    	   //Window.alert(text);
			    	// String ids = feed.getIds();
			    	  
			    	   JsArray<Profile> entries = feed.getEntries();
			    	   ArrayList<UserAccountInfo> friends = new  ArrayList<UserAccountInfo>();
			    	   for (int i = 0; i < entries.length(); i++) {
				        	 Profile entry = entries.get(i);
				        	 UserAccountInfo friend = new UserAccountInfo(entry.getId(), UserAccountInfo.VKUSER);
				        	 friend.setFirstName(entry.getFirstName());
				        	 friend.setLastName(entry.getLastName());
				        	 friend.setPhoto(entry.getPhoto());
				        	 friends.add(friend);
				        	 
				         }
				         authManager.setUserFriends(friends);
			    	 
			       } 
			     });

	}
	
	public void sendConvoToFriends(final AuthManager authManager, String frindId, String conversation, String postUrl) {
		String url= URL.encode("https://api.vkontakte.ru/method/wall.post?owner_id="
				+ frindId 
				+ "&message="
				+ conversation
				+ "&attachment="
				+ postUrl
				+ "&access_token="
				+ authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url,
			     new AsyncCallback<Response>() {
			       public void onFailure(Throwable throwable) {
			    	   Window.alert("Error: " + throwable);
			    	   
			       }

			       public void onSuccess(Response feed) {
			    	   //String text = feed.toString();
			    	   //Window.alert(text);
			    	// String ids = feed.getIds();
			    	  // CurrentUser currentUser = authManager.getCurrentUser();
			    	   JsArray<Profile> entries = feed.getEntries();
			    	   String postId ="";
			    	   
			    	   for (int i = 0; i < entries.length(); i++) {
				        	 Profile entry = entries.get(i);
				        	 postId = entry.getPostId();
				        	 
				         }
			    	   Window.alert(""+postId);
			       } 
			     });
	}
	
	 static class Profile extends JavaScriptObject {
		   protected Profile() {}

		   public final native String getId() /*-{
		     return this.uid.toString();
		   }-*/;

		   public final native String getFirstName() /*-{
		     return this.first_name;
		   }-*/;
		   
		   public final native String getLastName() /*-{
		     return this.last_name;
		   }-*/;
		   
		   public final native String getPhoto() /*-{
		     return this.photo;
		   }-*/;;
		   
		   public final native String getPostId() /*-{
		     return this.post_id;
		   }-*/;
//		   public final native String getEndTime() /*-{
//		     return this.gd$when ? this.gd$when[0].endTime : null;
//		   }-*/;
		 }

	 static class Response extends JavaScriptObject {
		   protected Response() {}

		   public final native JsArray<Profile> getEntries() /*-{
		     return this.response;
		   }-*/;
		   public final native String[] getIds() /*-{
		     return this.response;
		   }-*/;
		 }

	@Override
	public AuthRequest getRequest() {		
		return new AuthRequest(VK_AUTH_URL, VK_CLIENT_ID)
		.withScopes(VK_FRIENDS_SCOPE)
		.withScopeDelimiter(",");
	}

	@Override
	public Integer getService() {
		return service;
	}
		 
	
}
