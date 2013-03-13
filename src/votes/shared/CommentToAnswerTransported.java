package votes.shared;

import java.io.Serializable;
import java.util.Date;

public class CommentToAnswerTransported implements Serializable{
	
    private Long id;
    private String content;
    private Integer rate;
    private Long idOfPost;
    private String author;
    private UserAccountInfo authorUser;
    private Boolean spam;
    private Date date;

    public CommentToAnswerTransported(){
    	
    }

    public CommentToAnswerTransported(String content, Long id, Integer rate, Long idOfPost) {
        this.content = content;
        this.setIdOfPost(idOfPost);
        this.id = id;
        this.rate = rate;
        this.spam = false;
    }
    
    public CommentToAnswerTransported(String content, Long id, Integer rate, Long idOfPost, String author) {
        this.content = content;
        this.setIdOfPost(idOfPost);
        this.id = id;
        this.rate = rate;
        this.setAuthor(author);
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

	public void setDate(Date date) {
		this.date = new Date(date.getTime());
	}

	public Date getDate() {
		return date;
	}

	public UserAccountInfo getAuthorUser() {
		return authorUser;
	}

	public void setAuthorUser(UserAccountInfo authorUser) {
		this.authorUser = authorUser;
	}
	
	public Boolean isAuthorUser(){
		if ((authorUser==null)||(authorUser.getService()==UserAccountInfo.UNAUTHORIZED)) {
			return false;
		} else{
			return true;
		}
	}
}
