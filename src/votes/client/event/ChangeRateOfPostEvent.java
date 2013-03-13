package votes.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ChangeRateOfPostEvent extends GwtEvent<ChangeRateOfPostEventHandler> {
  public static Type<ChangeRateOfPostEventHandler> TYPE = new Type<ChangeRateOfPostEventHandler>();
  private final Long Id;
  private final Boolean increase;
  
  public ChangeRateOfPostEvent(Long Id, Boolean increase) {
	super();
	this.Id = Id;
	this.increase = increase;
}

  @Override
  public Type<ChangeRateOfPostEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChangeRateOfPostEventHandler handler) {
    handler.onChangeRateContact(this);
  }

  public Long getId() {
	return Id;
  }
  
  public Boolean isIncrease(){
	  return increase;
  }
}
