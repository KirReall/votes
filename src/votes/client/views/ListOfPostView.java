package votes.client.views;


import votes.shared.PostTransported;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ListOfPostView extends View {
	private final FlowPanel vPanel;
	private PostSubView[] PostsViews = new PostSubView[10];
	private ListChangerView listChanger = new ListChangerView();
	private HTML loadImg = new HTML();
	
	public ListOfPostView(){
		vPanel = new FlowPanel();
	    //vPanel.setSpacing(5);
	    vPanel.setWidth("100%");
	    
	    
	    for (int i = 0; i < 10; i++) {
	    	PostsViews[i]= new PostSubView();
	    	vPanel.add(PostsViews[i].asWidget());
	    }
	    vPanel.add(loadImg);
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
				PostsViews[i].setVisible(true);
				PostsViews[i].setPostContent(Contents[i]);
				PostsViews[i].setEventHandlers(eventBus);
			}
		}
		loadImg.setVisible(false);
	}
	
	
	public void setCurrentMaxPages(Long currentPage, Long maxPage) {
		listChanger.setChangerPropertis(currentPage, maxPage);
	}
	
	public void setLoadImg(){
		for (int i = 0; i < PostsViews.length; i++) {
				PostsViews[i].setVisible(false);
		}	
		loadImg.setVisible(true);
		loadImg.setHTML("<style>.bubblingG {text-align: center;width:80px;height:50px; margin:auto;}.bubblingG span {display: inline-block;vertical-align: middle;width: 10px;height: 10px;margin: 25px auto;background: #000000;-moz-border-radius: 50px;-moz-animation: bubblingG 1.3s infinite alternate;-webkit-border-radius: 50px;-webkit-animation: bubblingG 1.3s infinite alternate;-ms-border-radius: 50px;-ms-animation: bubblingG 1.3s infinite alternate;-o-border-radius: 50px;-o-animation: bubblingG 1.3s infinite alternate;border-radius: 50px;animation: bubblingG 1.3s infinite alternate;}#bubblingG_1 {-moz-animation-delay: 0s;-webkit-animation-delay: 0s;-ms-animation-delay: 0s;-o-animation-delay: 0s;animation-delay: 0s;}#bubblingG_2 {-moz-animation-delay: 0.39s;-webkit-animation-delay: 0.39s;-ms-animation-delay: 0.39s;-o-animation-delay: 0.39s;animation-delay: 0.39s;}#bubblingG_3 {-moz-animation-delay: 0.78s;-webkit-animation-delay: 0.78s;-ms-animation-delay: 0.78s;-o-animation-delay: 0.78s;animation-delay: 0.78s;}@-moz-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-moz-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-moz-transform: translateY(-21px);}}@-webkit-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-webkit-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-webkit-transform: translateY(-21px);}}@-ms-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-ms-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-ms-transform: translateY(-21px);}}@-o-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-o-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-o-transform: translateY(-21px);}}@keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;transform: translateY(-21px);}}</style><div class='bubblingG'><span id='bubblingG_1'></span><span id='bubblingG_2'></span><span id='bubblingG_3'></span></div>");
	}

//	public void setEventHandlers(HandlerManager eventBus) {
//		for (int i = 0; i < PostsViews.length; i++) {
//			PostsViews[i].setEventHandlers(eventBus);
//	    }		
//	}	
	
}
