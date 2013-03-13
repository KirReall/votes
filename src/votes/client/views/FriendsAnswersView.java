package votes.client.views;

import java.util.ArrayList;

import votes.shared.UserAccountInfo;

import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

public class FriendsAnswersView extends View {
	private final DecoratedPopupPanel plane = new DecoratedPopupPanel();
		
	public FriendsAnswersView(){
		initWidget(plane);
		
	}
	public void showFriends(ArrayList<UserAccountInfo> friends){
		if (friends.isEmpty()){
			plane.setWidget(new HTML("empty"));
		} else {
			for (UserAccountInfo friend : friends) {
				FlowPanel friendPanel = new FlowPanel();
				Image img = new Image(friend.getPhoto());
				img.setTitle(friend.getFirstName()+" "+friend.getLastName());
				friendPanel.add(img);
				friendPanel.add(new HTML(SimpleHtmlSanitizer.sanitizeHtml(friend.getFirstName()+" "+friend.getLastName()).asString()));
				plane.add(friendPanel);
			}
		}
	}
	
	
}
