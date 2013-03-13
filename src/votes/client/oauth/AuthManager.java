package votes.client.oauth;

import java.util.ArrayList;
import java.util.List;

import votes.client.PostService;
import votes.client.PostServiceAsync;
import votes.client.controlers.LoginControler;
import votes.shared.UserAccountInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AuthManager {
	private Auth auth;
	private AuthRequest request;
	private CurrentUser currentUser;
	private SocialAPI api;
	private final PostServiceAsync postService = GWT.create(PostService.class);
	private LoginControler loginControler;
	
	public AuthManager(){
		auth = Auth.get();
		//auth.clearAllTokens();//закоментить		
		currentUser = new CurrentUser(new UserAccountInfo());
	}
	
	public void loginVK(){
		api = new VKAPI();
		login();
	}
	
	public void loginGP() {
		api = new GPAPI();
		login();
	}
	
	public void loginFB() {
		api = new FBAPI();
		login();
	}
	
	public void logout(){
		auth.clearAllTokens();	
		currentUser = new CurrentUser(new UserAccountInfo());
		History.fireCurrentHistoryState();
	}
	
	public CurrentUser getCurrentUser() {
		if ((currentUser.getUserService().equals(UserAccountInfo.UNAUTHORIZED))||(isUserLoginIn()&&!(getCurrentUserID().equals(currentUser.getId())))){	
			loginCurrentUser();
		} 
		return currentUser;
	}
	
	public String getAccessToken(){
		request = api.getRequest();
		return auth.getCurrentToken(request);
		
	}
	
	public String getCurrentUserID(){
		request = api.getRequest();
		return auth.getCurrentUserId(request);		
	}
	
	
	private void login(){
		request = api.getRequest();
		auth.login(request, new Callback<String, Throwable>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error:\n" + caught.getMessage());
				
			}

			@Override
			public void onSuccess(String token) {
				//Window.alert("Acces token:\n" + token);
				loginCurrentUser();
			}
		});
	}


	protected void loginCurrentUser() {
		if (api == null){
			this.currentUser = new CurrentUser(new UserAccountInfo());
			return;
		}
		if (getAccessToken()==null){
			this.currentUser = new CurrentUser(new UserAccountInfo());
			return;
		}
		UserAccountInfo userAccount = new UserAccountInfo(getCurrentUserID(), api.getService());
		currentUser = new CurrentUser(userAccount);
		api.getProfileFromServer(this);
		
		
		
	}

	protected void updateCurrentUser(UserAccountInfo userAccount) {
		currentUser.setUserAccount(userAccount);
		api.getFriendsFromServer(this);
		
	}

	protected void setUserFirstName(String firstName) {
		currentUser.setFirstName(firstName);
		renewUserInfo();
	}

	protected void setUserLastName(String lastName) {
		currentUser.setLastName(lastName);
		renewUserInfo();
	}

	protected void setUserPhoto(String photo) {
		currentUser.setPhoto(photo);
		renewUserInfo();
	}

	private void renewUserInfo() {
		if(!((currentUser.getFirstName()==null)||(currentUser.getLastName()==null)||(currentUser.getPhoto()==null))) {
			postService.getUserAccount(currentUser.getUserAccount(), new AsyncCallback<UserAccountInfo>() {
				
				@Override
				public void onSuccess(UserAccountInfo result) {
					updateCurrentUser(result);
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
			loginControler.setUser(currentUser);
			History.fireCurrentHistoryState();
		}
		
	}

	protected void setUserFriends(final ArrayList<UserAccountInfo> friends) {
		
		postService.getFriendsUserAccounts(friends, new AsyncCallback<List<UserAccountInfo>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<UserAccountInfo> result) {
				renewFriendsAccounts(result, friends);
				
			}
		});
	}

	protected void renewFriendsAccounts(List<UserAccountInfo> result, ArrayList<UserAccountInfo> friends) {
		for (UserAccountInfo friend : friends) {
			UserAccountInfo userAccount;
			if (friend.getUid().equals(result.get(friends.indexOf(friend)).getUid())){
				userAccount = result.get(friends.indexOf(friend));
			} else {
				userAccount = findUserAccountWithUid(result, friend);
			}
			friend = userAccount;
		}
		currentUser.setFriends(friends);
	}

	private UserAccountInfo findUserAccountWithUid(
			List<UserAccountInfo> result, UserAccountInfo userAccountInfo) {
		for (UserAccountInfo userAccount : result) {
			if (userAccount.getUid().equals(userAccountInfo.getUid())){
				return userAccount;
			}
		}
		return userAccountInfo;
	}
	
	public boolean isUserLoginIn(){
		if (api==null) {
			api = new VKAPI();
			request = api.getRequest();
			if (auth.getCurrentToken(request)!=null){
				return true;
			} else {
				api = new GPAPI();
				request = api.getRequest();
				if (auth.getCurrentToken(request)!=null){
					return true;
				} else {
					api = new FBAPI();
					request = api.getRequest();
					if (auth.getCurrentToken(request)!=null){
						return true;
					} else {
						api = null;
						return false;
					}
				}
			}
				
		} else {
			request = api.getRequest();
			return auth.getCurrentToken(request)!=null;
		}
		
	}

	public void setCurrentUserId(String id) {
		currentUser.getUserAccount().setUid(id);
		request = api.getRequest();
		auth.setCurrentUserId(id, request);
		
	}

	public LoginControler getLoginControler() {
		return loginControler;
	}

	public void setLoginControler(LoginControler loginControler) {
		this.loginControler = loginControler;
	}

}
