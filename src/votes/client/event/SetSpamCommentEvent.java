package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SetSpamCommentEvent extends GwtEvent<SetSpamCommentEventHandler> {
  public static Type<SetSpamCommentEventHandler> TYPE = new Type<SetSpamCommentEventHandler>();
  private final Long Id;
  
  public SetSpamCommentEvent(Long Id) {
	super();
	this.Id = Id;
}

  @Override
  public Type<SetSpamCommentEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SetSpamCommentEventHandler handler) {
    handler.onSetSpamComment(this);
  }

  public Long getId() {
	return Id;
  }
}
