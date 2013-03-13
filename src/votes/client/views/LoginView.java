package votes.client.views;

import votes.client.VotesConstants;
import votes.client.event.LogoutEvent;
import votes.client.oauth.CurrentUser;
import votes.shared.UserAccountInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends View {
	private CurrentUser user;
	private Boolean initialized = false;
	private VotesConstants constants = GWT.create(VotesConstants.class);
	private FlowPanel list = new FlowPanel();
	private FlowPanel panel = new FlowPanel();
	private LoginButton vkLoginButton = new LoginButton(UserAccountInfo.VKUSER);
	private LoginButton gpLoginButton = new LoginButton(UserAccountInfo.GPLUSUSER);
	private LoginButton fbLoginButton = new LoginButton(UserAccountInfo.FBUSER);
	private Anchor logout = new Anchor(constants.Logout());
	
	public LoginView(){
		
		vkLoginButton.setStyleName("Vk");
		vkLoginButton.addStyleName("button");
		gpLoginButton.setStyleName("gp");
		gpLoginButton.addStyleName("button");
		fbLoginButton.setStyleName("fb");
		fbLoginButton.addStyleName("button");
		
//		vkLoginButton.addStyleName("linkElement");
//		gpLoginButton.addStyleName("linkElement");
//		fbLoginButton.addStyleName("linkElement");	
		
		panel.addStyleName("loginPanel");
		
		panel.add(list);
		initWidget(panel);
	}
	
	public void init(){
		if (!initialized) {
			user = null;
			initialized = true;
			list.clear();
			list.add(new Label(constants.Login()));
			list.add(vkLoginButton);
			list.add(gpLoginButton);
			list.add(fbLoginButton);
			
			panel.addStyleName("login");
			panel.removeStyleName("logout");
		}		
			
	}
	
	public class ULPanel extends ComplexPanel { 
		private UListElement list; 
		public ULPanel() { 
			list = Document.get().createULElement(); 
			//list.setId("linkList");
			setElement(list); 
		} 
		@Override 
		public void add(Widget child) { 
			Element li = Document.get().createLIElement().cast(); 
			list.appendChild(li); 
			//li.appendChild(child.getElement());
			super.add(child, li); 
		}
		
		@Override 
		public void clear() { 
			super.clear();
			Node li = list.getLastChild();
			while (!(li == null)) {
				list.removeChild(li);
				li = list.getLastChild();
			}
			//li.appendChild(child.getElement());
			 
		}
	}	   
	
	public void setEventHandlers(final HandlerManager eventBus){
		vkLoginButton.setEventHandlers(eventBus);
		gpLoginButton.setEventHandlers(eventBus);
		fbLoginButton.setEventHandlers(eventBus);
		logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LogoutEvent());
				
			}
		});
	}

	public void setUser(CurrentUser user) {
		if (this.user != user){
			list.clear();
			initialized = false;
			this.user = user;
			//HTML userHTML = new HTML("<b>"+user.getFirstName()+" "+ user.getLastName()+"</b>");
			Image userPhoto = new Image(user.getPhoto());
			Integer width = userPhoto.getWidth();
			Integer height = userPhoto.getHeight();
			if (width*height == 0){
				userPhoto.setVisibleRect(0, 0, 50, 50);
			} else {
				userPhoto.setVisibleRect(0, Math.round((height-width)/2), width, width);
			}
			userPhoto.setTitle(user.getFirstName()+" "+ user.getLastName());
			userPhoto.setStyleName("photo");
			userPhoto.addStyleName("loginElement");
			
			panel.removeStyleName("login");
			panel.addStyleName("logout");
			
//			userPhoto.addStyleName("linkElement");
//			userHTML.setStyleName("linkElement");
			list.add(userPhoto);
			FlowPanel logoutPanel = new FlowPanel();
			logoutPanel.add(logout);
			list.add(logoutPanel);
			//list.add(userHTML);
		}		
	}
}
