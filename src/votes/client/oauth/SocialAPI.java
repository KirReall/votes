package votes.client.oauth;

public interface SocialAPI {
	
	public void getProfileFromServer(final AuthManager authManager);
	public void getFriendsFromServer(final AuthManager authManager);
	public void sendConvoToFriends(final AuthManager authManager, String frindId, String conversation, String postUrl);
	public AuthRequest getRequest();
	public Integer getService();
}
