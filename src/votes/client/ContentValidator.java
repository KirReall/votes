package votes.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;

public class ContentValidator {
	private String content = null;
	private List<String> answersContent = new ArrayList<String>();
	private String erorrsInFilling = "";
	private VotesConstants constants = GWT.create(VotesConstants.class);
	private final Boolean comment;
	
	public ContentValidator(){
		comment = false;
	}
	
	public ContentValidator(String content) {
		super();
		this.content = content;
		comment = true;
		
	}
	
	public ContentValidator(String content, List<String> answersContent) {
		super();
		this.content = content;
		this.answersContent = answersContent;
		comment = false;
	}
	
	public Boolean isNotValid(){
		if (isContentNotValid()){
			if (comment) {
				erorrsInFilling = constants.SmallComment();
			} else {
				erorrsInFilling = constants.SmallQuestion();
			}
			return true;
		} else if (isAnswersContentNotValid()){
			erorrsInFilling = constants.EmptyAnswer();
			return true;
		}
		return false;
	}
	
	public String getErorrsInFilling(){
		return this.erorrsInFilling;
	}

	private boolean isAnswersContentNotValid() {
		Boolean result = false;
		for (String answerContent : answersContent) {
			result = result||isStringEmpty(answerContent);
		}
		return result;
	}

	private boolean isContentNotValid() {
		//String testContent = content;		
		return isStringTooSmall(content);
	}

	private Boolean isStringTooSmall(String content) {
		String test = content;
		test = test.replace(" ", "");
		test = test.replace("\n", "");
		if (test.length()<5) {
			return true;
		}
		return false;
	}
	
	private Boolean isStringEmpty(String content) {
		String test = content;
		test = test.replace(" ", "");
		test = test.replace("\n", "");
		if (test.isEmpty()) {
			return true;
		}
		return false;
	}

}
