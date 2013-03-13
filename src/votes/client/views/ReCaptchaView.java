package votes.client.views;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class ReCaptchaView extends Composite { 
    private Label dummyLable = new Label(); 
    private static final String DIV_NAME = "recaptcha_div"; 
    public ReCaptchaView() { 
            this.initWidget(dummyLable); 
            //Label dummyLable = new Label(); 
            //grid.setWidget(0, 0, dummyLable); 
            Element divElement = dummyLable.getElement(); 
            divElement.setAttribute("id", DIV_NAME); 
    } 
    private void createChallenge() { 
            createNewChallengeJSNI(); 
    } 
    public String getChallengeField() { 
            String value = getChallengeJSNI(); 
            //PMLogger.debug("challenge=" + value); 
            return value; 
    } 
    public String getResponse() { 
            String value = getResponseJSNI(); 
            //PMLogger.debug("response=" + value); 
            return value; 
    } 
private native String getResponseJSNI() 
/*-{ 
  return $wnd.Recaptcha.get_response(); 
}-*/; 
private native String getChallengeJSNI() 
/*-{ 
  return $wnd.Recaptcha.get_challenge(); 
}-*/; 
private native void createNewChallengeJSNI() //6LdzccMSAAAAAFYlWEtYjRjELU-hrEzQaGt4Ovc3//6LcN0MISAAAAACaoTuDRycJCJXWBZ0Uq7wCxGyld
/*-{ 
            $wnd.Recaptcha.create("6LdzccMSAAAAAFYlWEtYjRjELU-hrEzQaGt4Ovc3", 
            "recaptcha_div", { 
               theme: "red", 
               lang: "ru",
               callback: $wnd.Recaptcha.focus_response_field 
            }); 
}-*/; 
private native void reloadChallengeJSNI() 
/*-{ 
            $wnd.Recaptcha.reload(); 
}-*/; 
private native void destroyJSNI() 
/*-{ 
            $wnd.Recaptcha.destroy(); 
}-*/; 
    public void createNewChallenge() { 
            reloadChallengeJSNI(); 
    } 
    public void destroyCaptcha() { 
            destroyJSNI(); 
    } 
    @Override 
    protected void onAttach() { 
            super.onAttach(); 
            createChallenge(); 
    } 
}
