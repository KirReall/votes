package votes.client.controlers;

import votes.client.views.LinkListView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class LinkListControler extends Controler {
	private final HandlerManager eventBus;
	public LinkListControler(HandlerManager eventBus){
		LinkListView view = new LinkListView();
		setView(view);
		this.eventBus = eventBus;
	}
	
	public void showView(HasWidgets container){
		super.showView(container);
	}
	
	public void activate(String token, Boolean userLogin){
		((LinkListView)view).activateLink(token, userLogin);
	}
	
}
