package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import java.util.ArrayList;
import java.util.HashMap;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.PropertyTaxSearchService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.PropertyTaxSearchServiceAsync;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.TweeterService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.TweeterServiceAsync;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.DataTablePanel;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.GwtMapWidget;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.SearchParameter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
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
	private static final String MIN_LAND_VALUE_TEXT = "Minimum Land Value";
	private static final String MAX_LAND_VALUE_TEXT = "Maximum Land Value";

	// Search fields.
	private VerticalPanel searchPanel = new VerticalPanel();
	private TextBox minCurrentValTextBox = new TextBox();
	private TextBox maxCurrentValTextBox = new TextBox();
	private TextBox postalCodeTextBox = new TextBox();
	private Button searchButton = new Button("Search");
	private Button detailResultButton = new Button("Detail/Simple View");
	private Button compareButton = new Button("Compare");
	private Button clearButton = new Button("Clear");
	private Label searchLabel = new Label("Search Result.");
	private VerticalPanel resultPanel = new VerticalPanel();
	private HorizontalPanel resultSubPanel = new HorizontalPanel();
	private final TabPanel resultTabPanel = new TabPanel();
	private final TabPanel compareTabPanel = new TabPanel();
	private final int[] resultSimpleColumn = new int[] { 0, 11, 12, 19, 20 };
	private final int[] resultDetailColumn = new int[26];
	private boolean hasSearch;
	private boolean hasCompare;
	private boolean isDetail;
	private DataTable dataTable;
	MapWidget map;
	Geocoder geocoder;
	private HashMap<String, LatLng> latLngMap = new HashMap<String, LatLng>();

	// Server services
	private static TweeterServiceAsync tweeterService;
	private static PropertyTaxSearchServiceAsync propertyTaxSearchService;

	public SearchPage()
	{
		super("Search");
		tweeterService = GWT.create(TweeterService.class);
		propertyTaxSearchService = GWT.create(PropertyTaxSearchService.class);
	}

	@Override
	public void loadPage()
	{
		super.loadPage();

		this.initializeFields();
		
		Maps.loadMapsApi("", "2", false, new Runnable() 
		{

			@Override
			public void run()
			{
				System.out.println("creating map");
				map = new MapWidget(LatLng.newInstance(49.2505, -123.1119), 13);
			    map.setSize("600px", "400px");
			    RootPanel.get().add(map);
			    geocoder = new Geocoder();				
			}
		      
		});
		
		this.runVisualizations();
	}
	
	private void initializeFields()
	{
		for (int i = 0; i < resultDetailColumn.length; i++)
			resultDetailColumn[i] = i;

		hasSearch = false;
		hasCompare = false;
		isDetail = false;
		dataTable = null;
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

	public void getLatLng(String zipcode)
	{
		final String address = zipcode;
		geocoder.getLatLng(address, new LatLngCallback() {
		      public void onFailure()
		      {
		        System.out.println(address + " not found");
		      }

		      public void onSuccess(LatLng point) 
		      {
		    	latLngMap.put(address, point);
		        addMapMarker(point);
		      }
		    });
	}

	private void addMapMarker(LatLng point)
	{
		System.out.println("Added map marker");
		map.addOverlay(new Marker(point));
	}
	
	private int getZipcodeColumnIndex(DataTable dataTable)
	{
		for (int i = 0; i < dataTable.getNumberOfColumns(); i++)
		{
			if (dataTable.getColumnLabel(i).toLowerCase().contains("postal"))
			{
				return i;
			}
		}
		return -1;
	}
	
	private void addMapMarkers(DataTable dataTable)
	{
		addMapMarkers(DataView.create(dataTable));
	}
	
	private void addMapMarkers(DataView mapView)
	{
		map.clearOverlays();
		int zipIndex = getZipcodeColumnIndex(dataTable);
		for (int i = 0; i < dataTable.getNumberOfRows(); i++)
		{
			String zipcode = dataTable.getValueString(i, zipIndex);
			if (latLngMap.containsKey(zipcode))
				addMapMarker(latLngMap.get(zipcode));
			else if (zipcode == null || zipcode.trim().length() < 6)
				System.out.println("Invalid zipcode format: "+zipcode);
			else
				getLatLng(zipcode);
		}
		
	}
	
	private void createSearchWidget()
	{

		searchPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		searchPanel.setStylePrimaryName("container-narrow");
		searchPanel.addStyleName("row");
		searchButton.setStylePrimaryName("btn btn-success");
		detailResultButton.setStylePrimaryName("btn btn-success");

		RootPanel.get().add(searchPanel);

		searchPanel.add(minCurrentValTextBox);
		searchPanel.add(maxCurrentValTextBox);
		searchPanel.add(postalCodeTextBox);
		searchPanel.add(searchButton);
		searchPanel.add(clearButton);

		minCurrentValTextBox.setText("Minimum Land Value");
		maxCurrentValTextBox.setText("Maximum Land Value");
		postalCodeTextBox.setText("Postal Code");

		RootPanel.get().add(searchPanel);

		resultPanel.add(searchLabel);
		resultPanel.add(detailResultButton);
		resultPanel.add(compareButton);
		resultPanel.add(resultSubPanel);
		resultSubPanel.add(resultTabPanel);
		resultSubPanel.add(compareTabPanel);
		resultPanel.setVisible(hasSearch);
		compareTabPanel.setVisible(hasCompare);

		resultTabPanel.setSize("600px", "400px");
		compareTabPanel.setSize("600px", "400px");

		RootPanel.get().add(resultPanel);

		createSearchButton();
		createDetailResultButton();
		createCompareButton();
		createClearButton();
	}

	private void createClearButton()
	{
		clearButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				initializeFields();
				map.clearOverlays();
				resultTabPanel.clear();
				compareTabPanel.clear();
				resultPanel.setVisible(hasSearch);
				compareTabPanel.setVisible(hasCompare);

				minCurrentValTextBox.setText("Minimum Land Value");
				maxCurrentValTextBox.setText("Maximum Land Value");
				postalCodeTextBox.setText("Postal Code");

				minCurrentValTextBox.selectAll();
			}
		});
		return;
	}

	private void createDetailResultButton()
	{
		detailResultButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				resultTabPanel.remove(0);
				DataView resultView = DataView.create(dataTable);

				if (isDetail)
				{
					resultView.setColumns(resultSimpleColumn);
					isDetail = false;
				} else
				{
					resultView.setColumns(resultDetailColumn);
					isDetail = true;
				}
				resultTabPanel.add(new DataTablePanel(
						(AbstractDataTable) resultView), "Result Table");
				resultTabPanel.selectTab(0);
			}
		});
		return;
	}

	private void createCompareButton()
	{
		compareButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				DataView resultView = DataView.create(dataTable);
				// resultTabPanel.clear();
				compareTabPanel.clear();
				if (isDetail)
				{
					resultView.setColumns(resultDetailColumn);
				} else
				{
					resultView.setColumns(resultSimpleColumn);
				}
				compareTabPanel.add(new DataTablePanel(
						(AbstractDataTable) resultView), "Pervious Result");
				hasCompare = true;
				compareTabPanel.setVisible(hasCompare);
				compareTabPanel.selectTab(0);
			}
		});
		return;
	}

	private void createSearchButton()
	{
		searchButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				SearchParameter para = new SearchParameter();

				String message = PropertyTaxWatcher.loginInfo.getNickname()
						+ " searched for property tax values with";

				String minMessage = extractMinMessage(para);
				if (minMessage == null)
				{
					return;
				}
				message += minMessage;

				String maxMessage = extractMaxMessage(para);
				if (maxMessage == null)
				{
					return;
				}
				message += maxMessage;

				String postalCode = extractPostalCode(para);
				if (postalCode == null)
				{
					return;
				}
				message += postalCode;

				if (!isParameterFilled(para))
				{
					Window.alert("Please type in search parameter.");
					minCurrentValTextBox.selectAll();
					return;
				}
				message += ".";

				search(para);
				tweet(message);
			}
		});
		return;
	}

	private void displayResult(ArrayList<PropertyTax> result)
	{
		DataTable resultDataTable = DataTable.create();
		resultDataTable = PropertyTaxWatcher
				.initializeDataTable(resultDataTable);
		resultDataTable = PropertyTaxWatcher.populateDataTable(resultDataTable,
				result);
		dataTable = resultDataTable;
		DataView resultView = DataView.create(resultDataTable);
		DataView mapView = DataView.create(resultDataTable);

		if (resultDataTable.getNumberOfRows() == 0)
		{
			Window.alert("No result found.");
			return;
		}

		isDetail = false;
		resultView.setColumns(resultSimpleColumn);
		mapView.setColumns(new int[] { 12, 19 });
		map.clearOverlays();
		addMapMarkers(mapView);
		//System.out.println("Should create map markers here.");

		resultPanel.setVisible(hasSearch);
		resultTabPanel.clear();
		// resultTabPanel.add(createMapVisualization(mapView), "Map");
		resultTabPanel.add(new DataTablePanel((AbstractDataTable) resultView),
				"Result Table");
		resultTabPanel.selectTab(0);
	}

	

	/**
	 * Tweets a message on the golddigger310's Twitter account.
	 * 
	 * @param message
	 *            The message to tweet.
	 */
	private void tweet(String message)
	{
		tweeterService.updateStatus(message, new AsyncCallback<Void>()
		{
			@Override
			public void onFailure(Throwable caught)
			{
				GWT.log("Failed to tweet!");
			}

			@Override
			public void onSuccess(Void result)
			{
				GWT.log("Status update successful!");
			}

		});
	}

	private void search(SearchParameter para)
	{
		propertyTaxSearchService.searchProperty(para,
				new AsyncCallback<ArrayList<PropertyTax>>()
				{
					public void onFailure(Throwable error)
					{
						GWT.log(error.getMessage());
					}

					public void onSuccess(ArrayList<PropertyTax> result)
					{
						hasSearch = true;
						displayResult(result);
						GWT.log("Search Succeed!");
					}
				});
	}

	private String extractMinMessage(SearchParameter para)
	{
		String minMessage = minCurrentValTextBox.getText();
		if (minSearchable(minMessage))
		{
			if (!isNumber(minMessage))
			{
				Window.alert("'" + minMessage + "' is not a valid value.");
				minCurrentValTextBox.selectAll();
				return null;
			}

			para.setMinCurrentLandValue(Double.parseDouble(minMessage));
			return "lower limit = " + minMessage + " ";

		}
		return "";
	}

	private String extractMaxMessage(SearchParameter para)
	{
		String maxMessage = maxCurrentValTextBox.getText();
		if (maxSearchable(maxMessage))
		{
			if (!isNumber(maxMessage))
			{
				Window.alert("'" + maxMessage + "' is not a valid value.");
				maxCurrentValTextBox.selectAll();
				return null;
			} else
			{
				para.setMaxCurrentLandValue(Double.parseDouble(maxMessage));
				return "upper limit = " + maxMessage + " ";
			}
		}

		if (para.hasMinCurrentLandValue()
				&& para.hasMaxCurrentLandValue()
				&& para.getMinCurrentLandValue() > para
						.getMaxCurrentLandValue())
		{
			Window.alert("'" + minCurrentValTextBox.getText()
					+ "' is larger than '" + maxMessage
					+ ", not a valid bound.");
			minCurrentValTextBox.selectAll();
			maxCurrentValTextBox.selectAll();
			return null;
		}
		return "";
	}

	private String extractPostalCode(SearchParameter para)
	{
		String postal = postalCodeTextBox.getText().toUpperCase().trim()
				.replaceAll("\\s", "");
		if (!postalCodeTextBox.getText().matches("Postal Code")
				&& !postal.isEmpty())
		{
			if (!postal.matches("[A-Z][0-9][A-Z][0-9][A-Z][0-9]"))
			{
				Window.alert("'" + postal + "' is not a valid postal code.");
				postalCodeTextBox.selectAll();
				return null;
			} else
			{
				para.setPostalCode(postal.substring(0, 3) + " "
						+ postal.substring(3));
				return "and postal code " + postal + " ";
			}
		}
		return "";
	}

	private boolean isParameterFilled(SearchParameter para)
	{
		return para.hasMaxCurrentLandValue() || para.hasMinCurrentLandValue()
				|| para.hasPostalCode();
	}

	private boolean isNumber(String number)
	{
		try
		{
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e)
		{
			return false;
		}
	}

	private boolean minSearchable(String message)
	{
		return equalsSearchable(message, MIN_LAND_VALUE_TEXT);
	}

	private boolean maxSearchable(String message)
	{
		return equalsSearchable(message, MAX_LAND_VALUE_TEXT);
	}

	private boolean equalsSearchable(String message, String condition)
	{
		return !message.isEmpty() && !message.equals(condition);
	}
}
