package votes.client.views;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class YaShareView extends Composite { 
    private final Label dummyLable = new Label(); 
    private String DIV_NAME; 
    private String postfix;
    private String title;
    /*public YaShareView() { 
             
            //Label dummyLable = new Label(); 
            //grid.setWidget(0, 0, dummyLable); 
            
    } */
    
    public YaShareView() { 
    	this.initWidget(dummyLable);
        
    }
    
    public void turnOn(String postfix, String title){
    	this.postfix = postfix;
    	this.title = title;
        Element divElement = dummyLable.getElement();
        DIV_NAME = "yashare_div"+postfix; 
        divElement.setAttribute("id", DIV_NAME);        
        //createYaShare();
} 
    
    private void createYaShare() { 
    	String link =Location.getHref().replaceAll(Location.getHash(), "")+"#post&"+postfix;
        createNewYaShareJSNI(DIV_NAME, link, title); 
    } 

private native void createNewYaShareJSNI(String div, String link, String title) 
/*-{ 
	new $wnd.Ya.share({
        element: div,
            elementStyle: {
                'type': 'icon',
                'border': false,
                'quickServices': ['vkontakte','gplus' ,'facebook', 'twitter', 'odnoklassniki', 'surfingbird']
            },
            link: link,
            title: title,
            
	});
}-*/; 


    @Override 
    protected void onAttach() { 
            super.onAttach(); 
            createYaShare(); 
    }
}
