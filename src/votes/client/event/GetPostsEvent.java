package votes.client.event;

import votes.client.views.ListOfPostView;

import com.google.gwt.event.shared.GwtEvent;

public class GetPostsEvent extends GwtEvent<GetPostsEventHandler> {
  public static Type<GetPostsEventHandler> TYPE = new Type<GetPostsEventHandler>();
  private final Long currentPage;
  private final String orderField;
  private final ListOfPostView view;
  private final Boolean my;
  
  public GetPostsEvent(Long currentPage, String orderField, Boolean my, ListOfPostView view) {
	super();
	this.currentPage = currentPage;
	this.orderField = orderField;
	this.view = view;
	this.my = my;
}

  @Override
  public Type<GetPostsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GetPostsEventHandler handler) {
    handler.onGetPosts(this);
  }

  public Long getcurrentPage() {
	return currentPage;
  }

public ListOfPostView getView() {
	return view;
}

public String getOrderField() {
	return orderField;
}

public Boolean getMy() {
	return my;
}
  
}
