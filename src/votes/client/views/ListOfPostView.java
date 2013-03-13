package votes.client.views;


import votes.shared.PostTransported;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class ListOfPostView extends View {
	private final FlowPanel vPanel;
	private PostSubView[] PostsViews = new PostSubView[10];
	private ListChangerView listChanger = new ListChangerView();
	
	public ListOfPostView(){
		vPanel = new FlowPanel();
	    //vPanel.setSpacing(5);
	    vPanel.setWidth("100%");
	    
	    
	    for (int i = 0; i < 10; i++) {
	    	PostsViews[i]= new PostSubView();
	    	vPanel.add(PostsViews[i].asWidget());
	    }
	    vPanel.add(listChanger.asWidget());
	    initWidget(vPanel);
	}

	public void setRowData(PostTransported[] Contents, HandlerManager eventBus){
		Window.scrollTo(0, 0);
		int numberOfContents = Contents.length;
		for (int i = 0; i < PostsViews.length; i++) {
			if (i>numberOfContents-1){
				PostsViews[i].clear();
			}else{
				PostsViews[i].setPostContent(Contents[i]);
				PostsViews[i].setEventHandlers(eventBus);
			}
		}
	}
	
	
	public void setCurrentMaxPages(Long currentPage, Long maxPage) {
		listChanger.setChangerPropertis(currentPage, maxPage);
	}

//	public void setEventHandlers(HandlerManager eventBus) {
//		for (int i = 0; i < PostsViews.length; i++) {
//			PostsViews[i].setEventHandlers(eventBus);
//	    }		
//	}	
	
}
