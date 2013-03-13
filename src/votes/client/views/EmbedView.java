package votes.client.views;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class EmbedView extends View {
	private HTML embed;
	private FlowPanel closeEmbedPanel;
	private HTML closeEmbedButton;
	
	public EmbedView(){
		embed = new HTML();
	    //embed.setStyleName("content");
		
		closeEmbedPanel = new FlowPanel();
		closeEmbedPanel.setStyleName("closePanel");
	    
	    closeEmbedButton = new HTML();
	    closeEmbedButton.setStyleName("close");	    
	    closeEmbedButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				embed.setHTML("");
				embed.setVisible(false);
				closeEmbedButton.setVisible(false);	
				closeEmbedPanel.setVisible(false);
			}
		});
	    
	    this.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				closeEmbedButton.setVisible(true);	
				closeEmbedPanel.setVisible(true);
			}
		});
	    this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				closeEmbedButton.setVisible(false);
				closeEmbedPanel.setVisible(false);
			}
		});
	    
	    FlowPanel embedPanel = new FlowPanel();
	    
	    closeEmbedPanel.add(closeEmbedButton);
	    embedPanel.add(embed);
	    embedPanel.add(closeEmbedPanel);
	    embedPanel.setStyleName("embed");

	    initWidget(embedPanel);
	    
	}
	
	public void clear(){
		embed.setHTML("");
	    embed.setVisible(false);
	    closeEmbedButton.setVisible(false);
	    closeEmbedPanel.setVisible(false);
	}
	
	public void setEmbed(String html) {
		embed.setHTML(html);
		embed.setVisible(true);
	}
	
	public String getEmbed() {
		return embed.getHTML();
	}
	
	public Boolean isEmbedEmpty(){
		return (!(embed.getText().length()>0));
	}
	
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

}
