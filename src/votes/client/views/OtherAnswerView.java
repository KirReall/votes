package votes.client.views;

import java.util.ArrayList;

import votes.client.VotesConstants;
import votes.shared.AnswerTransported;
import votes.shared.UserAccountInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;

public class OtherAnswerView extends View implements IAnswerView{
	private final FlowPanel plane = new FlowPanel();
	private AnswerTransported answer;
	private Image img = new Image("res/closed.gif");
	private final HTML answerButton = new HTML();
	private final HTML answerContent = new HTML();
	private String color = null;
	private Boolean otherAnswer = false;
	private VotesConstants constants = GWT.create(VotesConstants.class);

	public OtherAnswerView() {
		plane.setWidth("100%");
		plane.setStyleName("answer");
		initWidget(plane);
		img.setStyleName("otherImage");
	}

	public OtherAnswerView(AnswerTransported answer) {
		this();
		this.answer = answer;
		showAnswer();
	}
	
	public void showAnswer(){
		plane.clear();
		answerButton.setText(constants.Other());
		plane.add(img);
		plane.add(answerButton);
	}
	
	public void showAnswerRate(Integer totalRating){
		plane.clear();
		Integer rateOfAnswer = answer.getRate();
		Integer percentOfAnswerRate = Math.round((rateOfAnswer.floatValue()*100)/totalRating);
		InlineLabel rate = new InlineLabel(" - "+rateOfAnswer.toString()+" - ");
		rate.setStyleName("otherImage");
		//HTML html = new HTML(+"<img src='"+img.getUrl()+"'>"+answer.getContent());
		//html.setStyleName("answerContent");
		
		SimplePanel answerBar = new SimplePanel();
		answerBar.addStyleName("answerBarConteiner");
		
		//answerContent.setHTML("<div style='width:"+String.valueOf(percentOfAnswerRate)+"%;background:"+color+"'>"+percentOfAnswerRate.toString()+"%</div>");
		answerContent.setText(percentOfAnswerRate.toString()+"%");
		answerContent.setWidth(String.valueOf(percentOfAnswerRate)+"%");
		answerContent.setStyleName("answerBar");
		answerContent.addStyleName(color);
		if (answer.isSelected()) {
			answerContent.addStyleName("selectedAnswerBar");
		}
		answerBar.add(answerContent);
		
		plane.add(answerBar);
		plane.add(rate);
		plane.add(img);
		plane.add(answerButton);
	}
	
	public HasClickHandlers getAnswerButton(){
		return answerButton;
	}
	
	public Long getAnswerId(){
		return answer.getId();
	}
	
	public void increaseAnswerRate(){
		answer.increaseRate();
	}

	public void setColor(String color) {
		this.color = color;
		
	}

	public void setOtherAnswer(Boolean otherAnswer) {
		this.otherAnswer = otherAnswer;
	}

	public Boolean isOtherAnswer() {
		return otherAnswer;
	}

	@Override
	public Boolean isOther() {
		return true;
	}
	
	public void setOpenImg() {
		img.setUrl("res/opened.gif");
	}
	
	public void setCloseImg() {
		img.setUrl("res/closed.gif");
	}

	@Override
	public void setSelected() {
		answer.setSelected(true);
		
	}
	
	@Override
	public void showFriends(ArrayList<UserAccountInfo> arrayList) {
		for (UserAccountInfo friend : arrayList) {
			Image img = new Image(friend.getPhoto());
			img.setTitle(friend.getFirstName()+" "+friend.getLastName());
			plane.add(img);
		}
		
		
	}
	
}
