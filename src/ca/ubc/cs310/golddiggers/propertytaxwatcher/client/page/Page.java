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

		// Draw the title and menu.
		HTML title = new HTML("<center><h3 class = \"brand\">Property Tax Watcher</h3></center>");
		HTML divider = new HTML("<hr>");
		root.add(title);
		root.add(pageMenu);
		root.add(divider);
	}

	public String getName()
	{
		return this.name;
	}
}
