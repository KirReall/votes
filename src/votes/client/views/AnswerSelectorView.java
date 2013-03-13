package votes.client.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import votes.client.event.AddOtherAnswerEvent;
import votes.client.event.ChangeRateOfAnswerEvent;
import votes.client.event.ChangeRateOfOtherAnswerEvent;
import votes.client.event.GetFriendsAnswersEvent;
import votes.client.event.GetOtherAnswersEvent;
import votes.client.event.GetOtherFriendsAnswersEvent;
import votes.client.oauth.Friend;
import votes.shared.AnswerTransported;
import votes.shared.UserAccountInfo;
import votes.shared.UserVoteTransported;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;

public class AnswerSelectorView extends View {
	private final PostSubView postView;
	private final FlowPanel plane = new FlowPanel();
	private List<AnswerTransported> answers = new ArrayList<AnswerTransported>();
	private List<IAnswerView> answerViews = new ArrayList<IAnswerView>();
	private String[] answersColors = new String[10];
	private FlowPanel otherAnswerPanel = new FlowPanel();
	private FlowPanel newOtherAnswerPanel = new FlowPanel();
	private TextBox answer = new TextBox();
	private Button addNewAnswer = new Button();
	private Long postId;
	private Boolean otherAnswerMode = false;
	private Boolean fulled;
	private final DisclosurePanel otherPanel = new DisclosurePanel();
	private AnswerTransported otherAnswer;
	private OtherAnswerView otherAnswerView;
	private Boolean answered = false;
	private Boolean otherAnswerIsHere = false;
	private HandlerRegistration otherOpenHandlerRegistration;
	private HandlerRegistration addNewHandlerRegistration;
	private Integer totalRating;
	
	public AnswerSelectorView(PostSubView postView) {
		this.postView = postView;
		fulled = false;
		initAnswerColors();
		otherPanel.setWidth("100%");
		otherPanel.add(otherAnswerPanel);
		newOtherAnswerPanel.setVisible(false);		
		answer.addStyleName("answerBox");
		answer.addStyleName("textBox");
		addNewAnswer.setStyleName("button");
		addNewAnswer.addStyleName("addButton");
		
		
		initWidget(plane);
	}
	
	private void initAnswerColors() {
		answersColors[0]="background1";
		answersColors[1]="background2";
		answersColors[2]="background3";
		answersColors[3]="background4";
		answersColors[4]="background5";
		answersColors[5]="background6";
	}

