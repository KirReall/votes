package votes.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CommentToAnswer{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent 
    private String content;
    
    @Persistent
    private String author;
    
    @Persistent
    private Long authorId;
    
    @Persistent
    private Date dateOfCreate;
    
    @Persistent
    private Integer rate;
    
    @Persistent
    private Long idOfPost;
    
    @Persistent
    private Boolean spam;

    public CommentToAnswer(){
    	
    }

    public CommentToAnswer(String content, Long idOfPost) {
        this.content = content;
        this.setIdOfPost(idOfPost);
        this.dateOfCreate = new Date();
        this.rate = 0;
        this.spam = false;
    }
    
    public CommentToAnswer(String content, Long idOfPost, String author) {
        this.content = content;
        this.setIdOfPost(idOfPost);
        this.author = author;
        this.dateOfCreate = new Date();
        this.rate = 0;
        this.spam = false;
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
	
    public void setRate(Integer rate) {
		this.rate = rate;
	}

	public void setContent(String content) {
        this.content = content;
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

	public void setDateOfCreate(Date dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public Date getDateOfCreate() {
		return dateOfCreate;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setSpam(Boolean spam) {
		this.spam = spam;
	}

	public Boolean isSpam() {
		return spam;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
}
