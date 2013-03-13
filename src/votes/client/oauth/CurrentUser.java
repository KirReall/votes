package votes.client.oauth;

import java.util.ArrayList;
import votes.shared.UserAccountInfo;

public class CurrentUser {
	//private String id;
//	private String firstName;
//	private String lastName;
//	private String photo;
	private ArrayList<UserAccountInfo> friends;
	//private Integer userType;
	private UserAccountInfo userAccount;
	
	public static Integer VKUSER = 1;

	public CurrentUser(UserAccountInfo userAccount){
		this.setUserAccount(userAccount);
		//this.userType = userType;
	}
	
	public String getId(){
		return userAccount.getUid();
	}
	
	public Integer getUserService(){
		return userAccount.getService();
	}
		
	public void setFirstName(String firstName) {
		userAccount.setFirstName(firstName);
	}

	public String getFirstName() {
		return userAccount.getFirstName();
	}

	public void setLastName(String lastName) {
		userAccount.setLastName(lastName);
	}

	public String getLastName() {
		return userAccount.getLastName();
	}

	public void setFriends(ArrayList<UserAccountInfo> friends) {
		this.friends = friends;
	}

	public ArrayList<UserAccountInfo> getFriends() {
		return friends;
	}
	
	public ArrayList<String> getFriendsUids() {
		ArrayList<String> friendsUids = new ArrayList<String>();
		for (UserAccountInfo friend : friends) {
			friendsUids.add(friend.getUid());
		}
		return friendsUids;
	}
	
	public void setPhoto(String photo) {
		userAccount.setPhoto(photo);
	}

	public String getPhoto() {
		return userAccount.getPhoto();
	}

	public void setUserAccount(UserAccountInfo userAccount) {
		this.userAccount = userAccount;
	}

	public UserAccountInfo getUserAccount() {
		return userAccount;
	}

	public UserAccountInfo getFriendByUserId(Long friendId) {
		for (UserAccountInfo friend : friends) {
			if (friend.isUser()&&friend.getId().equals(friendId)) {
				return friend;
			}
		}
		return null;
	}

}
