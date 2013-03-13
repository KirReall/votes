package votes.client.event;

import votes.client.views.AnswerSelectorView;

import com.google.gwt.event.shared.GwtEvent;

public class GetFriendsAnswersEvent extends GwtEvent<GetFriendsAnswersEventHandler> {
  public static Type<GetFriendsAnswersEventHandler> TYPE = new Type<GetFriendsAnswersEventHandler>();
  private final Long Id;
  private final AnswerSelectorView view;
  
  public GetFriendsAnswersEvent(Long Id, AnswerSelectorView view) {
	super();
	this.Id = Id;
	this.view = view;
}

  @Override
  public Type<GetFriendsAnswersEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GetFriendsAnswersEventHandler handler) {
    handler.onGetFriendsAnswers(this);
  }

  public Long getId() {
	return Id;
  }

public AnswerSelectorView getView() {
	return view;
}
  
}
