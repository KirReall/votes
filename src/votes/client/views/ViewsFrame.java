package votes.client.views;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ViewsFrame extends View{
	FlowPanel body = new FlowPanel();
	FlowPanel leftborder = new FlowPanel();
	FlowPanel rightborder = new FlowPanel();
	FlowPanel center = new FlowPanel();
	FlowPanel topleft = new FlowPanel();
	FlowPanel topright = new FlowPanel();
	FlowPanel bottomleft = new FlowPanel();
	FlowPanel bottomright = new FlowPanel();
	
	public ViewsFrame(){
		
		body.addStyleName("body");
		
		leftborder.addStyleName("leftborder");
		body.add(leftborder);
		
		rightborder.addStyleName("rightborder");
		leftborder.add(rightborder);
		
		center.addStyleName("center");
		rightborder.add(center);
		
		topleft.addStyleName("topleft");
		center.add(topleft);
		
		topright.addStyleName("topright");
		topleft.add(topright);
		
		bottomleft.addStyleName("bottomleft");
		topright.add(bottomleft);
		
		bottomright.addStyleName("bottomright");
		bottomleft.add(bottomright);
		initWidget(body);
	}

	public void add(Widget w){
		bottomright.add(w);
	}
	
	public void clear(){
		bottomright.clear();
	}
	
	public void addStyleName(String styleName){
		leftborder.addStyleName(styleName);
		rightborder.addStyleName(styleName);
		center.addStyleName(styleName);
		topleft.addStyleName(styleName);
		topright.addStyleName(styleName);
		bottomleft.addStyleName(styleName);
		bottomright.addStyleName(styleName);		
	}
	
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}
}
