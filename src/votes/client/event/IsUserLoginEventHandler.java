package votes.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface IsUserLoginEventHandler extends EventHandler {
  void onUserLoginRequest(IsUserLoginEvent event);
}
