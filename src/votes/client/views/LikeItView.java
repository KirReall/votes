package votes.client.views;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class LikeItView extends Composite { 
    private Label dummyLable = new Label(); 
    private final String DIV_NAME; 
    private String postfix;
    /*public YaShareView() { 
             
            //Label dummyLable = new Label(); 
            //grid.setWidget(0, 0, dummyLable); 
            
    } */
    
    public LikeItView(String postfix) { 
    	this.initWidget(dummyLable);
        this.postfix = postfix;
        Element divElement = dummyLable.getElement();
        DIV_NAME = "vk_like"+postfix; 
        divElement.setAttribute("id", DIV_NAME); 
        
} 
    
    private void createYaShare() { 
    	String link =Location.getHref().replaceAll(Location.getHash(), "")+"#post&"+postfix;
        createNewYaShareJSNI(DIV_NAME, link); 
    } 

private native void createNewYaShareJSNI(String div, String link) 
/*-{ 
	$wnd.VK.Widgets.Like("div", {type: "button"});
}-*/; 


    @Override 
    protected void onAttach() { 
            super.onAttach(); 
            createYaShare(); 
    } 
}
