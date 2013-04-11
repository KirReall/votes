package votes.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import votes.client.event.AddCommentEvent;
import votes.client.event.AddCommentEventHandler;
import votes.client.event.AddOtherAnswerEvent;
import votes.client.event.AddOtherAnswerEventHandler;
import votes.client.event.AddPostEvent;
import votes.client.event.AddPostEventHandler;
import votes.client.event.ChangeRateOfAnswerEvent;
import votes.client.event.ChangeRateOfAnswerEventHandler;
import votes.client.event.ChangeRateOfCommentEvent;
import votes.client.event.ChangeRateOfCommentEventHandler;
import votes.client.event.ChangeRateOfOtherAnswerEvent;
import votes.client.event.ChangeRateOfOtherAnswerEventHandler;
import votes.client.event.ChangeRateOfPostEvent;
import votes.client.event.ChangeRateOfPostEventHandler;
import votes.client.event.GetFriendsAnswersEvent;
import votes.client.event.GetFriendsAnswersEventHandler;
import votes.client.event.GetOtherAnswersEvent;
import votes.client.event.GetOtherAnswersEventHandler;
import votes.client.event.GetOtherFriendsAnswersEvent;
import votes.client.event.GetOtherFriendsAnswersEventHandler;
import votes.client.event.GetPostEvent;
import votes.client.event.GetPostEventHandler;
import votes.client.event.GetPostsEvent;
import votes.client.event.GetPostsEventHandler;
import votes.client.event.IsUserLoginEvent;
import votes.client.event.IsUserLoginEventHandler;
import votes.client.event.LoginEvent;
import votes.client.event.LoginEventHandler;
import votes.client.event.LogoutEvent;
import votes.client.event.LogoutEventHandler;
import votes.client.event.SetSpamCommentEvent;
import votes.client.event.SetSpamCommentEventHandler;
import votes.client.oauth.AuthManager;
import votes.client.oauth.CurrentUser;
import votes.client.views.AddPostView;
import votes.client.views.AnswerSelectorView;
import votes.client.views.ListOfPostView;
import votes.client.views.PostAndCommentsView;
import votes.shared.AnswerTransported;
import votes.shared.PostTransported;
import votes.shared.UserAccountInfo;
import votes.shared.UserVoteTransported;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EventBusBinder {
	private final HandlerManager eventBus;
	private final AuthManager authManager;
	private final PostServiceAsync postService = GWT.create(PostService.class);
	private Boolean bindedCommon = false;
	private Boolean bindedListOfPost = false;
	private Boolean bindedPostAndComments = false;
	private Boolean bindedAddPost = false;

	public EventBusBinder(HandlerManager eventBus, AuthManager authManager) {
		this.eventBus = eventBus;
		this.authManager = authManager; 
	}

	public void bindCommonEventBus(){
		if (!bindedCommon){
			innerBindCommonEventBus();
		}
	}
	
	public void bindListOfPostEventBus(){
		if (!bindedListOfPost){
			innerBindListOfPostEventBus();
		}
	}
	
	public void bindAddPostEventBus(){
		if (!bindedAddPost){
			innerBindAddPostEventBus();
		}
	}
	
	public void bindPostAndCommentsEventBus() {
		if (!bindedPostAndComments){
			innerBindPostAndCommentsEventBus();
		}
	}
	
	private void innerBindCommonEventBus(){
		bindedCommon = true;
		
		eventBus.addHandler(GetFriendsAnswersEvent.TYPE, new GetFriendsAnswersEventHandler() {
			
			@Override
			public void onGetFriendsAnswers(GetFriendsAnswersEvent event) {
				getFriendsAnswers(event.getId(),event.getView(), false);
				
			}
		});
		
		eventBus.addHandler(GetOtherFriendsAnswersEvent.TYPE, new GetOtherFriendsAnswersEventHandler() {
			
			@Override
			public void onGetFriendsAnswers(GetOtherFriendsAnswersEvent event) {
				getFriendsAnswers(event.getId(),event.getView(), true);
				
			}
		});
		
		eventBus.addHandler(AddOtherAnswerEvent.TYPE,
				new AddOtherAnswerEventHandler() {

					@Override
					public void onAddOtherAnswer(AddOtherAnswerEvent event) {
						addOtherAnswer(event.getContent(), event.getPostId());

					}
				});

		eventBus.addHandler(GetOtherAnswersEvent.TYPE,
				new GetOtherAnswersEventHandler() {

					@Override
					public void onGetOtherAnswers(GetOtherAnswersEvent event) {
						getOtherAnswers(event.getId(), event.getView());
					}
				});
		
		eventBus.addHandler(ChangeRateOfPostEvent.TYPE,
				new ChangeRateOfPostEventHandler() {

					@Override
					public void onChangeRateContact(ChangeRateOfPostEvent event) {
						if (event.isIncrease()) {
							increaseRateOfPost(event.getId());
						} else {
							decreaseRateOfPost(event.getId());
						}

					}
				});

		eventBus.addHandler(ChangeRateOfOtherAnswerEvent.TYPE,
				new ChangeRateOfOtherAnswerEventHandler() {

					@Override
					public void onChangeRateOtherAnswer(
							ChangeRateOfOtherAnswerEvent event) {
						increaseRateOfOtherAnswer(event.getId(),event.getOtherId(), event.getPostId(),event.getTotalRating());

					}
				});

		eventBus.addHandler(ChangeRateOfAnswerEvent.TYPE,
				new ChangeRateOfAnswerEventHandler() {

					@Override
					public void onChangeRateContact(
							ChangeRateOfAnswerEvent event) {
						increaseRateOfAnswer(event.getId(), event.getPostId());

					}
				});
	}
	
	public void bindLoginEventBus(){
		
		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			
			@Override
			public void onLogin(LoginEvent event) {
				if (event.getService().equals(UserAccountInfo.VKUSER)) {
					authManager.loginVK();
				} else if (event.getService().equals(UserAccountInfo.GPLUSUSER)){
					authManager.loginGP();
				} else if (event.getService().equals(UserAccountInfo.FBUSER)){
					authManager.loginFB();
				}
				
				
			}
		});
		
		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			
			@Override
			public void onLogout(LogoutEvent event) {
				authManager.logout();				
			}
		});
	}
	
	private void innerBindListOfPostEventBus(){
		bindedListOfPost = true;
		eventBus.addHandler(GetPostsEvent.TYPE, new GetPostsEventHandler() {
			
			@Override
			public void onGetPosts(GetPostsEvent event) {
				getPosts(event.getcurrentPage(), event.getOrderField(), event.getMy(), event.getView());
				
			}
		});
	}
	
	private void innerBindAddPostEventBus(){
		bindedAddPost = true;
		eventBus.addHandler(AddPostEvent.TYPE, new AddPostEventHandler() {

			@Override
			public void onAddContact(AddPostEvent event) {
				addingPost(event.getContent(), event.getAnswers(), event.getEmbed());
			}

		});
		
		eventBus.addHandler(IsUserLoginEvent.TYPE, new IsUserLoginEventHandler() {

			@Override
			public void onUserLoginRequest(IsUserLoginEvent event) {
				requestUserLogin(event.getView());
			}

		});
	}
	

	private void innerBindPostAndCommentsEventBus() {
		bindedPostAndComments = true;
		eventBus.addHandler(GetPostEvent.TYPE, new GetPostEventHandler() {
			
			@Override
			public void onGetPost(GetPostEvent event) {
				getPost(event.getpostId(), event.getView());
			}
		});
		
		eventBus.addHandler(SetSpamCommentEvent.TYPE,
				new SetSpamCommentEventHandler() {

					@Override
					public void onSetSpamComment(SetSpamCommentEvent event) {
						setSpamComment(event.getId());

					}
				});	
		

		eventBus.addHandler(AddCommentEvent.TYPE, new AddCommentEventHandler() {

			@Override
			public void onAddContact(AddCommentEvent event) {
				addComment(event.getContent(), event.getPostId(),
						event.getAuthor());

			}
		});

		eventBus.addHandler(ChangeRateOfCommentEvent.TYPE,
				new ChangeRateOfCommentEventHandler() {

					@Override
					public void onChangeRateContact(
							ChangeRateOfCommentEvent event) {
						if (event.isIncrease()) {
							increaseRateOfComment(event.getId());
						} else {
							decreaseRateOfComment(event.getId());
						}
					}
				});

	}
	
	protected void requestUserLogin(AddPostView view) {
		if (authManager.getCurrentUser().getUserService()!=UserAccountInfo.UNAUTHORIZED){
			view.setCaptcha(false);
		} else {
			view.setCaptcha(true);
		}		
	}

	protected void getPost(Long postId, final PostAndCommentsView view) {
		UserAccountInfo user = authManager.getCurrentUser().getUserAccount();
		postService.getPost(postId, user, new AsyncCallback<PostTransported>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(PostTransported result) {
				view.setPost(result);
				view.setEventHandlers(eventBus);

			}
		});
		if (authManager.getCurrentUser().getUserService()!=UserAccountInfo.UNAUTHORIZED){
			view.disableAuthor();
		}
	}

	protected void getPosts(Long currentPage, String orderField, Boolean my,
			final ListOfPostView view) {
		view.setLoadImg();
		UserAccountInfo user = authManager.getCurrentUser().getUserAccount();
		postService.getPosts(currentPage, orderField, my, user, new AsyncCallback<List<PostTransported>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<PostTransported> result) {
				view.setRowData((PostTransported[])result.toArray(new PostTransported[0]), eventBus);
				
			}
		});
		
	}

	protected void getFriendsAnswers(Long postId, final AnswerSelectorView view, Boolean other) {
		CurrentUser currentUser = authManager.getCurrentUser();
		if (!(currentUser.getUserService()==UserAccountInfo.UNAUTHORIZED)){
			if (!other){
				postService.getUserVotes(postId, currentUser.getFriendsUids(), currentUser.getUserService(), new AsyncCallback<List<UserVoteTransported>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
	
					}
	
					@Override
					public void onSuccess(List<UserVoteTransported> result) {
						HashMap<Long, ArrayList<UserAccountInfo>> friendsAnswers = getFriendsAnswers(result);					
						view.setFriendsAnswers(friendsAnswers);
	
					}
				});
			} else {
				postService.getUserOtherVotes(postId, currentUser.getFriendsUids(), currentUser.getUserService(), new AsyncCallback<List<UserVoteTransported>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
	
					}
	
					@Override
					public void onSuccess(List<UserVoteTransported> result) {
						HashMap<Long, ArrayList<UserAccountInfo>> friendsAnswers = getFriendsAnswers(result);
						view.setFriendsAnswers(friendsAnswers);
	
					}
				});
			}
		}
	}

	protected HashMap<Long, ArrayList<UserAccountInfo>> getFriendsAnswers(
			List<UserVoteTransported> result) {
		//CurrentUser currentUser = authManager.getCurrentUser();
		HashMap<Long, ArrayList<UserAccountInfo>> friendsAnswers = new HashMap<Long, ArrayList<UserAccountInfo>>();
		for (UserVoteTransported userVoteTransported : result) {
			Long answerId=userVoteTransported.getAnswerId();
			ArrayList<UserAccountInfo> friends;
			if (friendsAnswers.containsKey(answerId)) {
				friends = friendsAnswers.get(answerId);
			} else {
				friends = new ArrayList<UserAccountInfo>();
			}
			UserAccountInfo friend = userVoteTransported.getUser();
			if (!(friend.equals(null))) {
				friends.add(friend);
				friendsAnswers.put(answerId, friends);
			}
			
		}
		return friendsAnswers;
	}

	protected void addOtherAnswer(String content, Long postId) {
		content = SimpleHtmlSanitizer.sanitizeHtml(content).asString();
		postService.addOtherAnswer(postId, content, authManager.getCurrentUser().getUserAccount(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}
		});

	}

	protected void getOtherAnswers(Long id, final AnswerSelectorView view) {
		UserAccountInfo user = authManager.getCurrentUser().getUserAccount();
		postService.getOtherAnswers(id, user,
				new AsyncCallback<List<AnswerTransported>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<AnswerTransported> result) {
						view.addOtherAnswers(result, eventBus);
						// view.setEventHandlers(eventBus);
					}
				});
	}

	protected void setSpamComment(Long id) {
		postService.setSpamComment(id, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
			}
		});

	}

	protected void decreaseRateOfComment(Long id) {
		postService.decreaseRateOfComment(id, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}
		});

	}

	protected void increaseRateOfComment(Long id) {
		postService.increaseRateOfComment(id, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}
		});

	}

	protected void addComment(String content, final Long idOfPost, String author) {
		content = SimpleHtmlSanitizer.sanitizeHtml(content).asString();
		author = SimpleHtmlSanitizer.sanitizeHtml(author).asString();
		UserAccountInfo user = authManager.getCurrentUser().getUserAccount();
		postService.addCommentToPost(idOfPost, content, author, user,
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						History.fireCurrentHistoryState();
						// History.newItem("post&"+idOfPost.toString());
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						History.newItem("fail");
					}
				});

	}

	protected void increaseRateOfOtherAnswer(Long id, Long otherId, Long postId, Integer totalRating) {

		
		
		postService.increaseRateOfOtherAnswer(id, otherId, postId, totalRating, authManager.getCurrentUser().getUserAccount(), new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});

	}

	protected void increaseRateOfAnswer(Long id, Long postId) {
		postService.increaseRateOfAnswer(id, postId, authManager.getCurrentUser().getUserAccount(), new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});

	}

	protected void decreaseRateOfPost(Long id) {
		postService.decreaseRateOfPost(id, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});

	}

	protected void increaseRateOfPost(Long id) {
		postService.increaseRateOfPost(id, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});

	}

	private void addingPost(String content, List<String> answers, String embed) {
		content = SimpleHtmlSanitizer.sanitizeHtml(content).asString();
		for (String answer : answers) {
			answer = SimpleHtmlSanitizer.sanitizeHtml(answer).asString();
		}
		postService.addPost(content, answers, embed, authManager.getCurrentUser().getUserAccount(), new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

				History.newItem("list&1");
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				History.newItem("fail");
			}
		});

	}

}
