package votes.client.controlers;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

import votes.client.oauth.CurrentUser;
import votes.client.views.LoginView;

public class LoginControler extends Controler {
	private final HandlerManager eventBus;
	public LoginControler(HandlerManager eventBus){
		LoginView view = new LoginView();
		setView(view);
		this.eventBus = eventBus;
	}
	
	public void showView(HasWidgets container){
		((LoginView)view).setEventHandlers(eventBus);
		super.showView(container);
	}
	
	public void init(){
		((LoginView)view).init();
	}
	
	public void setUser(CurrentUser user){
		((LoginView)view).setUser(user);
	}
}
