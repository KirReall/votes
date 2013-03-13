package votes.client;

import votes.client.controlers.LinkListControler;
import votes.client.controlers.LoginControler;
import votes.client.oauth.AuthManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class HistoryValueChangeHandler implements ValueChangeHandler<String> {
	private LinkListControler headerlinkListControler;
	private LinkListControler footerlinkListControler;
	private LoginControler loginControler;
	private final ControlersChanger controlersChanger;
	private final AuthManager authManager;

	public HistoryValueChangeHandler() {
		super();

		authManager = new AuthManager();

		HandlerManager eventBus = new HandlerManager(null);
		EventBusBinder eBB = new EventBusBinder(eventBus, authManager);
		eBB.bindLoginEventBus();
		
		this.controlersChanger = new ControlersChanger(eventBus, eBB);
		
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess() {
				headerlinkListControler = controlersChanger.getHeaderControler();
				footerlinkListControler = controlersChanger.getFooterControler();
				headerlinkListControler.showView(RootPanel.get("headerLinks"));

				footerlinkListControler.showView(RootPanel.get("footerLinks"));
				
				loginControler = controlersChanger.getLoginControler();
				loginControler.showView(RootPanel.get("login"));
				authManager.setLoginControler(loginControler);
			}
		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		RootPanel.get("body").clear();
		final String token = event.getValue();
		FlowPanel imgPanel = new FlowPanel();
		Image img = new Image("res/1.gif");
		imgPanel.setStyleName("loading");
		imgPanel.add(img);
		RootPanel.get("body").add(imgPanel);
		// RootPanel.getBodyElement().insertAfter(RootPanel.get("loadingWrapper").getElement(),
		// RootPanel.get("body").getElement());

		controlersChanger.createControler(token);

		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess() {

				// RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());
				
				if (authManager.isUserLoginIn()) {
					footerlinkListControler.activate(token,true);
					headerlinkListControler.activate(token,true);
					authManager.getCurrentUser();
					//loginControler.setUser(authManager
					//		.getCurrentUser());
					
				} else {
					footerlinkListControler.activate(token,false);
					headerlinkListControler.activate(token,false);
					loginControler.init();
				}

			}
		});

	}

}
