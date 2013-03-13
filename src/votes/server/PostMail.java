package votes.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PostMail {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long postId;
    
    @Persistent
    private Long senderId;
    
    @Persistent
    private Long receiverId;
    
    @Persistent
    private Boolean read;
    
    public PostMail(){
    	
    }
    
    public PostMail(Long postId, Long senderId, Long receiverId, Boolean read){
    	this.postId = postId;
    	this.senderId = senderId;
    	this.receiverId = receiverId;
    	this.read = read;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getRead() {
		return read;
	}

}
