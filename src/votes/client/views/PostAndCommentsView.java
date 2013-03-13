package votes.client.views;


import java.util.ArrayList;
import java.util.List;

import votes.client.VotesConstants;
import votes.shared.CommentToAnswerTransported;
import votes.shared.PostTransported;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class PostAndCommentsView extends View {
	private final FlowPanel vPanel;
	private final FlowPanel commentContainer = new FlowPanel();
	private final FlowPanel addCommentPanel = new FlowPanel();
	private final TextArea tb;
	private final TextBox author;
	private final InlineLabel authorLabel = new InlineLabel();
	private Label errorLabel;
	private final Button submitButton;
	private PostSubView PostsView = new PostSubView();
	private ListChangerView listChanger = new ListChangerView();
	private List<CommentView> CommentViews = new ArrayList<CommentView>();
	private VotesConstants constants = GWT.create(VotesConstants.class);
	
	public PostAndCommentsView(){
		vPanel = new FlowPanel();
	    //vPanel.setSpacing(5);
	    vPanel.setWidth("100%");
	    //vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	   
	   	PostsView = new PostSubView();
	   	
	    
	    errorLabel = new Label();
	    
	    author = new TextBox();
	    author.setWidth("100px");
	    author.setStyleName("authorTextBox");
	    author.addStyleName("textBox");
	    
	    tb = new TextArea();
	    //tb.setWidth("80%");
	    tb.setWidth("100%");
	    tb.setName("textBoxFormElement");
	    tb.addStyleName("textBox");
	   
	    
	    submitButton = new Button(constants.Submit());
	    submitButton.setStyleName("button");
	    
	    
	    authorLabel.setText(constants.Author()+":");
	    FlowPanel authorSubmitDiv = new FlowPanel();
	    authorSubmitDiv.add(authorLabel);
	    authorSubmitDiv.add(author);
	    authorSubmitDiv.add(submitButton);
	    authorSubmitDiv.setStyleName("authorSubmitDiv");
	    
	    addCommentPanel.add(errorLabel);
	    addCommentPanel.add(tb);
	    addCommentPanel.add(authorSubmitDiv);
	    
	    
	    addCommentPanel.setStyleName("addComment");
	    commentContainer.setStyleName("commentContainer");
	    
	    initWidget(vPanel);
	}

	public void initView(){
		vPanel.clear();
		commentContainer.clear();	
		vPanel.add(PostsView.asWidget());
	    
	    vPanel.add(listChanger.asWidget());
	    vPanel.add(commentContainer);
	    
	    errorLabel.setText("");
	    commentContainer.add(addCommentPanel);
	    
	    //FlowPanel hPanel = new FlowPanel();
	    //hPanel.setWidth("80%");
	    author.setValue("");
	    
	    //hPanel.add(author);
	    tb.setValue("");
	    
	    //hPanel.add(tb);
	}
	
	public void setPost(PostTransported content){
		PostsView.setPostContent(content);
		PostsView.hideButtonsAndCommentLink();
		CommentViews.clear();
		for (CommentToAnswerTransported comment : content.getComments()) {
			CommentView commentView = new CommentView();
			commentView.setPostContent(comment);
			CommentViews.add(commentView);
			commentContainer.add(commentView.asWidget());
		}
		Window.setTitle("qoops.ru: "+content.getContent());
	}
	


	public void setCurrentMaxPages(Long currentPage, Long maxPage) {
		listChanger.setChangerPropertis(currentPage, maxPage);
	}

	public void setEventHandlers(HandlerManager eventBus) {
		PostsView.setEventHandlers(eventBus);
		for (CommentView commentView : CommentViews) {
			commentView.setEventHandlers(eventBus);
		}
	}	
	
	public HasClickHandlers getSubmitButton(){
		return submitButton;
	}
	
	public HasValue<String> getContent(){
		return tb;
	}
	
	public HasValue<String> getAuthor(){
		return author;
	}
	
	public void setError(String error){
		errorLabel.setText(error);
	}

	public void disableAuthor() {
		author.setVisible(false);
		authorLabel.setVisible(false);		
	}
}
