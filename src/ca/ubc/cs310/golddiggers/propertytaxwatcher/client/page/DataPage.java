package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.RemoteDataService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.RemoteDataServiceAsync;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.DataTablePanel;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.IntensityMap;
import com.google.gwt.visualization.client.visualizations.MapVisualization;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

/**
 * DataPage class. This class represents the data page on the Property Tax
 * Watcher web app.
 * 
 * @author Hubert Ngu
 */
public class DataPage extends Page
{

	// Data display fields.
	private List<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();
	private final TabPanel tabPanel = new TabPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	
	private static final RemoteDataServiceAsync dataService = GWT.create(RemoteDataService.class);

	public DataPage()
	{
		super("Data");
		//PropertyTaxWatcher.initializeLocalPropertyTaxes(this.propertyTaxes);
	}

	@Override
	public void loadPage()
	{
		super.loadPage();
		tabPanel.clear();
		mainPanel.clear();

		//runVisualizations();
		getDataFromRemote();
		runVisualizations();
		RootPanel.get().add(mainPanel);

	}

	public void runVisualizations()
	{
		VisualizationUtils.loadVisualizationApi(
				new Runnable()
				{
					public void run()
					{
						DataTable dataTable = DataTable.create();
						dataTable = PropertyTaxWatcher
								.initializeDataTable(dataTable);
						dataTable = PropertyTaxWatcher.populateDataTable(
								dataTable, propertyTaxes);
						DataView mapView = DataView.create(dataTable);
						mapView.setColumns(new int[] { 12, 19 });

						mainPanel.getElement().getStyle()
								.setPropertyPx("margin", 15);
						RootPanel.get().add(mainPanel);

						mainPanel.add(new Label(
								"Property Tax Watcher: Entire Data Set"));
						mainPanel.add(tabPanel);
						tabPanel.setSize("450px", "450px");
						tabPanel.add(new DataTablePanel(dataTable), "Table");
						tabPanel.add(createMapVisualization(mapView), "Map");
						tabPanel.add(createGeoMap(mapView), "GeoMap");
						tabPanel.selectTab(0);
					}
				}, PieChart.PACKAGE, Table.PACKAGE, MapVisualization.PACKAGE,
				IntensityMap.PACKAGE, GeoMap.PACKAGE);
	}

	private Widget createMapVisualization(DataView dataView)
	{
		MapVisualization map = new MapVisualization("400", "400");
		map.draw(dataView);
		return map.asWidget();
	}

	private Widget createGeoMap(DataView dataView)
	{
		com.google.gwt.visualization.client.visualizations.GeoMap.Options options;
		options = com.google.gwt.visualization.client.visualizations.GeoMap.Options
				.create();
		options.set("dataMode", "regions");
		GeoMap map = new GeoMap();
		map.draw(dataView, options);
		return map.asWidget();
	}
	private void getDataFromRemote() {
		
		dataService.getData(new AsyncCallback<ArrayList<PropertyTax>>()
				{
					public void onFailure(Throwable error) {
						GWT.log(error.getMessage());
					}
					
					public void onSuccess(ArrayList<PropertyTax> result)
					{
						for (int i = 0; i < result.size(); i ++) {
							propertyTaxes.add(result.get(i));
						}
						GWT.log("Data Access Succeed!");
					}
				});
	
	}
}
