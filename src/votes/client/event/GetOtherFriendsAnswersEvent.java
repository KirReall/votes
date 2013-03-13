package votes.client.event;

import votes.client.views.AnswerSelectorView;

import com.google.gwt.event.shared.GwtEvent;

public class GetOtherFriendsAnswersEvent extends GwtEvent<GetOtherFriendsAnswersEventHandler> {
  public static Type<GetOtherFriendsAnswersEventHandler> TYPE = new Type<GetOtherFriendsAnswersEventHandler>();
  private final Long Id;
  private final AnswerSelectorView view;
  
  public GetOtherFriendsAnswersEvent(Long Id, AnswerSelectorView view) {
	super();
	this.Id = Id;
	this.view = view;
}

  @Override
  public Type<GetOtherFriendsAnswersEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GetOtherFriendsAnswersEventHandler handler) {
    handler.onGetFriendsAnswers(this);
  }

  public Long getId() {
	return Id;
  }

public AnswerSelectorView getView() {
	return view;
}
  
}
