package votes.shared;

import java.io.Serializable;


public class OtherAnswerTransported extends AnswerTransported implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 5287396652759632144L;

    private Long idOfPost;

    public OtherAnswerTransported(){
    	
    }

    public OtherAnswerTransported(Long idOfPost, Long id, String content, Integer rate) {
    	this.id = id;
        this.idOfPost = idOfPost;
    	this.content = content;
        this.rate = rate;
    }



	public void setIdOfPost(Long idOfPost) {
		this.idOfPost = idOfPost;
	}

	public Long getIdOfPost() {
		return idOfPost;
	}
}
