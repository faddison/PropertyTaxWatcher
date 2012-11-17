package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * PageMenu class. This class represents a page menu widget.
 * 
 * @author Hubert Ngu
 */
public class PageMenu extends HorizontalPanel
{
	public PageMenu()
	{
		initMenu();
	}

	private void initMenu()
	{
		// Regular static buttons.
		Button home = new Button(PropertyTaxWatcher.HOME_PAGE.getName());
		Button search = new Button(PropertyTaxWatcher.SEARCH_PAGE.getName());
		Button compare = new Button(PropertyTaxWatcher.COMPARE_PAGE.getName());
		Button data = new Button(PropertyTaxWatcher.DATA_PAGE.getName());
		Button about = new Button(PropertyTaxWatcher.ABOUT_PAGE.getName());
		Button logout = new Button("Logout");

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

		compare.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				PropertyTaxWatcher.COMPARE_PAGE.loadPage();
			}
		});

		data.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				PropertyTaxWatcher.DATA_PAGE.loadPage();
			}
		});

		about.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				PropertyTaxWatcher.ABOUT_PAGE.loadPage();
			}
		});

		logout.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				Window.Location.assign(PropertyTaxWatcher.loginInfo
						.getLogoutUrl());
			}
		});
		
		this.setHorizontalAlignment(ALIGN_CENTER);

		this.add(home);
		this.add(search);
		this.add(compare);
		this.add(data);
		this.add(about);
		this.add(logout);

		this.setStylePrimaryName("container-narrow");
	}
}
