package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * AboutPage class. This class represents the about page on the Property Tax
 * Watcher web app.
 * 
 * @author Hubert
 */
public class AboutPage extends Page
{
	private static final String DESCRIPTION = "<div class = \"hero-unit\">"
			+ "<p>This is the Property Tax Watcher application!<br /><br />"
			+ "A project created by the Gold Diggers team for CPSC 310 at UBC<br /><br />"
			+ "The property tax watcher application is developed to search for "
			+ "propertys in the Vancouver area and compare multiple search results. "
			+ "The data set used in this application is from " 
			+ "<a href=\"http://data.vancouver.ca/datacatalogue/index.htm\"> Data Vancouver </a>."
			+ "<br /><br />Developed By:"
			+ "<br />    Hubert Ngu" + "<br />    Ryan Taylor"
			+ "<br />    Fraser Addison" + "<br />    Terry Guo"
			+ "<br /></p></div>";

	public AboutPage()
	{
		super("About");
	}

	@Override
	public void loadPage()
	{
		super.loadPage();

		VerticalPanel layout = new VerticalPanel();

		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.add(new HTML(DESCRIPTION));
		layout.setStylePrimaryName("container-about");

		RootPanel.get().add(layout);
	}

}
