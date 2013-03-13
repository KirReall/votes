package votes.client.controlers;

import votes.client.views.View;

import com.google.gwt.user.client.ui.HasWidgets;

public abstract class Controler implements ControlerIF{
	protected View view;
	
	public void setView(View view){
		this.view = view;
	}

	public void showView(HasWidgets container){
		container.clear();
	    container.add(view.asWidget());
	}
}
