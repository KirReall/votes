package votes.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddPostEventHandler extends EventHandler {
  void onAddContact(AddPostEvent event);
}
