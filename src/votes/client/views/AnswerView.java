package votes.client.views;

import java.util.ArrayList;

import votes.shared.AnswerTransported;
import votes.shared.UserAccountInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;

public class AnswerView extends View implements IAnswerView{
	private final FlowPanel plane = new FlowPanel();
	private AnswerTransported answer;
	private final RadioButton answerButton = new RadioButton("answer");
	private final HTML answerContent = new HTML();
	private String color = null;
	private Boolean otherAnswer = false;

	public AnswerView() {
		plane.setWidth("100%");
		plane.setStyleName("answer");
		initWidget(plane);
	}

	public AnswerView(AnswerTransported answer) {
		this();
		this.answer = answer;
		showAnswer();
	}
	
	public void showAnswer(){
		plane.clear();
		answerButton.setText(answer.getContent());
		plane.add(answerButton);
	}
	
	public void showAnswerRate(Integer totalRating){
		plane.clear();
		Integer rateOfAnswer = answer.getRate();
		Integer percentOfAnswerRate = Math.round((rateOfAnswer.floatValue()*100)/totalRating);
		
		Anchor rateLink = new Anchor();
		rateLink.setText(rateOfAnswer.toString());
		rateLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		HTML html = new HTML(" - "+answer.getContent());
		html.setStyleName("answerContent");
		
		SimplePanel answerBar = new SimplePanel();
		answerBar.addStyleName("answerBarConteiner");
		
		//answerContent.setHTML("<div style='width:"+String.valueOf(percentOfAnswerRate)+"%;background:"+color+"'>"+percentOfAnswerRate.toString()+"%</div>");
		answerContent.setText(percentOfAnswerRate.toString()+"%");
		answerContent.setWidth(String.valueOf(percentOfAnswerRate)+"%");
		answerContent.setStyleName("answerBar");
		answerContent.addStyleName(color);
		if (answer.isSelected()) {
			answerContent.addStyleName("selectedAnswerBar");
			html.addStyleName("selectedContent");
		}
		//
		answerBar.add(answerContent);
		
		plane.add(answerBar);
		plane.add(html);
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
		return false;
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
