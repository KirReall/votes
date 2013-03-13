package votes.client.oauth;

import votes.shared.UserAccountInfo;

public class Friend {
	private String firstName;
	private String lastName;
	private String photo;
	private UserAccountInfo userAccount;
	
	public Friend(){
		
	}
	
	public Friend(String uid, Integer service){
		this.setUserAccount(new UserAccountInfo(uid, service));
		
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}
	
	public String getUid(){
		return userAccount.getUid();
	}

	public void setUserAccount(UserAccountInfo userAccount) {
		this.userAccount = userAccount;
	}

	public UserAccountInfo getUserAccount() {
		return userAccount;
	}
	
	public Boolean isItUserAccountId(Long id){
		return userAccount.isUser()&&userAccount.getId().equals(id);		
	}
}
