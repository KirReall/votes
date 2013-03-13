package votes.client.oauth;

import java.util.ArrayList;

import votes.shared.UserAccountInfo;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FBAPI implements SocialAPI {
	private static final String FB_AUTH_URL = "https://www.facebook.com/dialog/oauth";
	private static final String FB_CLIENT_ID = "288437194568988";
	private static final String FB_ME_SCOPE = "user_about_me";
	private static final String FB_FRIENDS_SCOPE = "friends_about_me";
	private static Integer service = UserAccountInfo.FBUSER;

	public FBAPI() {

	}

	public void getProfileFromServer(final AuthManager authManager) {
		String url = URL
				.encode("https://graph.facebook.com/fql?q="
						+ "SELECT uid, first_name, last_name, pic_small FROM  user WHERE uid = me()"
						+ "&access_token=" + authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url, new AsyncCallback<Response>() {
			public void onFailure(Throwable throwable) {
				Window.alert("Error: " + throwable);

			}

			public void onSuccess(Response feed) {
				JsArray<Profile> entries = feed.getEntries();
				String id = "";
				String firstName = "";
				String lastName = "";
				String photo = "";
				for (int i = 0; i < entries.length(); i++) {
					Profile entry = entries.get(i);
					id = entry.getId();
					firstName = entry.getFirstName();
					lastName = entry.getLastName();
					photo = entry.getPhoto();
				}
				authManager.setCurrentUserId(id);
				authManager.setUserFirstName(firstName);
				authManager.setUserLastName(lastName);
				authManager.setUserPhoto(photo);
			}
		});
	}

	public void getFriendsFromServer(final AuthManager authManager) {
		String url = URL
				.encode("https://graph.facebook.com/fql?q="
						+ "SELECT uid, first_name, last_name, pic_small FROM  user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me())"
						+ "&access_token=" + authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url, new AsyncCallback<Response>() {
			public void onFailure(Throwable throwable) {
				Window.alert("Error: " + throwable);

			}

			public void onSuccess(Response feed) {
				JsArray<Profile> entries = feed.getEntries();
				ArrayList<UserAccountInfo> friends = new ArrayList<UserAccountInfo>();
				for (int i = 0; i < entries.length(); i++) {
					Profile entry = entries.get(i);
					UserAccountInfo friend = new UserAccountInfo(entry.getId(),
							UserAccountInfo.FBUSER);
					friend.setFirstName(entry.getFirstName());
					friend.setLastName(entry.getLastName());
					friend.setPhoto(entry.getPhoto());
					friends.add(friend);

				}
				authManager.setUserFriends(friends);

			}
		});

	}

	public void sendConvoToFriends(final AuthManager authManager,
			String frindId, String conversation, String postUrl) {

	}

	static class Profile extends JavaScriptObject {
		protected Profile() {
		}

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
			return this.pic_small;
		}-*/;;

		// public final native String getPostId() /*-{
		// return this.post_id;
		// }-*/;
		// public final native String getEndTime() /*-{
		// return this.gd$when ? this.gd$when[0].endTime : null;
		// }-*/;
	}

	static class Response extends JavaScriptObject {
		protected Response() {
		}

		public final native JsArray<Profile> getEntries() /*-{
			return this.data;
		}-*/;

		public final native String[] getIds() /*-{
			return this.data;
		}-*/;
	}

	@Override
	public AuthRequest getRequest() {
		return new AuthRequest(FB_AUTH_URL, FB_CLIENT_ID).withScopes(
				FB_ME_SCOPE, FB_FRIENDS_SCOPE).withScopeDelimiter(",");
	}

	@Override
	public Integer getService() {
		return service;
	}

}
