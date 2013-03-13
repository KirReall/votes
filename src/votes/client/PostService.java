package votes.client;


import java.util.List;

import votes.shared.AnswerTransported;
import votes.shared.PostTransported;
import votes.shared.UserAccountInfo;
import votes.shared.UserVoteTransported;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("post")
public interface PostService extends RemoteService {
	void addPost(String content, List<String> answers, String embed,
			UserAccountInfo user);
	public Long removePost(Long Id);
	public List<PostTransported> getPosts(Long page, String orderField, Boolean my, UserAccountInfo user);
	public void increaseRateOfPost(Long Id);
	public void decreaseRateOfPost(Long Id);
	public void increaseRateOfAnswer(Long Id, Long postId, UserAccountInfo user);
	public void increaseRateOfComment(Long Id);
	public void decreaseRateOfComment(Long Id);
	public void setSpamComment(Long Id);
	public Long getMaxPost();
	public PostTransported getPost(Long id, UserAccountInfo user);
	public void addCommentToPost(Long idofPost, String content, String author, UserAccountInfo user);
	public void addOtherAnswer(Long idofPost, String content, UserAccountInfo user);
	public void increaseRateOfOtherAnswer(Long Id, Long otherId, Long postId, Integer totalRating, UserAccountInfo user);
	public List<AnswerTransported> getOtherAnswers(Long Id, UserAccountInfo user);
	public UserAccountInfo getUserAccount(UserAccountInfo userAccount);
	public List<UserVoteTransported> getUserVotes(Long postId, List<String> friendsUids, Integer Service);
	public List<UserVoteTransported> getUserOtherVotes(Long postId, List<String> friendsUids, Integer Service);
	public List<UserAccountInfo> getFriendsUserAccounts(List<UserAccountInfo> friendsAccounts);
}
