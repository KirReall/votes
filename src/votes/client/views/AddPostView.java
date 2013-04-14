package votes.client.views;

import java.util.ArrayList;
import java.util.List;

import votes.client.VotesConstants;
import votes.client.controlers.AddPostControler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;

public class AddPostView extends View {

	private AddPostControler controler;
	private final FormPanel formPanel;
	private final FlowPanel vPanel;
	private Button submitButton;
	private HTML errorLabel;
	private ExtendedTextArea tb;
	private EmbedView embedView;
	private FlowPanel answersPanel;
	private FlowPanel info;
	private List<AddAnswerView> answers = new ArrayList<AddAnswerView>();
	private VotesConstants constants = GWT.create(VotesConstants.class);
	
	public AddPostView(){
		errorLabel = new HTML();
	    
	    tb = new ExtendedTextArea();
	    tb.addStyleName("addPostBox");
	    tb.setName("textBoxFormElement");
	    tb.addStyleName("textBox");
	    
//	    tb.addValueChangeHandler(new ValueChangeHandler<String>() {
//			
//			@Override
//			public void onValueChange(ValueChangeEvent<String> event) {
//				if (isEmbedEmpty()){
//					controler.findEmbed(event.getValue());
//				}
//				
//			}
//		});
	    
	    tb.addOnPaste(new OnPasteHandler() {
			
			@Override
			public void onPaste(OnPasteEvent event) {
				if (embedView.isEmbedEmpty()) {
				controler.findEmbed(event.getValue());
				}
			}
		});
	    
	    tb.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String tbValue = tb.getValue();
				if (tbValue.length()>500){
					tbValue = tbValue.substring(0, 500);
					tb.setValue(tbValue);
				}
				Integer keyCode = event.getNativeKeyCode();
				if ((keyCode.equals(32)||keyCode.equals(13))&&(embedView.isEmbedEmpty())) {
					
				}
			}
		});
	    
	    embedView = new EmbedView();
	    
	    embedView.addEmbedDoneHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				controler.getEmbed(embedView.getAddEmbed());
				
			}
		});
	    
	    answersPanel = new FlowPanel();
	    answersPanel.setWidth("590px");
	    
	    submitButton = new Button(constants.Submit());
	    submitButton.setStyleName("button");
	    
	    submitButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				HandleSubmitEvent();
			}
		});
		
	    //FlowPanel container = new FlowPanel();
		vPanel = new FlowPanel();
		vPanel.setStyleName("addPostBody");
		//SimplePanel simple = new SimplePanel();
		//simple.setStyleName("simple");
		//container.add(simple);
		//container.add(vPanel);
	    
	    formPanel = new FormPanel();
	    formPanel.setMethod("POST");
	    formPanel.add(vPanel);
	    formPanel.setStyleName("addPostForm");
	  
		ViewsFrame plane = new ViewsFrame();
		plane.addStyleName("addPostFrame");
		
		info = new FlowPanel();
		info.setStyleName("info");
		HTML header = new HTML();
		header.setHTML("<b>"+constants.Information()+"</b><br><br>");
		HTML requirement1 = new HTML();
		requirement1.setHTML(constants.Requirement1()+"<br><br>");
		HTML requirement2 = new HTML();
		requirement2.setHTML(constants.Requirement2());
		info.add(header);
		info.add(requirement1);
		info.add(requirement2);
		//vPanel.add(info);
		//container.add(simple);
		
		plane.add(formPanel);
		//plane.add(info);
	    initWidget(plane);
	}
	

	public AddPostView(AddPostControler controler){
		this();
		this.controler = controler;
		
	}
	
	protected void HandleSubmitEvent() {
		String content = tb.getValue();
		List<String> answersContent = getAnswersContent();
		String embedContent = embedView.getEmbed();
		PopupPanel popup = new PopupPanel();
		HTML load = new HTML();
		load.setHTML("<style>.bubblingG {text-align: center;width:80px;height:50px; margin:auto;}.bubblingG span {display: inline-block;vertical-align: middle;width: 10px;height: 10px;margin: 25px auto;background: #000000;-moz-border-radius: 50px;-moz-animation: bubblingG 1.3s infinite alternate;-webkit-border-radius: 50px;-webkit-animation: bubblingG 1.3s infinite alternate;-ms-border-radius: 50px;-ms-animation: bubblingG 1.3s infinite alternate;-o-border-radius: 50px;-o-animation: bubblingG 1.3s infinite alternate;border-radius: 50px;animation: bubblingG 1.3s infinite alternate;}#bubblingG_1 {-moz-animation-delay: 0s;-webkit-animation-delay: 0s;-ms-animation-delay: 0s;-o-animation-delay: 0s;animation-delay: 0s;}#bubblingG_2 {-moz-animation-delay: 0.39s;-webkit-animation-delay: 0.39s;-ms-animation-delay: 0.39s;-o-animation-delay: 0.39s;animation-delay: 0.39s;}#bubblingG_3 {-moz-animation-delay: 0.78s;-webkit-animation-delay: 0.78s;-ms-animation-delay: 0.78s;-o-animation-delay: 0.78s;animation-delay: 0.78s;}@-moz-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-moz-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-moz-transform: translateY(-21px);}}@-webkit-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-webkit-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-webkit-transform: translateY(-21px);}}@-ms-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-ms-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-ms-transform: translateY(-21px);}}@-o-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-o-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-o-transform: translateY(-21px);}}@keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;transform: translateY(-21px);}}</style><div class='bubblingG'><span id='bubblingG_1'></span><span id='bubblingG_2'></span><span id='bubblingG_3'></span></div>");
		popup.add(load);
		popup.setGlassEnabled(true);
		popup.show();
		
		controler.HandleSubmitEvent(content, answersContent, embedContent);
	}

	public void initView() {
		vPanel.clear();
				
	    errorLabel.setText("");
	    vPanel.add(errorLabel);
	    
	    tb.setValue("");
	    vPanel.add(tb);
	    
	    embedView.clear();
	    vPanel.add(embedView);
	    
	    answersPanel.clear();
	    answers.clear();
	    showAnswers(2);
	    HorizontalPanel operationAnswersPanel = new HorizontalPanel();
	    Button addAnswerButton = new Button();
	    addAnswerButton.setStyleName("button");
	    addAnswerButton.addStyleName("addButton");
	    addAnswerButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addAnswer();
				
			}
		});
	    
	    operationAnswersPanel.add(answersPanel);
	    operationAnswersPanel.add(addAnswerButton);
	    operationAnswersPanel.setCellVerticalAlignment(addAnswerButton, HasVerticalAlignment.ALIGN_BOTTOM);
	    vPanel.add(operationAnswersPanel);
	    
	    FlowPanel submitButtonDiv = new FlowPanel();
	    submitButtonDiv.setStyleName("submitDiv");
	    submitButtonDiv.add(submitButton);
	    vPanel.add(submitButtonDiv);
	    vPanel.add(info);
	}
	
	protected void addAnswer() {
		final AddAnswerView answer = new AddAnswerView();
		answer.getDelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deleteAnswer(answer);
				
			}
		});
		answers.add(answer);			
		answersPanel.add(answer.asWidget());
		showDelButtons();
	}

	protected void deleteAnswer(AddAnswerView answer) {
		answers.remove(answer);			
		answersPanel.remove(answer.asWidget());
		showDelButtons();
	}

	private void showDelButtons() {
		if (answers.size()>2) {
			for (AddAnswerView answer : answers) {
				answer.setDelButtonVisible(true);
			}
		} else {
			for (AddAnswerView answer : answers) {
				answer.setDelButtonVisible(false);
			}
		}
		
	}

	private void showAnswers(Integer numberOfAnswers){
		for (int i = 0; i < numberOfAnswers; i++) {
			addAnswer();
		}
	}
	
	public HasClickHandlers getSubmitButton(){
		return submitButton;
	}
	
	public HasValue<String> getContent(){
		return tb;
	}
	
	public List<String> getAnswersContent(){
		List<String> answersContent = new ArrayList<String>();
		for (AddAnswerView answer : answers) {
			answersContent.add(answer.getAnswerConent().getValue());
		}
		return answersContent;
	}
	
	public void setError(String error){
		errorLabel.setHTML(error);
	}
	
	public HasHandlers getForm(){
		return formPanel;
	}
	
	
	private interface OnPasteHandler extends EventHandler {
		  void onPaste(OnPasteEvent event);
	}
	
	private static class OnPasteEvent extends GwtEvent<OnPasteHandler> {
		public static final Type<OnPasteHandler> TYPE = new Type<OnPasteHandler>();
		private final String value;
		
		public OnPasteEvent(String value) {
			super();
			this.value = value;
		} 
		 
		@Override
		public GwtEvent.Type<OnPasteHandler> getAssociatedType() {
			 return TYPE;
		}

		@Override
		protected void dispatch(OnPasteHandler handler) {
			handler.onPaste(this);
			
		}

		public String getValue() {
			return value;
		}

	}
	
	private class ExtendedTextArea extends TextArea {

	    public ExtendedTextArea() {
	        super();
	        sinkEvents(Event.ONPASTE);
	    }

	    public HandlerRegistration addOnPaste(OnPasteHandler handler) {
	        // Initialization code
	        return addHandler(handler, OnPasteEvent.TYPE);
	      }
	    
	    @Override
	    public void onBrowserEvent(Event event) {
	        super.onBrowserEvent(event);
	        switch (DOM.eventGetType(event)) {
	            case Event.ONPASTE:
	            	Scheduler.ScheduledCommand cmd = new Scheduler.ScheduledCommand() {

	                    @Override
	                    public void execute() {
	                    	//ValueChangeEvent.fire(ExtendedTextArea.this, getText());
	                    	OnPasteEvent event = new OnPasteEvent(getText());
	                    	ExtendedTextArea.this.fireEvent(event);
	                    }

	                };
	            	Scheduler.get().scheduleDeferred(cmd);
	                break;
	        }
	    }
	}

	public void setEmbed(String html) {
		embedView.setEmbed(html);
	}
	
	public void setEmbedLoadImg(){
		embedView.setLoadImg();
	}
	
	public void setEmbedError(){
		embedView.setEmbedError();
	}

}


