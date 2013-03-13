package votes.client.views;

import java.util.ArrayList;

import votes.shared.UserAccountInfo;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface IAnswerView {
	public Boolean isOther();
	public void showAnswer();
	public void showAnswerRate(Integer totalRating);
	public HasClickHandlers getAnswerButton();
	public Long getAnswerId();
	public void increaseAnswerRate();
	public void setOtherAnswer(Boolean otherAnswer);
	public Boolean isOtherAnswer();
	public void setColor(String color);
	public Widget asWidget();
	public void setSelected();
	public void showFriends(ArrayList<UserAccountInfo> arrayList);
	
}
