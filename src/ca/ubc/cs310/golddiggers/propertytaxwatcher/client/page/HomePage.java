package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.GooglePlusOneWidget;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.TwitterWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * HomePage class. This class represents the Home Page of the PropertyTaxWatcher
 * web app.
 * 
 * @author Hubert Ngu
 */
public class HomePage extends Page
{
	private static final String description = "<p>This is the Property Tax Watcher application!\n"
			+ "You can search for propertys in Vancouver "
			+ "using the searching feature and compare "
			+ "the values using the comparison feature!\n</p>";

	// Widgets that exist on the home page.
	private TwitterWidget twitterWidget = new TwitterWidget();
	private GooglePlusOneWidget plusOneWidget = new GooglePlusOneWidget();

	public HomePage()
	{
		super("Home");
	}

	/**
	 * Loads the HomePage.
	 */
	@Override
	public void loadPage()
	{
		super.loadPage();

		// If not logged in switch to login page

		// If logged in show the description of product, +1, twitter
		VerticalPanel layout = new VerticalPanel();
		layout.setSize("500px", "500px");
		layout.add(new HTML(description));
		layout.add(twitterWidget);
		layout.add(plusOneWidget);

		RootPanel.get().add(layout);
	}
}
