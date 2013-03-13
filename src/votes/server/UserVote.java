package votes.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserVote {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
 
    @Persistent
    private Long postId;
    
    @Persistent
    private Long userId;
    
    @Persistent
    private Long answerId;
    
    @Persistent
    private Boolean other;

    public UserVote(){
    	
    }

    public UserVote(Long postId, Long userId, Long answerId){
    	this.postId = postId;
    	this.userId = userId;
    	this.answerId = answerId;
    	this.other = false;
    }
    
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
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
