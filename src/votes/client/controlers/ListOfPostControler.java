package votes.client.controlers;

import votes.client.PostService;
import votes.client.PostServiceAsync;
import votes.client.event.GetPostsEvent;
import votes.client.views.ListOfPostView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;


public class ListOfPostControler extends Controler{
	private static ListOfPostControler INSTANCE;
	private final HandlerManager eventBus;
	private final PostServiceAsync postService = GWT.create(PostService.class);
	private Long currentPage = null; 
	private String orderField;
	private Boolean my;
	//Image img;
	
	private ListOfPostControler(HandlerManager eventBus){
		ListOfPostView view = new ListOfPostView();
		setView(view);
		this.eventBus = eventBus;
		my = false;
	}
	
	public static synchronized ListOfPostControler getControler(HandlerManager eventBus) {
		if (INSTANCE == null) {
			INSTANCE = new ListOfPostControler(eventBus);
		}
		return INSTANCE;
	}
	
	public void showView(final HasWidgets container){
		
		container.clear();
	    //container.add(imgPanel.asWidget());
		
		String token = History.getToken();
		currentPage = Long.valueOf(token.substring(token.indexOf("&")+1));
		eventBus.fireEvent(new GetPostsEvent(currentPage, orderField, my, (ListOfPostView)view));
		showViewWithData(container);
		
				
	}

	protected void showViewWithData(HasWidgets container) {

		super.showView(container);
		postService.getMaxPost(new AsyncCallback<Long>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				History.newItem("fail");
			}@Override
			public void onSuccess(Long result) {
				Long maxPage = result/10;
				if (!(result==maxPage*10)) {
					maxPage = maxPage+1;
				}
				((ListOfPostView)view).setCurrentMaxPages(currentPage, maxPage);
			}
		});
	}

	public void setOrderField(String order) {
		this.orderField = order;
	}

	public String getOrderField() {
		return orderField;
	}

	public Boolean getMy() {
		return my;
	}

	public void setMy(Boolean my) {
		this.my = my;
	}

	
	

}
