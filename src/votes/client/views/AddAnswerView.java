package votes.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;

public class AddAnswerView extends View {
	private TextBox answer;
	private Button deleteButton;
	
	public AddAnswerView() {
		FlowPanel panel = new FlowPanel();
		panel.setWidth("590px");
		answer = new TextBox();
		answer.setMaxLength(500);
		answer.addStyleName("answerBox");
		answer.addStyleName("textBox");
		deleteButton = new Button();
		deleteButton.setVisible(false);
		deleteButton.setStyleName("button");
		deleteButton.addStyleName("delButton");
		
		panel.add(answer);
		panel.add(deleteButton);
		
		initWidget(panel);
	}
	
	public void setDelButtonVisible(Boolean visible){
		deleteButton.setVisible(visible);
	}
	
	public HasClickHandlers getDelButton(){
		return this.deleteButton;
	}
	
	public HasValue<String> getAnswerConent(){
		return this.answer;
	}
}
