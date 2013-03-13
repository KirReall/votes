package votes.client.views;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class View extends Composite {
	public Widget asWidget() {
		return this;
	}
}
