package votes.client;


import java.util.List;

import votes.shared.AnswerTransported;
import votes.shared.PostTransported;
import votes.shared.UserAccountInfo;
import votes.shared.UserVoteTransported;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PostServiceAsync {

	void removePost(Long Id, AsyncCallback<Long> callback);

	void getPosts(Long page, String orderField, Boolean my,
			UserAccountInfo user, AsyncCallback<List<PostTransported>> callback);

	void increaseRateOfPost(Long Id, AsyncCallback<Void> callback);

	void decreaseRateOfPost(Long Id, AsyncCallback<Void> callback);

	void getMaxPost(AsyncCallback<Long> callback);

	void addPost(String content, List<String> answers, String embed, UserAccountInfo user,
			AsyncCallback<Void> callback);

	void increaseRateOfAnswer(Long Id, Long postId, UserAccountInfo user,
			AsyncCallback<Void> callback);

	void getPost(Long id, UserAccountInfo user,
			AsyncCallback<PostTransported> callback);

	void addCommentToPost(Long idofPost, String content, String author,
			UserAccountInfo user, AsyncCallback<Void> callback);

	void increaseRateOfComment(Long Id, AsyncCallback<Void> callback);

	void decreaseRateOfComment(Long Id, AsyncCallback<Void> callback);

	void setSpamComment(Long Id, AsyncCallback<Void> callback);

	void addOtherAnswer(Long idofPost, String content, UserAccountInfo user,
			AsyncCallback<Void> callback);

	void increaseRateOfOtherAnswer(Long Id, Long otherId, Long postId,
			Integer totalRating, UserAccountInfo user,
			AsyncCallback<Void> callback);

	void getOtherAnswers(Long Id, UserAccountInfo user,
			AsyncCallback<List<AnswerTransported>> callback);

	void getUserAccount(UserAccountInfo userAccount,
			AsyncCallback<UserAccountInfo> callback);

	void getUserVotes(Long postId, List<String> friendsUids, Integer Service,
			AsyncCallback<List<UserVoteTransported>> callback);

	void getFriendsUserAccounts(List<UserAccountInfo> friendsAccounts,
			AsyncCallback<List<UserAccountInfo>> callback);

	void getUserOtherVotes(Long postId, List<String> friendsUids,
			Integer Service, AsyncCallback<List<UserVoteTransported>> callback);

}
