package votes.client.event;

import votes.client.views.PostAndCommentsView;

import com.google.gwt.event.shared.GwtEvent;

public class GetPostEvent extends GwtEvent<GetPostEventHandler> {
  public static Type<GetPostEventHandler> TYPE = new Type<GetPostEventHandler>();
  private final Long postId;
  private final PostAndCommentsView view;
  
  public GetPostEvent(Long postId, PostAndCommentsView view) {
	super();
	this.postId = postId;
	this.view = view;
}

  @Override
  public Type<GetPostEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GetPostEventHandler handler) {
    handler.onGetPost(this);
  }

  public Long getpostId() {
	return postId;
  }

public PostAndCommentsView getView() {
	return view;
}
  
}
