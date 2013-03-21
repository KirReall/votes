package votes.client.views;

import votes.client.VotesConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;

public class EmbedView extends View {
	private FlowPanel embedPanel;
	private HTML embed;
	private FlowPanel closeEmbedPanel;
	private HTML closeEmbedButton;
	private HTML addEmbedButton;
	private HTML embedError;
	private FlowPanel addEmbedPanel;
	private Button embedOKButton;
	private TextBox embedLinkBox;
	private VotesConstants constants = GWT.create(VotesConstants.class);
	
	public EmbedView(){
		embed = new HTML();
	    //embed.setStyleName("content");
		
		closeEmbedPanel = new FlowPanel();
		closeEmbedPanel.setStyleName("closePanel");
	    
	    closeEmbedButton = new HTML();
	    closeEmbedButton.setStyleName("close");	    
	    closeEmbedButton.setText(constants.Close());
	    closeEmbedButton.setVisible(false);	
		closeEmbedPanel.setVisible(false);
		
	    closeEmbedButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				embed.setHTML("");
				embed.setVisible(false);
				closeEmbedButton.setVisible(false);	
				closeEmbedPanel.setVisible(false);
			}
		});
	    
	    embedPanel = new FlowPanel();
	    
	    embedPanel.addDomHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				closeEmbedButton.setVisible(true);	
				closeEmbedPanel.setVisible(true);
			}
		}, MouseOverEvent.getType());
	    
	    embedPanel.addDomHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				closeEmbedButton.setVisible(false);
				closeEmbedPanel.setVisible(false);
			}
		}, MouseOutEvent.getType());
	    
	    closeEmbedPanel.add(closeEmbedButton);
	    
	    embedError = new HTML();
	    embedError.setVisible(false);
	    embedError.setStyleName("embedError");
	    
	    addEmbedPanel = new FlowPanel();
	    addEmbedPanel.setStylePrimaryName("addEmbed");
	    addEmbedPanel.setVisible(false);
	    
	    addEmbedButton = new HTML();
	    addEmbedButton.setStyleName("addEmbedButton");
	    addEmbedButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addEmbedPanel.setVisible(!addEmbedPanel.isVisible());
				embedError.setVisible(addEmbedPanel.isVisible());
				addEmbedPanel.setStyleDependentName("visible", addEmbedPanel.isVisible());
				embedLinkBox.setValue("");
				embedError.setText("");
			}
		});
	    
	    
	    embedLinkBox = new TextBox();
	    embedLinkBox.setMaxLength(500);
	    embedLinkBox.setStyleName("addEmbedBox");
	    embedOKButton = new Button(constants.Done());	
	    embedOKButton.setStyleName("button");
	    
	    addEmbedPanel.add(embedLinkBox);
	    addEmbedPanel.add(embedOKButton);
	    
	    FlowPanel panel = new FlowPanel();	
	    panel.add(addEmbedButton);
	    panel.add(addEmbedPanel);
	    panel.add(embedError);
	    embedPanel.add(embed);
	    embedPanel.add(closeEmbedPanel);
	    panel.add(embedPanel);
	    embedPanel.setStyleName("embed");

	    initWidget(panel);
	    
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
		colapseAddEmbedPanel();
	}
	
	public String getEmbed() {
		return embed.getHTML();
	}
	
	public Boolean isEmbedEmpty(){
		return (!(embed.getText().length()>0));
	}
	
//	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
//		return embedPanel.addDomHandler(handler, MouseOverEvent.getType());
//	}
//	
//	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
//		return embedPanel.addDomHandler(handler, MouseOutEvent.getType());
//	}
	
	public HandlerRegistration addEmbedDoneHandler(ClickHandler handler) {
		return embedOKButton.addClickHandler(handler);
	}
	
	public String getAddEmbed(){
		return embedLinkBox.getValue();
	}
	
	private void colapseAddEmbedPanel(){
		if (addEmbedPanel.isVisible()){
			addEmbedPanel.setVisible(false);
			addEmbedPanel.setStyleDependentName("visible", false);
			embedError.setVisible(false);
		}
	}
	
	public void setEmbedError(){
		embedError.setText(constants.ErrorGetingEmbed());
	}
	
	public void setLoadImg(){
		embedError.setHTML("<style>.bubblingG {text-align: center;width:80px;height:50px; margin:auto;}.bubblingG span {display: inline-block;vertical-align: middle;width: 10px;height: 10px;margin: 25px auto;background: #000000;-moz-border-radius: 50px;-moz-animation: bubblingG 1.3s infinite alternate;-webkit-border-radius: 50px;-webkit-animation: bubblingG 1.3s infinite alternate;-ms-border-radius: 50px;-ms-animation: bubblingG 1.3s infinite alternate;-o-border-radius: 50px;-o-animation: bubblingG 1.3s infinite alternate;border-radius: 50px;animation: bubblingG 1.3s infinite alternate;}#bubblingG_1 {-moz-animation-delay: 0s;-webkit-animation-delay: 0s;-ms-animation-delay: 0s;-o-animation-delay: 0s;animation-delay: 0s;}#bubblingG_2 {-moz-animation-delay: 0.39s;-webkit-animation-delay: 0.39s;-ms-animation-delay: 0.39s;-o-animation-delay: 0.39s;animation-delay: 0.39s;}#bubblingG_3 {-moz-animation-delay: 0.78s;-webkit-animation-delay: 0.78s;-ms-animation-delay: 0.78s;-o-animation-delay: 0.78s;animation-delay: 0.78s;}@-moz-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-moz-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-moz-transform: translateY(-21px);}}@-webkit-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-webkit-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-webkit-transform: translateY(-21px);}}@-ms-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-ms-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-ms-transform: translateY(-21px);}}@-o-keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;-o-transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;-o-transform: translateY(-21px);}}@keyframes bubblingG {0% {width: 10px;height: 10px;background-color:#000000;transform: translateY(0);}100% {width: 24px;height: 24px;background-color:#FFFFFF;transform: translateY(-21px);}}</style><div class='bubblingG'><span id='bubblingG_1'></span><span id='bubblingG_2'></span><span id='bubblingG_3'></span></div>");
	}
}
