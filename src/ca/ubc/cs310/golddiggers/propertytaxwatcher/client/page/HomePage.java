package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.GooglePlusOneWidget;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.TwitterWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
	private static final String DESCRIPTION = "<div class = \"hero-unit\">"
			+ "<p>Search and compare propertys in Vancouver area!<br />"
			+ "The entire data set is also viewable on the Data page</p></div>";

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

		// Show the description of propery tax watcher, twitter feed, +1.
		VerticalPanel layout = new VerticalPanel();

		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.add(new HTML(DESCRIPTION));
		layout.add(twitterWidget);
		layout.add(plusOneWidget);
		layout.setStylePrimaryName("container-home");

		RootPanel.get().add(layout);
	}
}
