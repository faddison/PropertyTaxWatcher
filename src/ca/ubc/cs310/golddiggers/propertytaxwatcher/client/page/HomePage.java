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
	private static final String description = "<div class = \"hero-unit\"><p>This is the Property Tax Watcher application!\n"
			+ "You can search for properties in Vancouver "
			+ "using the searching feature and compare "
			+ "the values using the comparison feature!\n</p></div>";

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
		layout.add(new HTML(description));
		layout.add(twitterWidget);
		layout.add(plusOneWidget);
		layout.setStylePrimaryName("container-narrow");

		RootPanel.get().add(layout);
	}
}