	public void setAnswers(List<AnswerTransported> answers){
		fulled = true;
		otherAnswerMode = false;
		otherAnswerIsHere = false;
		answered = false;
		plane.clear();
		otherAnswerPanel.clear();
		otherPanel.setOpen(false);
		
		this.answers = answers;
		Integer i = 0;
		otherAnswerIsHere = false;
		for (AnswerTransported answer : answers) {
			
			int colorNumber = i%6;
			
			if (!(answer.getContent().equals(AnswerTransported.OTHER))) {
				IAnswerView answerView = new AnswerView(answer);
				answerView.setColor(answersColors[colorNumber]);
				answerViews.add(answerView);
		        plane.add(answerView.asWidget());
		        i++;
			} else {
				IAnswerView answerView = new OtherAnswerView(answer);
				otherAnswer = answer;
				answerViews.add(answerView);
				otherAnswerView = (OtherAnswerView) answerView;
				otherAnswerIsHere = true;
				
			}
		}
		if (otherAnswerIsHere){ 
			int colorNumber = i%6;
			otherAnswerView.setColor(answersColors[colorNumber]);
			plane.add(otherAnswerView.asWidget()); 
			plane.add(otherPanel);
			otherPanel.setHeader(otherAnswerView.asWidget());
			//otherPanel.(otherPanel);
			otherAnswerPanel.add(newOtherAnswerPanel);
			newOtherAnswerPanel.setVisible(false);
			
			otherPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

				@Override
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					otherAnswerView.setOpenImg();
					
				}
				
			});
			otherPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
				
				@Override
				public void onClose(CloseEvent<DisclosurePanel> event) {
					otherAnswerView.setCloseImg();
					
				}
			});
		}
		
		totalRating = countUpTotalRatingOfAnswers();
	}
	
	public void addOtherAnswers(List<AnswerTransported> answers, HandlerManager eventBus){
		otherAnswerMode = true;
		Integer i = answers.size();
		this.answers.addAll(answers);
		if (!answered){
			answer.setValue("");
			newOtherAnswerPanel.add(answer);
			newOtherAnswerPanel.add(addNewAnswer);
			newOtherAnswerPanel.setVisible(true);
		}
		final FlowPanel flowPanel = new FlowPanel();
		
		for (AnswerTransported answer : answers) {
			AnswerView answerView = new AnswerView(answer);
			answerView.setOtherAnswer(true);
			int colorNumber = i%6;
			answerView.setColor(answersColors[colorNumber]);
			answerViews.add(answerView);
			addAnswerClickHandler(answerView, eventBus);
			flowPanel.add(answerView.asWidget());
		    i++;			
		}
		otherAnswerPanel.add(flowPanel);
		totalRating = countUpTotalRatingOfAnswers();
		if (answered){
			showAnswersRate();
			eventBus.fireEvent(new GetOtherFriendsAnswersEvent(postId, this));
		}
	}

	public void setEventHandlers(final HandlerManager eventBus) {
		
		
		for (IAnswerView answerView : answerViews) {
			if (!answerView.isOther()){
			addAnswerClickHandler(answerView, eventBus);
			}
			
		}
		
		final AnswerSelectorView view = this;
		
		if (answered) {
			eventBus.fireEvent(new GetFriendsAnswersEvent(postId, view));
		}
		
		if (otherOpenHandlerRegistration!=null){
			otherOpenHandlerRegistration.removeHandler();
		}
		otherOpenHandlerRegistration = otherPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				if (!otherAnswerMode){
					eventBus.fireEvent(new GetOtherAnswersEvent(postId,view));
				}
			}
		});
		
		if (addNewHandlerRegistration!=null){
			addNewHandlerRegistration.removeHandler();
		}
		
		addNewHandlerRegistration = addNewAnswer.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddOtherAnswerEvent(answer.getText(), postId));
				eventBus.fireEvent(new ChangeRateOfAnswerEvent(otherAnswer.getId(), postId));
				otherAnswerView.setSelected();
				AnswerTransported newAnswer = new AnswerTransported(Long.valueOf(0), answer.getText(), 1);
				answers.add(newAnswer);
				AnswerView answerView = new AnswerView(newAnswer);
				answerView.setSelected();
				int colorNumber = 5;
				answerView.setColor(answersColors[colorNumber]);
				answerViews.add(answerView);
		        Integer newAnswerIndex = otherAnswerPanel.getWidgetIndex(newOtherAnswerPanel);
		        otherAnswerPanel.remove(newOtherAnswerPanel);
		        otherAnswerPanel.insert(answerView,newAnswerIndex);
		        otherAnswerView.increaseAnswerRate();
		        postView.showAnswersRate();
		        
			}
		});
	}
	
	private void addAnswerClickHandler(final IAnswerView answerView, final HandlerManager eventBus) {
		final AnswerSelectorView view = this;
		answerView.getAnswerButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				totalRating = countUpTotalRatingOfAnswers()+1;
				
				remooveNewAnswerBox();
				
				if (answerView.isOtherAnswer()) {
					eventBus.fireEvent(new ChangeRateOfOtherAnswerEvent(answerView.getAnswerId(), otherAnswer.getId(), postId, totalRating+1));
					eventBus.fireEvent(new ChangeRateOfAnswerEvent(otherAnswer.getId(), postId));
					otherAnswerView.increaseAnswerRate();
					otherAnswerView.setSelected();
				} else {
					eventBus.fireEvent(new ChangeRateOfAnswerEvent(answerView.getAnswerId(), postId));
				}
				answerView.increaseAnswerRate();
				answerView.setSelected();
				
				postView.showAnswersRate();
				eventBus.fireEvent(new GetFriendsAnswersEvent(postId, view));
				if (otherAnswerIsHere&&!otherAnswerMode) {
					eventBus.fireEvent(new GetOtherFriendsAnswersEvent(postId, view));
				}
			}
		});
	}

	public Boolean isFulled(){
		return fulled;
	}
	
	public void setOtherAnswerMode(Long postId){
		RadioButton answerButton = new RadioButton("answer");
		newOtherAnswerPanel.add(answerButton);
		newOtherAnswerPanel.add(answer);
		newOtherAnswerPanel.add(addNewAnswer);
		this.postId = postId;
		this.otherAnswerMode = true;
		newOtherAnswerPanel.setVisible(true);
	}

	
	public void showAnswersRate(){
		setAnswered(true);
		if (otherAnswerIsHere&&!otherAnswerMode) {
			otherAnswerView.setVisible(true);
		}
		for (IAnswerView oneOfAnswerView : answerViews) {
			oneOfAnswerView.showAnswerRate(totalRating);
		}
	}

	public void showAnswers(){
		for (IAnswerView oneOfAnswerView : answerViews) {
			oneOfAnswerView.showAnswer();
		}
	}
	
	protected Integer countUpTotalRatingOfAnswers() {
		Integer totalRating = 0;
		for (AnswerTransported answer : answers) {
			if (!(otherAnswerMode&&(answer.getContent().equals(AnswerTransported.OTHER)))){
				totalRating = totalRating + answer.getRate();
			}
		}
		return totalRating;
	}

	public void setPostId(Long id) {
		this.postId = id;
		
	}
	
	public void remooveNewAnswerBox(){
		if (otherAnswerMode){
			otherAnswerPanel.remove(newOtherAnswerPanel);
		}
	}

	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}

	public Boolean isAnswered() {
		return answered;
	}
	
	public void setFriendsAnswers(HashMap<Long, ArrayList<UserAccountInfo>> friendsAnswers) {
		for (IAnswerView answerView : answerViews) {
			Long answerId = answerView.getAnswerId();
			if (friendsAnswers.containsKey(answerId)) {
				answerView.showFriends(friendsAnswers.get(answerId));
			}			
		} 
	}
	
}
