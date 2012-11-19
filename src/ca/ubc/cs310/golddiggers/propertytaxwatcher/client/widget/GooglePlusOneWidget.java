package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * GooglePlusOneWidget class. This class repreesnts a google +1 button widget.
 * 
 * @author Hubert Ngu
 */
public class GooglePlusOneWidget extends Composite
{
	private final FlowPanel plusPanel = new FlowPanel();

	public GooglePlusOneWidget()
	{
		this.plusPanel.getElement().setId(DOM.createUniqueId());
		initWidget(this.plusPanel);
	}

	@Override
	protected void onLoad()
	{
		super.onLoad();

		String plusOne = getPlusOneHTMLString(PropertyTaxWatcher.APP_SPOT_URL);
		this.plusPanel.add(new HTML(plusOne));

		Document doc = Document.get();
		ScriptElement script = doc.createScriptElement();
		script.setSrc("https://apis.google.com/js/plusone.js");
		script.setType("text/javascript");
		script.setLang("javascript");
		doc.getBody().appendChild(script);
	}
	
	@Override
	protected void onUnload() {
		plusPanel.clear();
	}

	private static String getPlusOneHTMLString(String url)
	{
		return "Recommend Property Tax Watcher on Google!<br /><g:plusone href=\""
				+ url + "\"></g:plusone>";
	}
}
