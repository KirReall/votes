package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ChangeRateOfAnswerEvent extends GwtEvent<ChangeRateOfAnswerEventHandler> {
  public static Type<ChangeRateOfAnswerEventHandler> TYPE = new Type<ChangeRateOfAnswerEventHandler>();
  private final Long Id;
  private final Long postId;
  
  public ChangeRateOfAnswerEvent(Long Id, Long postId) {
	super();
	this.Id = Id;
	this.postId =postId;
	
}
  
  @Override
  public Type<ChangeRateOfAnswerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChangeRateOfAnswerEventHandler handler) {
    handler.onChangeRateContact(this);
  }

  public Long getId() {
	return Id;
  }

public Long getPostId() {
	return postId;
}
  

}
