package votes.client.views;

import java.util.ArrayList;
import java.util.List;

import votes.client.VotesConstants;
import votes.client.controlers.AddPostControler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
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
	private ReCaptchaView captcha;
	private Boolean enableCaptcha;
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
					controler.findEmbed(tbValue);
				}
			}
		});
	    
	    embedView = new EmbedView();
	    
	    answersPanel = new FlowPanel();
	    answersPanel.setWidth("590px");
	    
	    submitButton = new Button(constants.Submit());
	    submitButton.setStyleName("button");
	    
	    submitButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (isEnableCaptcha()){
					formPanel.submit();
				} else {
					HandleSubmitEvent();
				}
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
	    formPanel.setAction("/votes/checkcaptcha");
	    formPanel.add(vPanel);
	    formPanel.setStyleName("addPostForm");
	    formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				String result = event.getResults();
				if (("<pre></pre>".equals(result))||("".equals(result))){
					HandleSubmitEvent();
				} else {
					setError("<b>"+constants.BadReCaptcha()+"</b>");
					captcha.createNewChallenge();
				}
			}
		});
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
	    
	    captcha = new ReCaptchaView();
	    vPanel.add(captcha);
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
	
	public void setCaptcha(Boolean enable){
		setEnableCaptcha(enable);
		if (!enable){
			vPanel.remove(captcha);
		}		
	}

	public Boolean isEnableCaptcha() {
		return enableCaptcha;
	}

	public void setEnableCaptcha(Boolean enableCaptcha) {
		this.enableCaptcha = enableCaptcha;
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
	

}


