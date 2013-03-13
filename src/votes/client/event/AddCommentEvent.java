package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddCommentEvent extends GwtEvent<AddCommentEventHandler> {
  public static Type<AddCommentEventHandler> TYPE = new Type<AddCommentEventHandler>();
  private final String content;
  private final Long postId;
  private final String author;
  
  public AddCommentEvent(String content, Long postId, String author) {
	super();
	this.postId = postId;
	this.content = content;
	this.author = author;
}


@Override
  public Type<AddCommentEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddCommentEventHandler handler) {
    handler.onAddContact(this);
  }

public String getContent() {
	return content;
}

public Long getPostId() {
	return postId;
}

public String getAuthor() {
	return author;
}

}
