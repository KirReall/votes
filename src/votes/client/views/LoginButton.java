package votes.client.views;

import votes.client.event.LoginEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;

public class LoginButton extends Button {
	private final Integer service; 
	public LoginButton(Integer service) {
		this.service = service;
	}
	
	public void setEventHandlers(final HandlerManager eventBus) {
		this.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LoginEvent(service));
				
			}
		});
	}
}
