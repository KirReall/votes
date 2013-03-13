package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddOtherAnswerEvent extends GwtEvent<AddOtherAnswerEventHandler> {
  public static Type<AddOtherAnswerEventHandler> TYPE = new Type<AddOtherAnswerEventHandler>();
  private final String content;
  private final Long postId;
  
  public AddOtherAnswerEvent(String content, Long postId) {
	super();
	this.postId = postId;
	this.content = content;
}


@Override
  public Type<AddOtherAnswerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddOtherAnswerEventHandler handler) {
    handler.onAddOtherAnswer(this);
  }

public String getContent() {
	return content;
}

public Long getPostId() {
	return postId;
}


}
