package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ChangeRateOfOtherAnswerEvent extends GwtEvent<ChangeRateOfOtherAnswerEventHandler> {
  public static Type<ChangeRateOfOtherAnswerEventHandler> TYPE = new Type<ChangeRateOfOtherAnswerEventHandler>();
  private final Long Id;
  private final Long otherId;
  private final Long postId;
  private final Integer totalRating;
  
  public ChangeRateOfOtherAnswerEvent(Long Id, Long otherId, Long postId, Integer totalRating) {
	super();
	this.Id = Id;
	this.otherId = otherId;
	this.postId = postId;
	this.totalRating = totalRating;
}

  @Override
  public Type<ChangeRateOfOtherAnswerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChangeRateOfOtherAnswerEventHandler handler) {
    handler.onChangeRateOtherAnswer(this);
  }

  public Long getId() {
	return Id;
  }
  
  public Integer getTotalRating(){
	  return totalRating;
  }

public Long getOtherId() {
	return otherId;
}

public Long getPostId() {
	return postId;
}
}
