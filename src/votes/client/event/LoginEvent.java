package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
  public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
  private final Integer service;
 
  
  public LoginEvent(Integer service) {
	super();
	this.service = service;
}


@Override
  public Type<LoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(LoginEventHandler handler) {
    handler.onLogin(this);
  }


public Integer getService() {
	return service;
}

//public String getAccessToken() {
//	return accessToken;
//}


}
