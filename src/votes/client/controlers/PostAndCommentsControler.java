package votes.client.controlers;

import votes.client.ContentValidator;
import votes.client.VotesConstants;
import votes.client.event.AddCommentEvent;
import votes.client.event.GetPostEvent;
import votes.client.views.PostAndCommentsView;
import votes.shared.PostTransported;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;


public class PostAndCommentsControler extends Controler{
	private static PostAndCommentsControler INSTANCE;
	private final HandlerManager eventBus;
	private Long postId = null;
	private VotesConstants constants = GWT.create(VotesConstants.class); 
	
	private PostAndCommentsControler(HandlerManager eventBus){
		PostAndCommentsView view = new PostAndCommentsView();
		setView(view);
		this.eventBus = eventBus;
		bindViewHandlers();
	}
	
	public static synchronized PostAndCommentsControler getControler(HandlerManager eventBus) {
		if (INSTANCE == null) {
			INSTANCE = new PostAndCommentsControler(eventBus);
		}
		return INSTANCE;
	}
	
	private void bindViewHandlers() {
		((PostAndCommentsView)view).getSubmitButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {				
				String author = ((PostAndCommentsView)view).getAuthor().getValue();
				if ((author.isEmpty())||(author.replace(" ", "").isEmpty())){
					author = constants.Anonym();
				}
				String content = ((PostAndCommentsView)view).getContent().getValue();
				ContentValidator contentValidator = new ContentValidator(content);
				if (contentValidator.isNotValid()) {
					((PostAndCommentsView)view).setError(contentValidator.getErorrsInFilling());
				} else {
					eventBus.fireEvent(new AddCommentEvent(content, postId, author));
				}							
			}
		});
		
	
	}
	
	public void showView(final HasWidgets container){
		((PostAndCommentsView)view).initView();
		String token = History.getToken();
		postId = Long.valueOf(token.substring(token.indexOf("&")+1));
		eventBus.fireEvent(new GetPostEvent(postId, (PostAndCommentsView)view));
		super.showView(container);
	}

	protected void showViewWithData(HasWidgets container, PostTransported result) {
		((PostAndCommentsView)view).setPost(result);
		((PostAndCommentsView)view).setEventHandlers(eventBus);		
	}

}
