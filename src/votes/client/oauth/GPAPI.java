package votes.client.oauth;

import java.util.ArrayList;

import votes.shared.UserAccountInfo;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GPAPI implements SocialAPI {
	private static final String GP_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	private static final String GP_CLIENT_ID = "614557241442.apps.googleusercontent.com";
	private static final String GP_ME_SCOPE = "https://www.googleapis.com/auth/plus.me";
	private static final String GP_LOGIN_SCOPE = "https://www.googleapis.com/auth/plus.login";
	// private static final String GP_PROFILE_SCOPE =
	// "https://www.googleapis.com/auth/userinfo.profile&state=profile";
	private static Integer service = UserAccountInfo.GPLUSUSER;
	private final ArrayList<UserAccountInfo> friends = new ArrayList<UserAccountInfo>();

	public GPAPI() {

	}

	public void getProfileFromServer(final AuthManager authManager) {
		String url = URL.encode("https://www.googleapis.com/plus/v1/people/me?"
				+ "fields=displayName,id,image,name(familyName,givenName)"
				+ "&pp=1" + "&access_token=" + authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url, new AsyncCallback<Profile>() {
			public void onFailure(Throwable throwable) {
				Window.alert("Error: " + throwable);

			}

			public void onSuccess(Profile entry) {
				// String text = feed.toString();
				// Window.alert(text);
				// String ids = feed.getIds();
				// CurrentUser currentUser = authManager.getCurrentUser();
				// JsArray<Profile> entries = feed.getEntries();
				String id = "";
				String firstName = "";
				String lastName = "";
				String photo = "";
				// for (int i = 0; i < entries.length(); i++) {
				// Profile entry = entries.get(i);
				id = entry.getId();
				firstName = entry.getFirstName();
				lastName = entry.getLastName();
				photo = entry.getPhoto();
				// }

				authManager.setCurrentUserId(id);
				authManager.setUserFirstName(firstName);
				authManager.setUserLastName(lastName);
				authManager.setUserPhoto(photo);
			}
		});
	}

	public void getFriendsFromServer(AuthManager authManager) {
		getFriendsFromServer(authManager, "");
	}

	private void getFriendsFromServer(final AuthManager authManager,
			String pageToken) {
		String url = URL
				.encode("https://www.googleapis.com/plus/v1/people/me/people/visible?"
						+ "pageToken="
						+ pageToken
						+ "&fields==items(displayName,id,image,name(familyName,givenName)),nextPageToken,totalItems"
						+ "&pp=1"
						+ "&access_token="
						+ authManager.getAccessToken());
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url, new AsyncCallback<Response>() {
			public void onFailure(Throwable throwable) {
				Window.alert("Error: " + throwable);

			}

			public void onSuccess(Response feed) {
				// String text = feed.toString();
				// Window.alert(text);
				// String ids = feed.getIds();

				JsArray<Profile> entries = feed.getEntries();

				for (int i = 0; i < entries.length(); i++) {
					Profile entry = entries.get(i);
					UserAccountInfo friend = new UserAccountInfo(entry.getId(),
							UserAccountInfo.GPLUSUSER);
					String firstName = entry.getFirstName();
					String lastName = entry.getLastName();
					if (firstName == null) {
						String displayName = entry.getDisplayName();
						lastName = displayName.split(" ", 2)[1];
						firstName = displayName.split(" ", 2)[0];
					}
					friend.setFirstName(firstName);
					friend.setLastName(lastName);
					friend.setPhoto(entry.getPhoto());
					friends.add(friend);

				}
				String nextPageToken = feed.getNextPageToken();
				if (nextPageToken == null) {
					authManager.setUserFriends(friends);
				} else {
					getFriendsFromServer(authManager, nextPageToken);
				}

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
			return this.id.toString();
		}-*/;

		public final native String getDisplayName() /*-{
			return this.displayName;
		}-*/;

		public final native String getFirstName() /*-{
			return this.name.givenName;
		}-*/;

		public final native String getLastName() /*-{
			return this.name.familyName;
		}-*/;

		public final native String getPhoto() /*-{
			return this.image.url;
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
			return this.items;
		}-*/;

		public final native String getNextPageToken() /*-{
			return this.nextPageToken;
		}-*/;
	}

	@Override
	public AuthRequest getRequest() {
		return new AuthRequest(GP_AUTH_URL, GP_CLIENT_ID).withScopes(
				GP_ME_SCOPE, GP_LOGIN_SCOPE).withScopeDelimiter("+");
	}

	@Override
	public Integer getService() {
		return service;
	}

}
