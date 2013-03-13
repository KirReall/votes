package votes.client.views;

import votes.client.VotesConstants;
import votes.client.event.ChangeRateOfCommentEvent;
import votes.client.event.SetSpamCommentEvent;
import votes.shared.CommentToAnswerTransported;

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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

public class CommentView extends View {
	private final HTML html = new HTML();
	private final FlowPanel buttonPanel = new FlowPanel();
	private final Button increaseButton = new Button();
	private final Button decreaseButton = new Button();
	private final Button spamButton = new Button();
	private final Label rate = new Label();
	private final HTML date = new HTML();
	private final FlowPanel plane = new FlowPanel();
	private final FocusPanel commentFrame = new FocusPanel(plane);
	private CommentToAnswerTransported comment;
	private VotesConstants constants = GWT.create(VotesConstants.class);
	
	public CommentView(){
		buttonPanel.setStyleName("commentButtonDiv");
		buttonPanel.add(increaseButton);		
	    buttonPanel.add(decreaseButton);
	    buttonPanel.add(spamButton);
		
	    commentFrame.setStyleName("commentFrame");		
		commentFrame.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				buttonPanel.setVisible(true);
				commentFrame.addStyleDependentName("over");
			}
		});
		commentFrame.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				buttonPanel.setVisible(false);
				commentFrame.removeStyleDependentName("over");
			}
		});
		increaseButton.setStyleName("increaseButton");
		increaseButton.addStyleName("button");
		increaseButton.addStyleName("rateButton");
		increaseButton.setTitle(constants.Good());
		
		decreaseButton.setStyleName("decreaseButton");
		decreaseButton.addStyleName("button");
		decreaseButton.addStyleName("rateButton");
		decreaseButton.setTitle(constants.Bad());
		
		spamButton.setStyleName("spamButton");
		spamButton.addStyleName("button");
		spamButton.addStyleName("rateButton");
		spamButton.setTitle(constants.Spam());
		
		rate.setStyleName("metaElements");
	    date.setStyleName("metaElements");
		
		plane.setStyleName("comment");

		initWidget(commentFrame);
	}
	
	public void setPostContent(final CommentToAnswerTransported comment) {

		if (comment.isAuthorUser()) {
			html.setHTML("<img src='"+comment.getAuthorUser().getPhoto()+"' height='32px' class='photo' style='float: left; margin: 1px;'/>"+comment.getAuthorUser().getFirstName() + " "+ comment.getAuthorUser().getLastName() +": "+ comment.getContent().replace("\n", "<br>"));
		} else {
			html.setHTML(comment.getAuthor() +": "+ comment.getContent().replace("\n", "<br>"));
		}
		this.comment = comment;
		//rate.setText(comment.getRate().toString());
		buttonPanel.setVisible(false);
		
	    plane.add(buttonPanel);
	    plane.add(html);
	    Integer absoluteRate = comment.getRate();
	    rate.setText(absoluteRate.toString());
		//rate.setStylePrimaryName("rate");
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
		String strDate = df.format(comment.getDate());
		date.setText(strDate);
	    
	    FlowPanel metadataPanel = new FlowPanel();
	    metadataPanel.addStyleName("commentMetadata");
	    
	    metadataPanel.add(rate);
	    metadataPanel.add(date);
	    
	    plane.add(metadataPanel);
	    
	    if (comment.isSpam()) {
	    	setSpamComment();
	    }
	}
	
	public void setEventHandlers(final HandlerManager eventBus) {
		increaseButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setButtonsInvisible();
				eventBus.fireEvent(new ChangeRateOfCommentEvent(comment.getId(), true));				
			}
		});
		
		decreaseButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setButtonsInvisible();
				eventBus.fireEvent(new ChangeRateOfCommentEvent(comment.getId(), false));				
			}
		});
		
		spamButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setSpamComment();
				eventBus.fireEvent(new SetSpamCommentEvent(comment.getId()));				
			}
		});
	}

	protected void setSpamComment() {
		setButtonsInvisible();
		html.setVisible(false);	
		final InlineLabel authorLabel = new InlineLabel(comment.getAuthor() +": "+ constants.MarkedAsSpam()+" ");
		plane.addStyleDependentName("spam");
		final Anchor showLink = new Anchor();
		showLink.setText(constants.Show());
		showLink.setStyleName("anchor");
		plane.add(authorLabel);
		plane.add(showLink);
		showLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				html.setVisible(true);
				plane.removeStyleDependentName("spam");
				//html.setHTML(comment.getAuthor() +": "+ comment.getContent().replace("\n", "<br>"));
				showLink.removeFromParent();
				authorLabel.removeFromParent();
			}
		});
	}

	protected void setButtonsInvisible() {
		spamButton.setVisible(false);
		increaseButton.setVisible(false);
		decreaseButton.setVisible(false);
		
	}

	
}
