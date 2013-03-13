package votes.client;

import votes.client.controlers.AddPostControler;
import votes.client.controlers.ControlerIF;
import votes.client.controlers.LinkListControler;
import votes.client.controlers.ListOfPostControler;
import votes.client.controlers.LoginControler;
import votes.client.controlers.PostAndCommentsControler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class ControlersChanger {
	private final HandlerManager eventBus;
	private final EventBusBinder eBB;
	private ControlerIF bodyControler;
	private LinkListControler headerlinkListControler;
	private LinkListControler footerlinkListControler;
	private LoginControler loginControler;

	public ControlersChanger(HandlerManager eventBus, EventBusBinder eBB) {
		this.eventBus = eventBus;
		this.eBB = eBB;
	}

	public void createControler(String token) {

		if (token != null) {
			if (token.startsWith(Token.LIST)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						eBB.bindCommonEventBus();
						eBB.bindListOfPostEventBus();
						ListOfPostControler listOfPostControler = ListOfPostControler
								.getControler(eventBus);
						listOfPostControler.setOrderField("rate");
						listOfPostControler.setMy(false);
						bodyControler = listOfPostControler;
						show();
					}
				});
			} else if (token.startsWith(Token.BEST)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						eBB.bindCommonEventBus();
						eBB.bindListOfPostEventBus();
						ListOfPostControler listOfPostControler = ListOfPostControler
								.getControler(eventBus);
						listOfPostControler.setOrderField("absoluteRate");
						listOfPostControler.setMy(false);
						bodyControler = listOfPostControler;
						show();
					}
				});
			} else if (token.startsWith(Token.LAST)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						eBB.bindCommonEventBus();
						eBB.bindListOfPostEventBus();
						ListOfPostControler listOfPostControler = ListOfPostControler
								.getControler(eventBus);
						listOfPostControler.setOrderField("date");
						listOfPostControler.setMy(false);
						bodyControler = listOfPostControler;
						show();
					}
				});
			} else if (token.startsWith(Token.MY)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						eBB.bindCommonEventBus();
						eBB.bindListOfPostEventBus();
						ListOfPostControler listOfPostControler = ListOfPostControler
								.getControler(eventBus);
						listOfPostControler.setOrderField("date");
						listOfPostControler.setMy(true);
						bodyControler = listOfPostControler;
						show();
					}
				});
			} else if (token.equals(Token.ADD)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						eBB.bindAddPostEventBus();
						AddPostControler addPostControler = AddPostControler
								.getControler(eventBus);
						bodyControler = addPostControler;
						show();
					}
				});
			} else if (token.startsWith(Token.POST)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						eBB.bindCommonEventBus();
						eBB.bindPostAndCommentsEventBus();
						PostAndCommentsControler postAndCommentsControler = PostAndCommentsControler
								.getControler(eventBus);
						bodyControler = postAndCommentsControler;
						show();
					}
				});
				// } else if (token.startsWith("admin")) {
				// return loginControler;
			}
		}

	}

	public LinkListControler getFooterControler() {
		if (footerlinkListControler == null) {
			footerlinkListControler = new LinkListControler(eventBus);
		}
		return footerlinkListControler;
	}

	public LinkListControler getHeaderControler() {
		if (headerlinkListControler == null) {
			headerlinkListControler = new LinkListControler(eventBus);
		}
		return headerlinkListControler;
	}
	
	public LoginControler getLoginControler() {
		if (loginControler == null) {
			loginControler = new LoginControler(eventBus);
		}
		return loginControler;
	}

	private void show() {

		bodyControler.showView(RootPanel.get("body"));
	}

}
