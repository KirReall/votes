package votes.client.views;

import votes.client.Token;
import votes.client.VotesConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class LinkListView extends View {
	private VotesConstants constants = GWT.create(VotesConstants.class);
	private ULPanel list = new ULPanel();
	private FlowPanel panel = new FlowPanel();

	public LinkListView(){

		panel.addStyleName("linkList");
		panel.add(list);
		initWidget(panel);
	}
	
	public void activateLink(String token, Boolean userLogin){
		list.clear();
		
		Widget listlink = null;
		Widget lastlink = null;
		Widget bestlink = null;
		Widget mylink = null;
		Widget addlink = null;
		HTML aboutlink = new HTML("<a href='/about.html'>"+constants.About()+"</a>");
		
		if (token.startsWith(Token.LIST)) {
			listlink = new HTML("<strong>"+constants.List()+"</strong>");
			lastlink = new Hyperlink(constants.Last(), "last&1");
			bestlink = new Hyperlink(constants.Best(), "best&1");
			mylink = new Hyperlink(constants.My(), "my&1");
			addlink = new Hyperlink(constants.Add(), "add");
		}else if (token.startsWith(Token.LAST)) {
			listlink = new Hyperlink(constants.List(),"list&1");
			lastlink = new HTML("<strong>"+constants.Last()+"</strong>");
			bestlink = new Hyperlink(constants.Best(), "best&1");
			mylink = new Hyperlink(constants.My(), "my&1");
			addlink = new Hyperlink(constants.Add(), "add");
		} else if (token.startsWith(Token.BEST)) {
			listlink = new Hyperlink(constants.List(),"list&1");
			lastlink = new Hyperlink(constants.Last(), "last&1");
			bestlink = new HTML("<strong>"+constants.Best()+"</strong>");
			mylink = new Hyperlink(constants.My(), "my&1");
			addlink = new Hyperlink(constants.Add(), "add");
		} else if (token.equals(Token.ADD)) {
			listlink = new Hyperlink(constants.List(),"list&1");
			lastlink = new Hyperlink(constants.Last(), "last&1");
			bestlink = new Hyperlink(constants.Best(), "best&1");
			mylink = new Hyperlink(constants.My(), "my&1");
			addlink = new HTML("<strong>"+constants.Add()+"</strong>");
		} else if (token.startsWith(Token.MY)) {
			listlink = new Hyperlink(constants.List(),"list&1");
			lastlink = new Hyperlink(constants.Last(), "last&1");
			bestlink = new Hyperlink(constants.Best(), "best&1");
			mylink = new HTML("<strong>"+constants.My()+"</strong>");
			addlink = new Hyperlink(constants.Add(), "add");
		} else {
			listlink = new Hyperlink(constants.List(),"list&1");
			lastlink = new Hyperlink(constants.Last(), "last&1");
			bestlink = new Hyperlink(constants.Best(), "best&1");
			mylink = new Hyperlink(constants.My(), "my&1");
			addlink = new Hyperlink(constants.Add(), "add");
		}
		list.add(listlink);
		list.add(lastlink);
		list.add(bestlink);
		list.add(mylink);
		list.add(addlink);
		list.add(aboutlink);
		
		listlink.setStyleName("linkElement");
		lastlink.setStyleName("linkElement");
		bestlink.setStyleName("linkElement");
		mylink.setStyleName("linkElement");
		addlink.setStyleName("linkElement");
		aboutlink.setStyleName("linkElement");
		
		if (!userLogin){
			mylink.setVisible(false);
		}
	}
	

	public class ULPanel extends ComplexPanel { 
		private UListElement list; 
		public ULPanel() { 
			list = Document.get().createULElement(); 
			//list.setId("linkList");
			setElement(list); 
		} 
		@Override 
		public void add(Widget child) { 
			Element li = Document.get().createLIElement().cast(); 
			list.appendChild(li); 
			//li.appendChild(child.getElement());
			super.add(child, li); 
		}
		
		@Override 
		public void clear() { 
			super.clear();
			Node li = list.getLastChild();
			while (!(li == null)) {
				list.removeChild(li);
				li = list.getLastChild();
			}
			//li.appendChild(child.getElement());
			 
		}
	}	   
	
}
