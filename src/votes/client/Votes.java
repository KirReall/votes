package votes.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Votes implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		HistoryInitialization();
		
	    
	}
	
	private void HistoryInitialization(){
		HistoryValueChangeHandler historyValueChangeHandler = new HistoryValueChangeHandler();
		History.addValueChangeHandler(historyValueChangeHandler);
		String token = History.getToken();
		if (token.isEmpty()){
			History.newItem("list&1");	
		} else {
			History.fireCurrentHistoryState();
		}
	}
}
