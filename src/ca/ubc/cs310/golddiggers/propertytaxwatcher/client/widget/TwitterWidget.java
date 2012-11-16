package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * TwitterWidget class. This class creates a twitter widget using Twitter's API.
 * 
 * This code was taken from:
 * http://stackoverflow.com/questions/8744663/how-to-insert
 * -twitter-widget-into-a-gwt-view
 * 
 * Modifications: - Changed twitter feed from search to user profile for
 * golddiggers310
 */
public class TwitterWidget extends Composite
{
	private static final String TWITTER_PROPERTY_TAX_WATCHER = "golddiggers310";

	private JavaScriptObject widgetJsObj = null;
	private final FlowPanel twPanel;
	private final boolean destroyOnUnload;

	public TwitterWidget()
	{
		this(true);
	}

	public TwitterWidget(boolean destroyOnUnload)
	{
		this.destroyOnUnload = destroyOnUnload;
		twPanel = new FlowPanel();
		twPanel.getElement().setId(DOM.createUniqueId());
		initWidget(twPanel);
	}

	@Override
	protected void onLoad()
	{
		super.onLoad();

		Callback<Void, Exception> callback = new Callback<Void, Exception>()
		{

			@Override
			public void onSuccess(Void result)
			{
				if (nativeEnsureTwitterWidgetJsLoadedAndSetToWnd())
				{
					renderAndStart();
				} else
				{
					GWT.log("even though success has been called, the twitter widget js is still not available");
					// some logic maybe keep checking every second for 1 minute
				}
			}

			@Override
			public void onFailure(Exception reason)
			{
				// TODO Auto-generated method stub
				GWT.log("exception loading the twitter widget javascript",
						reason);
			}

		};

		boolean isTwitterWidgetAvailable = nativeEnsureTwitterWidgetJsLoadedAndSetToWnd();
		if (isTwitterWidgetAvailable)
		{
			renderAndStart();
		} else
		{
			ScriptInjector.fromUrl("http://widgets.twimg.com/j/2/widget.js")
					.setWindow(ScriptInjector.TOP_WINDOW).setCallback(callback)
					.inject();
		}
	}

	@Override
	protected void onUnload()
	{
		super.onUnload();

		if (widgetJsObj != null)
		{
			// need to manually destroy so that attached events get removed
			if (destroyOnUnload)
			{
				nativeDestroyTwitterWidget(widgetJsObj);
			} else
			{
				nativeStopTwitterWidget(widgetJsObj);
			}
		}
	}

	private native JavaScriptObject nativeRenderStartTwitterWidget(
			String domId, String user) /*-{
		var twObj = new $wnd.TWTR.Widget({
			version : 2,
			id : domId,
			type : 'profile',
			interval : 30000,
			title : 'ProperyTaxWatcher',
			subject : 'Gold Diggers',
			width : 250,
			height : 300,
			theme : {
				shell : {
					background : '#8ec1da',
					color : '#ffffff'
				},
				tweets : {
					background : '#ffffff',
					color : '#444444',
					links : '#1985b5'
				}
			},
			features : {
				scrollbar : false,
				loop : true,
				live : true,
				behavior : 'default'
			}
		}).render().setUser(user).start();
		return twObj;
	}-*/;

	private native boolean nativeEnsureTwitterWidgetJsLoadedAndSetToWnd() /*-{
		// this only works when TWTR has been properly loaded to $wnd directly
		if (!(typeof $wnd.TWTR === "undefined") && !(null === $wnd.TWTR)) {
			return true;
		}
		return false;
	}-*/;

	private native JavaScriptObject nativeStopTwitterWidget(
			JavaScriptObject twObj) /*-{
		return twObj.stop();
	}-*/;

	private native JavaScriptObject nativeDestroyTwitterWidget(
			JavaScriptObject twObj) /*-{
		return twObj.destroy();
	}-*/;

	private void renderAndStart()
	{
		widgetJsObj = nativeRenderStartTwitterWidget(twPanel.getElement()
				.getId(), TWITTER_PROPERTY_TAX_WATCHER);
	}

}