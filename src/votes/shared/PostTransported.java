package votes.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PostTransported implements Serializable{
	
    private Long id;
    private UserAccountInfo author;
    private String content;
    private Integer rate;
    private Integer absoluteRate;
	private Date date;
    private Integer numberOfComment;
    private List<AnswerTransported> answers = new ArrayList<AnswerTransported>();
    private List<CommentToAnswerTransported> comments = new ArrayList<CommentToAnswerTransported>();
    private Boolean voted;
    private String embed;
    
    public PostTransported() {
    	
    }
    
    public PostTransported(String content, Date date, Long Id, Integer rate, Integer numberOfComments, List<AnswerTransported> answers, String embed) {
        this.content = content;
        this.date = new Date(date.getTime());
        this.id = Id;
        this.numberOfComment =numberOfComments;
        this.rate=rate;
        this.setAnswers(answers);
        this.voted = false;
        this.embed = embed;
    }

    public PostTransported(String content, Date date, Long Id, Integer rate, Integer numberOfComments, List<AnswerTransported> answers, String embed, List<CommentToAnswerTransported> comments) {
    	this(content,date,Id,rate,numberOfComments,answers, embed);
        this.setComments(comments);
    }
    
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

	public Integer getRate() {
		return rate;
	}

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }
	
	public void increaseRate() {
		this.rate ++;
		this.absoluteRate ++;
	}
	
	public void decreaseRate() {
		this.rate --;
		this.absoluteRate --;
	}

	public void setAnswers(List<AnswerTransported> answers) {
		this.answers = answers;
	}

	public List<AnswerTransported> getAnswers() {
		return answers;
	}

	public void setComments(List<CommentToAnswerTransported> comments) {
		this.comments = comments;
	}

	public List<CommentToAnswerTransported> getComments() {
		return comments;
	}

	public void setNumberOfComment(Integer numberOfComment) {
		this.numberOfComment = numberOfComment;
	}

	public Integer getNumberOfComment() {
		if (numberOfComment==null) {
			return 0;
		} else {
			return numberOfComment;
		}
		
	}
	
	public Integer getAbsoluteRate() {
		return absoluteRate;
	}

	public void setAbsoluteRate(Integer absoluteRate) {
		this.absoluteRate = absoluteRate;
	}

	public void setUserVote(Long userVote) {
		if (!(userVote==null)){
			this.voted = true;
			for (AnswerTransported answer : answers) {
				if (answer.getId().equals(userVote)) {
					answer.setSelected(true);
				}
			}
		}
	}

	public Boolean isVoted() {
		return voted;
	}

	public void setAuthor(UserAccountInfo author) {
		this.author = author;
	}

	public UserAccountInfo getAuthor() {
		return author;
	}

	public String getEmbed() {
		return embed;
	}

	public void setEmbed(String embed) {
		this.embed = embed;
	}

}
