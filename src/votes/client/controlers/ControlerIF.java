package votes.client.controlers;

import votes.client.views.View;

import com.google.gwt.user.client.ui.HasWidgets;

public interface ControlerIF {
	public void setView(View view);
	public void showView(HasWidgets container);
	//public ControlerIF getControler(HandlerManager eventBus);
}
