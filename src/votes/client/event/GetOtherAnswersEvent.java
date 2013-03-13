package votes.client.event;

import votes.client.views.AnswerSelectorView;

import com.google.gwt.event.shared.GwtEvent;

public class GetOtherAnswersEvent extends GwtEvent<GetOtherAnswersEventHandler> {
  public static Type<GetOtherAnswersEventHandler> TYPE = new Type<GetOtherAnswersEventHandler>();
  private final Long Id;
  private final AnswerSelectorView view;
  
  public GetOtherAnswersEvent(Long Id, AnswerSelectorView view) {
	super();
	this.Id = Id;
	this.view = view;
}

  @Override
  public Type<GetOtherAnswersEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GetOtherAnswersEventHandler handler) {
    handler.onGetOtherAnswers(this);
  }

  public Long getId() {
	return Id;
  }

public AnswerSelectorView getView() {
	return view;
}
  
}
