package votes.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface GetPostsEventHandler extends EventHandler {
  void onGetPosts(GetPostsEvent event);
}
