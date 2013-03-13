package votes.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddCommentEventHandler extends EventHandler {
  void onAddContact(AddCommentEvent event);
}
