package votes.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

//import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Post{

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long authorId;

    @Persistent
    private String content;
    
    @Persistent
    private Integer rate;
    
    @Persistent
    private Integer absoluteRate;
    
    @Persistent
    private Integer numberOfComment;
    
	@Persistent
    private Boolean published;

	@Persistent
    private Date date;
    
    @Persistent
    private List<Long> answers;
    
    @Persistent
    private Text embed;
    

	public Post(){
    	
    }    		
    
    public Post(String content, Date date) {
        this.content = content;
        this.date = date;
        Calendar cal = Calendar.getInstance();
        cal.set(2010, 0, 1);
        Date anotherDate = cal.getTime();  
        Long millisecondsInDay =  Integer.valueOf(1000*60*60*24).longValue();
        Integer created =  Long.valueOf((date.getTime() - anotherDate.getTime())/millisecondsInDay).intValue();
        this.rate = created;
        this.absoluteRate = 0;
        this.numberOfComment = 0;
    }

    public void setNumberOfComment(Integer numberOfComment) {
		this.numberOfComment = numberOfComment;
	}

    public Integer getNumberOfComment() {
		return numberOfComment;
	}
    
    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
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
	
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

	public void setAnswers(List<Long> answers) {
		this.answers = answers;
	}

	public List<Long> getAnswers() {
		return answers;
	}
	
	public void increaseRate() {
		if (absoluteRate == null) {
			this.absoluteRate = 0;
		}
		this.rate ++;
		this.absoluteRate ++;
	}
	
	public void decreaseRate() {
		this.rate --;
		this.absoluteRate --;
	}
	
	public void increaseNumberOfComment() {
		if (numberOfComment == null) {
			this.numberOfComment = 0;
		}
		this.numberOfComment ++;
	}
	
	public void decreaseNumberOfComment() {
		this.numberOfComment --;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Boolean getPublished() {
		return published;
	}

	public Integer getAbsoluteRate() {
		return absoluteRate;
	}

	public void setAbsoluteRate(Integer absoluteRate) {
		this.absoluteRate = absoluteRate;
	}

	public void setRate(Integer rate){
		this.rate = rate;
	}
	
	public void addAnswer(Long idOfAnswer){
		answers.add(idOfAnswer);
	}
	
    public String getEmbed() {
		return embed.getValue();
	}

	public void setEmbed(String embed) {
		this.embed = new Text(embed);
	}
}
