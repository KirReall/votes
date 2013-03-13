package votes.client.oauth;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class User {
	private AuthManager authManager;
	private List<String> friendIds = new ArrayList<String>();

	public User(AuthManager authManager) {
		this.authManager = authManager;
		this.friendIds = getFriendsFromServer();
	}

	private List<String> getFriendsFromServer() {
		String url= URL.encode("https://api.vkontakte.ru/method/friends.get?uid="
				+ authManager.getCurrentUser() 
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
			         for (int i = 0; i < entries.length(); i++) {
			        	 Profile entry = entries.get(i);
			           Window.alert(entry.getFirstName() +
			                    " (" + entry.getLastName() + ")");
			         }
			       } 
			     });
//		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,url);
//		try {
//			Request response = builder.sendRequest(null, new RequestCallback() {
//				public void onError(Request request, Throwable exception) {
//					// Code omitted for clarity
//				}
//
//				public void onResponseReceived(Request request,
//						Response response) {
//					if (response.getStatusCode() == Response.SC_OK){
//						String text = response.getText();
//						Window.alert(text);
//					}
//					
//				}
//			});
//		} catch (RequestException e) {
//			// Code omitted for clarity
//		}

		return null;
	}
	
	 static class Profile extends JavaScriptObject {
		   protected Profile() {}

		   public final native String getId() /*-{
		     return this.uid;
		   }-*/;

		   public final native String getFirstName() /*-{
		     return this.first_name;
		   }-*/;

		   public final native String getLastName() /*-{
		     return this.last_name;
		   }-*/;
		   
		   public final native String getFrId() /*-{
		     return this;
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
		 
	
}
