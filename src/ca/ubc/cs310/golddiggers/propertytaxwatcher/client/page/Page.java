package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.PageMenu;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Page class. This is the abstract framework for a page on the
 * PropertyTaxWatcher application.
 * 
 * @author Hubert Ngu
 */
public abstract class Page
{
	public String name;

	protected Page(String name)
	{
		this.name = name;
	}

	/**
	 * Clears the page and reloads the page menu and title. All inheriting
	 * classes should call super.loadPage().
	 */
	public void loadPage()
	{
		RootPanel root = RootPanel.get();

		// Clear any widgets on the RootPanel.
		root.clear();

		// Draw the title and menu.
		root.add(new HTML("<center><h3 class = \"brand\">Property Tax Watcher</h3></center>"));
		root.add(new HTML("<hr>"));
		root.add(new PageMenu());
		root.add(new HTML("<hr>"));
	}

	
	public String getName()
	{
		return this.name;
	}
}
