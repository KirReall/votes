package votes.client.event;

import votes.client.views.AddPostView;

import com.google.gwt.event.shared.GwtEvent;

public class IsUserLoginEvent extends GwtEvent<IsUserLoginEventHandler> {
  public static Type<IsUserLoginEventHandler> TYPE = new Type<IsUserLoginEventHandler>();
  private final AddPostView view;
  
  public IsUserLoginEvent(AddPostView view) {
	super();
	this.view = view;
}

  @Override
  public Type<IsUserLoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(IsUserLoginEventHandler handler) {
    handler.onUserLoginRequest(this);
  }

public AddPostView getView() {
	return view;
}

}
