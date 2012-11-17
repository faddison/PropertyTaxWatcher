package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.PageMenu;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class Page
{
	public String name;

	protected Page(String name)
	{
		this.name = name;
	}

	/**
	 * Clears the page and reloads the page menu and title.
	 */
	public void loadPage()
	{
		RootPanel root = RootPanel.get();

		// Clear any widgets on the RootPanel.
		root.clear();

		// Create the pages menu.
		PageMenu pageMenu = new PageMenu();
		root.add(pageMenu);

		// Draw the title.
		HTML title = new HTML("<h1>Property Tax Watcher</h1>");
		root.add(title);
	}

	public String getName()
	{
		return this.name;
	}
}
