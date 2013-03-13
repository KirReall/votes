package votes.shared;

import java.io.Serializable;

public class UserVoteTransported implements Serializable {
    private Long id;
    private Long postId;
    private UserAccountInfo user;
    private Long answerId;
    private Boolean other;

    public UserVoteTransported(){
    	
    }

    public UserVoteTransported(Long postId, UserAccountInfo user, Long answerId){
    	this.postId = postId;
    	this.user = user;
    	this.answerId = answerId;
    }
    
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setUserId(UserAccountInfo user) {
		this.user = user;
	}

	public UserAccountInfo getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setOther(Boolean other) {
		this.other = other;
	}

	public Boolean getOther() {
		return other;
	}
}
