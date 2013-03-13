package votes.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class AddPostEvent extends GwtEvent<AddPostEventHandler> {
  public static Type<AddPostEventHandler> TYPE = new Type<AddPostEventHandler>();
  private final String content;
  private final List<String> answers;
  private final String embed;
  
  public AddPostEvent(String content, List<String> answers, String embed) {
	super();
	this.content = content;
	this.answers = answers;
	this.embed = embed;
}

  @Override
  public Type<AddPostEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddPostEventHandler handler) {
    handler.onAddContact(this);
  }

public String getContent() {
	return content;
}

public List<String> getAnswers() {
	return answers;
}

public String getEmbed() {
	return embed;
}
}
