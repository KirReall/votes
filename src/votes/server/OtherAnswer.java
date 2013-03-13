package votes.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class OtherAnswer{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String content;
    
    @Persistent
    private Integer rate;
    
    @Persistent
    private Long idOfPost;

    public OtherAnswer(){
    	
    }

    public OtherAnswer(Long idofPost, String content) {
        this.content = content;
        this.idOfPost = idofPost;
        this.rate = 0;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

	public Integer getRate() {
		return rate;
	}
	
    public void setContent(String content) {
        this.content = content;
    }

    public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	public void increaseRate() {
		if (rate == null) {
			this.rate = 0;
		}
		this.rate ++;
	}
	
	public void decreaseRate() {
		this.rate --;
	}

	public void setIdOfPost(Long idOfPost) {
		this.idOfPost = idOfPost;
	}

	public Long getIdOfPost() {
		return idOfPost;
	}
}
