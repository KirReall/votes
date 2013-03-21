package votes.client.controlers;

import java.util.List;

import votes.client.ContentValidator;
import votes.client.event.AddPostEvent;
import votes.client.views.AddPostView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class AddPostControler extends Controler{
	private static AddPostControler INSTANCE;
	private final HandlerManager eventBus;
	private final EmbedAPI embedAPI;
	
	private AddPostControler(HandlerManager eventBus){
		AddPostView view = new AddPostView(this);
		setView(view);
		this.eventBus = eventBus;
		//bindViewHandlers();
		this.embedAPI = new EmbedAPI(this);
		
	}
	
	public static synchronized AddPostControler getControler(HandlerManager eventBus) {
		if (INSTANCE == null) {
			INSTANCE = new AddPostControler(eventBus);
		}
		return INSTANCE;
	}
	
	public void HandleSubmitEvent(String content, List<String> answersContent, String embedContent){
		ContentValidator contentValidator = new ContentValidator(content, answersContent);
		if (contentValidator.isNotValid()) {
			((AddPostView)view).setError(contentValidator.getErorrsInFilling());
		} else {
			eventBus.fireEvent(new AddPostEvent(content, answersContent, embedContent));				
		}
	}
	
	
	public void showView(final HasWidgets container){
		((AddPostView)view).initView();
		((AddPostView)view).setCaptcha(false);
		//eventBus.fireEvent(new IsUserLoginEvent((AddPostView)view));	
		super.showView(container);
	}

	public void findEmbed(String value) {
		if ((value.contains("http://"))||(value.contains("https://"))){
			String embedURL = value.substring(value.indexOf("http"));
			int firstSpace = embedURL.indexOf(' ');
			if (firstSpace>0){
				embedURL = embedURL.substring(0,firstSpace);
			}
			embedAPI.getEmbed(embedURL);
			((AddPostView)view).setEmbedLoadImg();
		}
		
	}
	
	public void getEmbed(String url){
		embedAPI.getEmbed(url);
		((AddPostView)view).setEmbedLoadImg();
	}

	public void setEmbed(String html) {
		((AddPostView)view).setEmbed(html);
	}
	
	public void setEmbedError() {
		((AddPostView)view).setEmbedError();		
	}
	

	
}
