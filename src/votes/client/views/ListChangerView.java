package votes.client.views;

import votes.client.VotesConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class ListChangerView extends View {
	private HorizontalPanel plane = new HorizontalPanel();
	private VotesConstants constants = GWT.create(VotesConstants.class);
	
	public ListChangerView(){
		plane.addStyleName("pages");
		initWidget(plane);
	}
	
	public void setChangerPropertis(Long currentPage, Long maxPage) {
		plane.clear();
		String location = Location.getHash().substring(1, Location.getHash().lastIndexOf("&")+1);
		HTML pages = new HTML(constants.Pages()+": ");
		plane.add(pages);
		Widget link;
		long i = 1;
		while (i<=maxPage) {
			if (i==currentPage) {
				link = new HTML("<strong>"+currentPage.toString()+"</strong>");
			} else {
				link = new Hyperlink(String.valueOf(i),location+String.valueOf(i));
			}	
			plane.add(link);
			if (i==maxPage){
				HTML dot = new HTML(".");
				plane.add(dot);
			} else{
				HTML comma = new HTML(", ");
				plane.add(comma);
			}
			
			if ((maxPage-3>3)&&(((currentPage<3)&&(i==3))||((currentPage>=3)&&(((currentPage-3>2)&&(i==3))||((maxPage-2-currentPage>2)&&(i==currentPage+1)))))){
				HTML dots = new HTML("...");
				HTML comma = new HTML(", ");
				plane.add(dots);
				plane.add(comma);
				if ((currentPage>5)&&(i<currentPage)) {
					i=Math.min(currentPage-1, maxPage-2);
				} else {
					i=maxPage-2;
				}
			} else{
				i++;
			}			
		}
	}
}
