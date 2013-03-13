package votes.shared;

import java.io.Serializable;


public class AnswerTransported implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1187634676995146415L;

	protected Long id;

	protected String content;
 
	protected Integer rate;
	
	private Boolean selected;
	
	public static String OTHER = "other";

    public AnswerTransported(){
    	
    }

    public AnswerTransported(Long id, String content, Integer rate) {
    	this.id = id;
        this.content = content;
        this.rate = rate;
        this.selected = false;
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
	
	public void decreseRate() {
		this.rate --;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boolean isSelected() {
		return selected;
	}
}
