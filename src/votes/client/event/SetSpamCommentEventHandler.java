package votes.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SetSpamCommentEventHandler extends EventHandler {
  void onSetSpamComment(SetSpamCommentEvent event);
}
