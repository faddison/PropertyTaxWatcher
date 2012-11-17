package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PageMenu extends HorizontalPanel
{

	public PageMenu()
	{
		initMenu();
	}

	private void initMenu()
	{
		Button home = new Button(PropertyTaxWatcher.HOME_PAGE.getName());
		Button search = new Button(PropertyTaxWatcher.SEARCH_PAGE.getName());
		Button compare = new Button(PropertyTaxWatcher.COMPARE_PAGE.getName());
		Button about = new Button(PropertyTaxWatcher.ABOUT_PAGE.getName());
		Button login = new Button(PropertyTaxWatcher.LOGIN_PAGE.getName());

		home.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				PropertyTaxWatcher.HOME_PAGE.loadPage();
			}
		});

		search.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				PropertyTaxWatcher.SEARCH_PAGE.loadPage();
			}
		});

		this.add(home);
		this.add(search);
		this.add(compare);
		this.add(about);
		this.add(login);
	}

}
