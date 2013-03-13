package votes.client.controlers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EmbedAPI {
	private static final String embedlyURL = "http://api.embed.ly/1/oembed?";
	private static final String key = "7938fbefcdaf4352a5e9f0bb842fa296";
	private final AddPostControler controler;
	
	public EmbedAPI(AddPostControler controler){
		this.controler = controler;
	}
	
	public void getEmbed(final String siteURL){
		//siteURL = URL.encodeQueryString(siteURL);
		String url = URL.encode(embedlyURL+"key="+key+"&url="+siteURL+"&maxwidth=650&maxheight=355&chars=300");
		JsonpRequestBuilder builder = new JsonpRequestBuilder();
		builder.requestObject(url, new AsyncCallback<Embed>() {
			public void onFailure(Throwable throwable) {
				Window.alert("Error: " + throwable);

			}

			public void onSuccess(Embed entry) {
				String html ="";
				String type = entry.getType();
				if (type.equals("photo")){
					html = "<a href='"+siteURL+"' target='_blank'><img src='"+entry.getUrl()+"' style='max-width:650px; max-height:455px;'></a>";
				} else if ((type.equals("video"))||(type.equals("rich"))) {
					html = entry.getHTML();
				} else if (type.equals("link")){
					String imgHtml = "";
					String widthOfDescription = "650";
					String margiLeftDescription = "0";
					if ((entry.getThumbnailUrl() != null)&&(!entry.getThumbnailUrl().isEmpty())){
						imgHtml = "<div style='width:215px; height:130px; float:left;  margin-bottom:15px;'><img src='"+entry.getThumbnailUrl()+"' style='max-width:215px; max-height:130px;'></div>";
						widthOfDescription = "415";
						margiLeftDescription = "235";
					}
					String title = "<h4><a href='"+entry.getUrl()+"'><img src='http://s2.googleusercontent.com/s2/favicons?domain="+getDomain(entry.getProviderUrl())+"' style='max-width:16px; max-height:16px; border:none; margin-right:4px;'>"+entry.getTitle()+"</a></h4>";
					html = imgHtml+"<div style='height:130px; width:"+widthOfDescription+"px; margin-left:"+margiLeftDescription+"px; margin-bottom:15px;'>"+title+"<p>"+entry.getDescription()+"</p></div>";
				}
				if (! html.isEmpty()){
					controler.setEmbed(html);
				}
			}

			private String getDomain(String providerUrl) {
				String domain = providerUrl.replace("https://","");
				domain = domain.replace("http://","");
				domain = domain.replace("/","");
				return domain;
			}
		});
		
	}
			
	static class Embed extends JavaScriptObject {
		protected Embed() {
		}

		public final native String getType() /*-{
			return this.type;
		}-*/;

		public final native String getversion() /*-{
			return this.version;
		}-*/;

		public final native String getTitle() /*-{
			return this.title;
		}-*/;

		public final native String getAuthorName() /*-{
			return this.author_name;
		}-*/;
		
		
		public final native String getProviderName() /*-{
			return this.provider_name;
		}-*/;
		
		public final native String getProviderUrl() /*-{
			return this.provider_url;
		}-*/;
		
		public final native String getCacheAge() /*-{
			return this.cache_age;
		}-*/;
		
		public final native String getThumbnailUrl() /*-{
			return this.thumbnail_url;
		}-*/;
		
		public final native String getThumbnailWidth() /*-{
			return this.thumbnail_width;
		}-*/;
		
		public final native String getThumbnailHeight() /*-{
			return this.thumbnail_height;
		}-*/;
		
		public final native String getDescription() /*-{
			return this.description;
		}-*/;
		
		public final native String getUrl() /*-{
			return this.url;
		}-*/;
		
		public final native String getHTML() /*-{
			return this.html;
		}-*/;
		
		public final native String getWidth() /*-{
			return this.width;
		}-*/;
		
		public final native String getHeight() /*-{
			return this.height;
		}-*/;
		
	}

	static class Response extends JavaScriptObject {
		protected Response() {
		}

		public final native JsArray<Embed> getEntries() /*-{
			return this;
		}-*/;

		public final native String[] getIds() /*-{
			return this;
		}-*/;
	}		
}
