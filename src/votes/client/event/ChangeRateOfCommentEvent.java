package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ChangeRateOfCommentEvent extends GwtEvent<ChangeRateOfCommentEventHandler> {
  public static Type<ChangeRateOfCommentEventHandler> TYPE = new Type<ChangeRateOfCommentEventHandler>();
  private final Long Id;
  private final Boolean increase;
  
  public ChangeRateOfCommentEvent(Long Id, Boolean increase) {
	super();
	this.Id = Id;
	this.increase = increase;
}

  @Override
  public Type<ChangeRateOfCommentEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChangeRateOfCommentEventHandler handler) {
    handler.onChangeRateContact(this);
  }

  public Long getId() {
	return Id;
  }
  
  public Boolean isIncrease(){
	  return increase;
  }
}
