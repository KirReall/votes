package votes.client.views;

import votes.client.VotesConstants;
import votes.client.event.ChangeRateOfPostEvent;
import votes.shared.PostTransported;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class PostSubView extends View {
	private final FlowPanel main = new FlowPanel(); 
	private final HTML content = new HTML();
	private final HTML embed = new HTML(); 
	private final HTML date = new HTML();
	private final Label rate = new Label();
	private final Anchor voteLink = new Anchor();
	private final Hyperlink comments = new Hyperlink();
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button increaseButton = new Button();
	private final Button decreaseButton = new Button();
	private final ViewsFrame plane = new ViewsFrame();
	private final AnswerSelectorView answerPlane = new AnswerSelectorView(this);
	private final FlowPanel metadataPanel;
	private final SimplePanel yaShare = new SimplePanel();
	private final Image author = new Image();
	private PostTransported post;
	
	

	private VotesConstants constants = GWT.create(VotesConstants.class);
	
	public PostSubView(){
		
		initWidget(main);
		// podgotovka
		
		buttonPanel.setStyleName("buttonDiv");
		
		buttonPanel.add(increaseButton);
		increaseButton.setStyleName("increaseButton");
		increaseButton.addStyleName("button");
		increaseButton.addStyleName("rateButton");
		increaseButton.setTitle(constants.Good());
		
	    buttonPanel.add(decreaseButton);
	    decreaseButton.setStyleName("decreaseButton");
	    decreaseButton.addStyleName("button");
	    decreaseButton.addStyleName("rateButton");
	    decreaseButton.setTitle(constants.Bad());
	    
	    metadataPanel = new FlowPanel();
	    metadataPanel.addStyleName("metadata");
	    
	    metadataPanel.add(rate);
	    metadataPanel.add(date);
	    metadataPanel.add(author);
	    metadataPanel.add(comments);
	    metadataPanel.add(yaShare);
	    
	    rate.setStyleName("metaElements");
	    date.setStyleName("metaElements");
	    author.setStyleName("metaElements");
	    comments.setStyleName("metaElements");
	    yaShare.setStyleName("metaElements");
	    	    
	    content.addStyleName("content");	
	    
	    embed.setStyleName("content");
	    
	    plane.add(buttonPanel);
	    plane.add(content);
	    plane.add(embed);
	    plane.add(answerPlane.asWidget());
	    //plane.add(otherButton);
	    plane.add(metadataPanel);
	    
	    buttonPanel.setVisible(false);
	    yaShare.setVisible(false);
	    plane.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				buttonPanel.setVisible(true);
				yaShare.setVisible(true);
			}
		});
	    
	    plane.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				buttonPanel.setVisible(false);
				yaShare.setVisible(false);
			}
		});
	    
	}
	
	public void clear(){
		//plane.clear();
		main.remove(plane);
		//plane.removeStyleName("postView");
	}
	
	public void setPostContent(final PostTransported post) {
		if (main.getWidgetCount()>0) {
			main.remove(plane);
		}
		String contentStr = redactContentStr(post.getContent());
		content.setHTML(contentStr);
		this.post = post;
		embed.setHTML(post.getEmbed());
		
		Integer absoluteRate = post.getAbsoluteRate();
		rate.setText(absoluteRate.toString());
		
		if (absoluteRate>0) {
			rate.removeStyleDependentName("minus");
			rate.addStyleDependentName("plus");
		} else if (absoluteRate<0) {
			rate.removeStyleDependentName("plus");
			rate.addStyleDependentName("minus");
		} else {
			rate.removeStyleDependentName("plus");
			rate.removeStyleDependentName("minus");
		}
		DateTimeFormat df = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
		String strDate = df.format(post.getDate());
		date.setText(strDate);
		if (post.getAuthor() != null) {
			author.setUrl(post.getAuthor().getPhoto());
			author.setTitle(post.getAuthor().getFirstName() + " "+ post.getAuthor().getLastName());
		}
		comments.setText(constants.Comments() +"("+post.getNumberOfComment().toString()+")");
		comments.setTargetHistoryToken("post&"+post.getId().toString());
		increaseButton.setVisible(true);
		decreaseButton.setVisible(true);
		//ya zakladki
		
		yaShare.clear();
		YaShareView yaShareView = new YaShareView(); 
		yaShareView.turnOn(post.getId().toString(), post.getContent());
		yaShare.add(yaShareView);
		
		answerPlane.setPostId(post.getId());
	    answerPlane.setAnswers(post.getAnswers());
	    if (post.isVoted()){
	    	showAnswersRate();
	    }
	    
	    main.add(plane);
	}
	
	private String redactContentStr(String contentIn) {
		String contentStr = contentIn.replace("\n", "<br>");
		if ((contentStr.contains("http://"))||(contentStr.contains("https://"))){
			String embedURL = contentStr.substring(contentStr.indexOf("http"));
			int firstSpace = embedURL.indexOf(' ');
			if (firstSpace>0){
				embedURL = embedURL.substring(0,firstSpace);
			}
			contentStr = contentStr.replace(embedURL,"<a href='"+embedURL+"'>"+embedURL+"</a>");
		}
		
		return contentStr;
	}

	public void hideButtonsAndCommentLink(){
		
		increaseButton.setVisible(false);
		decreaseButton.setVisible(false);
		comments.setVisible(false);
				
		voteLink.setStyleName("anchor");
		voteLink.setText(constants.Vote());
		
		plane.add(voteLink);
		voteLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				answerPlane.showAnswersRate();
				answerPlane.setAnswered(true);
				answerPlane.remooveNewAnswerBox();
				voteLink.removeFromParent();
				
			}
		});
	}
	

	public void setEventHandlers(final HandlerManager eventBus) {
		answerPlane.setEventHandlers(eventBus);
		increaseButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ChangeRateOfPostEvent(post.getId(), true));
				increaseButton.setVisible(false);
				decreaseButton.setVisible(false);
			}
		});
		decreaseButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ChangeRateOfPostEvent(post.getId(), false));
				increaseButton.setVisible(false);
				decreaseButton.setVisible(false);
			}
		});
		
		
	}

	public void showAnswersRate() {
		answerPlane.showAnswersRate();
		voteLink.removeFromParent();
				
	}
	
	public PostTransported getPost() {
		return post;
	}

}
