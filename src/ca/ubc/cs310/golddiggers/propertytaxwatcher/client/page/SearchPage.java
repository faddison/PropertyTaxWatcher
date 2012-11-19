package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.SearchWidget;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;

/**
 * SearchPage class. This class represents the search page on the Property Tax
 * Watch web application.
 * 
 * @author Hubert Ngu
 */
public class SearchPage extends Page
{
	private SearchWidget searchWidget = new SearchWidget();
	private HorizontalPanel mainPanel = new HorizontalPanel();

	public SearchPage()
	{
		super("Search");
	}

	@Override
	public void loadPage()
	{
		super.loadPage();
		this.runVisualizations();
	}

	private void runVisualizations()
	{
		// Load the search.
		VisualizationUtils.loadVisualizationApi(new Runnable()
		{
			@Override
			public void run()
			{
				PropertyTaxWatcher.initializePropertyTaxes();
				createSearchWidget();
			}
		}, Table.PACKAGE);
	}

	private void createSearchWidget()
	{
		searchWidget.setCompareButton(false);
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.add(searchWidget.getSearchWidget());
		RootPanel.get().add(mainPanel);
	}
}