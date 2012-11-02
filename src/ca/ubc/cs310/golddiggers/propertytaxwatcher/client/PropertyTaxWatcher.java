package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.Query;
import com.google.gwt.visualization.client.Query.Callback;
import com.google.gwt.visualization.client.Query.SendMethod;
import com.google.gwt.visualization.client.QueryResponse;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.formatters.ArrowFormat;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.IntensityMap;
import com.google.gwt.visualization.client.visualizations.MapVisualization;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PropertyTaxWatcher implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private LoginInfo loginInfo;
	private Anchor signInLink = new Anchor("Sign In");

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	// private final GreetingServiceAsync greetingService = GWT
	// .create(GreetingService.class);

	private FlexTable propertyTaxesFlexTable = new FlexTable();
	private List<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();
	private final PropertyTaxServiceAsync propertyTaxService = GWT
			.create(PropertyTaxService.class);
	private final TabPanel tabPanel = new TabPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private boolean useLogin = true;

	// private DataTable dataTable;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		if (this.useLogin)
			executeLogin();
		else
			loadPropertyTaxWatcher();
	}

	private void executeLogin()
	{
		// Load the login options.
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() 
				{
					public void onFailure(Throwable error) 
					{
						System.out.println(error.getMessage());
					}

					public void onSuccess(LoginInfo result)
					{
						System.out.println(GWT.getHostPageBaseURL());
						loginInfo = result;
						if (loginInfo.isLoggedIn()) 
						{
							loadPropertyTaxWatcher();
						} 
						else 
						{
							loadLogin();
						}
					}
				});
	}

	/**
	 * Loads the login.
	 */
	private void loadLogin() 
	{
		// Assemble login panel.
		VerticalPanel loginPanel = new VerticalPanel();
		Label loginLabel = new Label("Please sign in to your Google Account "
				+ "to access the PropertyTaxWatcher application.");
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("propertyTaxList").add(loginPanel);
	}

	private void loadPropertyTaxWatcher() 
	{
		// Set up sign out hyperlink.
		if (this.useLogin)
		{
			Anchor signOutLink = new Anchor("Sign Out");
			signOutLink.setHref(loginInfo.getLogoutUrl());
			RootPanel.get("propertyTaxList").add(signOutLink);
		}
		// Load the test property taxes.
		//initializePropertyTaxes();
		//loadPropertyTaxes();
		initializeLocalPropertyTaxes();
		runVisualizations();
	}

	public void runVisualizations() 
	{
		VisualizationUtils.loadVisualizationApi(new Runnable() 
		{
			public void run() 
			{
				DataTable dataTable = DataTable.create();
				dataTable = initializeDataTable(dataTable);
				dataTable = populateDataTable(dataTable);
				DataView mapView = DataView.create(dataTable);
				// Dataview of postal code and current land value
				mapView.setColumns(new int[]{12,19});
				mainPanel.getElement().getStyle().setPropertyPx("margin", 15);
				RootPanel.get().add(mainPanel);
				mainPanel.add(new Label(
						"Property Tax Watcher Visualization Demonstration."));
				mainPanel.add(tabPanel);
				tabPanel.setWidth("800");
				tabPanel.setHeight("600");
				// tabPanel.add(createPieChart(), "Pie Chart");
				tabPanel.add(createDataTableWidget(dataTable), "Table");
				tabPanel.add(createMapVisualization(mapView), "Map");
				//tabPanel.add(createIntensityMap(mapView), "IntensityMap");
				tabPanel.add(createGeoMap(mapView), "GeoMap");
				// tabPanel.add(createDataView(), "DataView");
				tabPanel.selectTab(0);
			}
		}, PieChart.PACKAGE, Table.PACKAGE, MapVisualization.PACKAGE, IntensityMap.PACKAGE, GeoMap.PACKAGE);
	}
	
	private ArrowFormat createFormatter() {
		ArrowFormat.Options options = ArrowFormat.Options.create();
		options.setBase(1.5);
		return ArrowFormat.create(options);
	}

	private Widget createGeoMap(DataView dataView)
	{
		com.google.gwt.visualization.client.visualizations.GeoMap.Options options;
		options = com.google.gwt.visualization.client.visualizations.GeoMap.Options.create();
		options.set("dataMode", "regions");
		GeoMap map = new GeoMap();
		map.draw(dataView, options);
		return map.asWidget();
	}
	
	private Widget createIntensityMap(DataView dataView) 
	{
		IntensityMap map = new IntensityMap();
		map.setSize("400", "400");
		map.draw(dataView);
		return map.asWidget();
	}
	
	private Widget createMapVisualization(DataView dataView)
	{
		MapVisualization map = new MapVisualization("400", "400");
		map.draw(dataView);
		return map.asWidget();
	}

	// private DataTable getDataTable()
	// {
	// return this.dataTable;
	// }

	private DataTable populateDataTable(DataTable dataTable) {
		dataTable.addRows(propertyTaxes.size());

		for (int row = 0; row < propertyTaxes.size(); row++) {
			PropertyTax propertyTax = propertyTaxes.get(row);

			dataTable.setValue(row, 0, propertyTax.getPid());
			dataTable.setValue(row, 1, propertyTax.getLegal_type());
			dataTable.setValue(row, 2, propertyTax.getFolio());
			dataTable.setValue(row, 3, propertyTax.getLand_coordinate());
			dataTable.setValue(row, 4, propertyTax.getLot());
			dataTable.setValue(row, 5, propertyTax.getBlock());
			dataTable.setValue(row, 6, propertyTax.getPlan());
			dataTable.setValue(row, 7, propertyTax.getDistrict_lot());
			dataTable.setValue(row, 8, propertyTax.getFrom_civic_number());
			dataTable.setValue(row, 9, propertyTax.getTo_civic_number());
			dataTable.setValue(row, 10,
					propertyTax.getStreet_prefix_direction());
			dataTable.setValue(row, 11, propertyTax.getStreet_name());
			dataTable.setValue(row, 12, propertyTax.getProperty_postal_code());
			dataTable.setValue(row, 13, propertyTax.getRecord_status_code());
			dataTable.setValue(row, 14, propertyTax.getNarrative_legal_line1());
			dataTable.setValue(row, 15, propertyTax.getNarrative_legal_line2());
			dataTable.setValue(row, 16, propertyTax.getNarrative_legal_line3());
			dataTable.setValue(row, 17, propertyTax.getNarrative_legal_line4());
			dataTable.setValue(row, 18, propertyTax.getNarrative_legal_line5());
			dataTable.setValue(row, 19, propertyTax.getCurrent_land_value());
			dataTable.setValue(row, 20,
					propertyTax.getCurrent_improvement_value());
			dataTable.setValue(row, 21, propertyTax.getAssessment_year());
			dataTable.setValue(row, 22, propertyTax.getPrevious_land_value());
			dataTable.setValue(row, 23,
					propertyTax.getPrevious_improvement_value());
			dataTable.setValue(row, 24, propertyTax.getYear_built());
			dataTable.setValue(row, 25, propertyTax.getBig_improvement_year());

		}
		return dataTable;
	}

	private DataTable initializeDataTable(DataTable dataTable) {
		dataTable.addColumn(ColumnType.STRING, "Pid");
		dataTable.addColumn(ColumnType.STRING, "Legal Type");
		dataTable.addColumn(ColumnType.NUMBER, "Folio");
		dataTable.addColumn(ColumnType.NUMBER, "Land Coordinate");
		dataTable.addColumn(ColumnType.STRING, "Lot");
		dataTable.addColumn(ColumnType.STRING, "Block");
		dataTable.addColumn(ColumnType.STRING, "Plan");
		dataTable.addColumn(ColumnType.STRING, "District Lot");
		dataTable.addColumn(ColumnType.STRING, "From Civic Number");
		dataTable.addColumn(ColumnType.STRING, "To Civic Number");
		dataTable.addColumn(ColumnType.STRING, "Street Prefix Direction");
		dataTable.addColumn(ColumnType.STRING, "Street Name");
		dataTable.addColumn(ColumnType.STRING, "Property Postal Code");
		dataTable.addColumn(ColumnType.STRING, "Record Status Code");
		dataTable.addColumn(ColumnType.STRING, "Narrative Legal Line1");
		dataTable.addColumn(ColumnType.STRING, "Narrative Legal Line2");
		dataTable.addColumn(ColumnType.STRING, "Narrative Legal Line3");
		dataTable.addColumn(ColumnType.STRING, "Narrative Legal Line4");
		dataTable.addColumn(ColumnType.STRING, "Narrative Legal Line5");
		dataTable.addColumn(ColumnType.NUMBER, "Current Land Value");
		dataTable.addColumn(ColumnType.NUMBER, "Current Improvement Value");
		dataTable.addColumn(ColumnType.NUMBER, "Assessment Year");
		dataTable.addColumn(ColumnType.NUMBER, "Previous Land Value");
		dataTable.addColumn(ColumnType.NUMBER, "Previous Improvement Value");
		dataTable.addColumn(ColumnType.NUMBER, "Year Built");
		dataTable.addColumn(ColumnType.NUMBER, "Big Improvement Year");

		return dataTable;
	}

	private Widget createDataTableWidget(DataTable dataTable) {
		final Panel panel = new FlowPanel();
		final Table viz = new Table();
		panel.add(viz);
		Options options = Table.Options.create();
		options.setShowRowNumber(true);

		ArrowFormat formatter = createFormatter();
		formatter.format(dataTable, 1);
		viz.draw(dataTable, options);

		return panel;
	}

	private DataTable uploadTable() {
		// Read data from spreadsheet
		// String dataUrl =
		// "https://docs.google.com/spreadsheet/ccc?key=0Agsv1QvoutjwdHNfM2NaZnh4MjdNVXJQc0FWY3I2Smc";
		String dataUrl = "https://docs.google.com/spreadsheet/ccc?key=0Agsv1QvoutjwdGJveU9LbmxFaFJoTW51RGxvbk81WUE";
		Query.Options queryOptions = Query.Options.create();
		queryOptions.setSendMethod(SendMethod.SCRIPT_INJECTION);
		// PersistenceManager pm = getPeris
		Query query = Query.create(dataUrl, queryOptions);
		query.setTimeout(1000);
		TableCallback tableCallback;
		query.send(tableCallback = new TableCallback());
		return tableCallback.getDataTable();
	}

	class TableCallback implements Callback {
		DataTable dataTable;

		public DataTable getDataTable() {
			return this.dataTable;
		}

		public void onResponse(QueryResponse response) {
			if (response.isError()) {
				System.out.println("Error in query: " + response.getMessage() + ' '
						+ response.getDetailedMessage());
				return;
			}

			this.dataTable = response.getDataTable();
		}
	}

	private void initializePropertyTaxes() {
		addPropertyTax(new PropertyTax("008-519-811", "LAND", 0, 67003480, "3",
				"180", "21435", "540", "", "3818", "", "10TH AVE W", "V6R 2G7",
				"", "LOT 3  BLOCK 180  PLAN 21435  DISTR",
				"ICT LOT 540  NWD OF LOT 2.", "", "", "", 1233000, 218000,
				2012, 1076000, 212000, 1988, 1988));
		addPropertyTax(new PropertyTax("006-442-536", "LAND", 0, 67403415,
				"18", "180", "VAP3513", "540", "", "3889", "", "11TH AVE W",
				"V6R 2K8", "", "LOT 18  BLOCK 180  PLAN VAP3513  DI",
				"STRICT LOT 540  NWD AMD OF LOT 1.", "", "", "", 2925000,
				184000, 2012, 2274000, 189000, 1935, 1975));
		addPropertyTax(new PropertyTax("011-899-549", "LAND", 0, 68802011,
				"19", "160", "VAP4414", "540", "", "4693", "", "15TH AVE W",
				"V6R 3B5", "", "LOT 19  BLOCK 160  PLAN VAP4414  DI",
				"STRICT LOT 540  NWD OF LOT 1.", "", "", "", 1570000, 33400,
				2012, 1260000, 37900, 1976, 1976));
		addPropertyTax(new PropertyTax("024-811-432", "STRATA", 0, 4565044,
				"5", "", "LMS4217", "540", "301", "2522", "", "WATERLOO ST",
				"V6R 3H5", "", "LOT 5  PLAN LMS4217  DISTRICT LOT 5",
				"40  NEW WESTMINSTER UNDIV 817/4428 ",
				"SHARE IN COMM PROP THEREIN.", "", "", 309000, 194000, 2012,
				275000, 200000, 2000, 2000));
		addPropertyTax(new PropertyTax("013-930-303", "LAND", 0, 9262756, "2",
				"185A", "VAP2301", "526", "", "1406", "", "LABURNUM ST",
				"V6J 3W3", "", "LOT 2  BLOCK 185A  PLAN VAP2301  DI",
				"STRICT LOT 526  NEW WESTMINSTER LOT",
				" 1, BLOCK 185A, PLAN VAP2301, DISTR",
				"ICT LOT 526, NEW WESTMINSTER LAND D", "ISTRICT.", 1885000,
				613000, 2012, 1788000, 45700, 1950, 1950));
		addPropertyTax(new PropertyTax("006-507-611", "STRATA", 0, 62907776,
				"7", "", "VAS1301", "526", "PH", "2446", "", "POINT GREY RD",
				"", "", "LOT 7  PLAN VAS1301  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 2080/7605",
				" SHARE IN COM PROP THEREIN.", "", "", 881000, 295000, 2012,
				851000, 315000, 1983, 1983));
		addPropertyTax(new PropertyTax("024-365-351", "LAND", 0, 63109007, "D",
				"195", "VAP15558", "526", "", "2055", "", "YORK AVE",
				"V6J 1E5", "", "LOT D  BLOCK 195  PLAN VAP15558  DI",
				"STRICT LOT 526  NEW WESTMINSTER", "", "", "", 12553000,
				5992000, 2012, 12539000, 6005000, 1966, 1966));
		addPropertyTax(new PropertyTax("014-957-337", "LAND", 0, 63109061,
				"14", "195", "1123", "526", "", "2029", "", "YORK AVE",
				"V6J 1E4", "", "LOT 14  BLOCK 195  PLAN 1123  DISTR",
				"ICT LOT 526  NWD PART E 1/2, EXC N ", "2 FT NOW LANE.", "",
				"", 1077000, 9300, 2012, 1105000, 7300, 1912, 1925));
		addPropertyTax(new PropertyTax("023-773-359", "STRATA", 0, 63109064,
				"5", "", "LMS2827", "526", "104", "2036", "", "YORK AVE",
				"V6J 1E6", "", "LOT 5  PLAN LMS2827  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 64/1283 S",
				"HARE IN COM PROP THEREIN.", "", "", 285000, 135000, 2012,
				277000, 140000, 1997, 1997));
		addPropertyTax(new PropertyTax("015-436-713", "LAND", 0, 63604239,
				"19", "11", "VAP229", "540", "", "3443", "", "2ND AVE W",
				"V6R 1J3", "", "LOT 19  BLOCK 11  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 88400,
				2012, 1366000, 106000, 1912, 1947));
		addPropertyTax(new PropertyTax("015-433-927", "LAND", 0, 63605307,
				"24", "14", "VAP229", "540", "", "1788", "", "TRUTCH ST",
				"V6K 4G1", "", "LOT 24  BLOCK 14  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 722000,
				2012, 1366000, 683000, 2006, 2006));
		addPropertyTax(new PropertyTax("015-671-275", "STRATA", 0, 63809784,
				"21", "", "VAS2581", "526", "421", "1820", "", "3RD AVE W",
				"V6J 1K8", "", "LOT 21  PLAN VAS2581  DISTRICT LOT ",
				"526  NEW WESTMINSTER UNDIV 79/1629 ",
				"SHARE IN COM PROP THEREIN.", "", "", 328000, 138000, 2012,
				319000, 144000, 1990, 1990));
		addPropertyTax(new PropertyTax("028-195-051", "STRATA", 0, 63809794,
				"28", "", "BCS3790", "526", "606", "1808", "", "3RD AVE W",
				"V6J 0C4", "", "LOT 28  PLAN BCS3790  DISTRICT LOT ",
				"526  NWD GROUP 1, TOGETHER WITH AN ",
				"INTEREST IN THE COMMON PROPERTY IN ",
				"PROPORTION TO THE UNIT ENTITLEMENT ",
				"OF THE STRATA LOT AS SHOW ON FORM 1", 250000, 151000, 2012,
				229000, 154000, 2010, 2010));
		addPropertyTax(new PropertyTax("023-936-631", "STRATA", 0, 15760602,
				"289", "", "LMS2995", "FC", "", "1015", "", "EXPO BLVD",
				"V6Z 2W1", "", "LOT 289  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 72/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 405000, 502000, 2012,
				418000, 512000, 1997, 1997));
		addPropertyTax(new PropertyTax("023-938-072", "STRATA", 0, 15760602,
				"433", "", "LMS2995", "FC", "2307", "1009", "", "EXPO BLVD",
				"V6Z 2V9", "", "LOT 433  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 42/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 306000, 292000, 2012,
				259000, 298000, 1997, 1997));
		addPropertyTax(new PropertyTax("018-381-430", "STRATA", 0, 15761251,
				"17", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 17  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		addPropertyTax(new PropertyTax("018-381-448", "STRATA", 0, 15761251,
				"18", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 18  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		addPropertyTax(new PropertyTax("018-381-456", "STRATA", 0, 15761251,
				"19", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 19  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		addPropertyTax(new PropertyTax("018-381-464", "STRATA", 0, 15761251,
				"20", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 20  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		addPropertyTax(new PropertyTax("024-843-491", "STRATA", 0, 15960788,
				"24", "", "LMS4255", "", "505", "193", "", "AQUARIUS MEWS",
				"V6Z 2Z2", "", "LOT 24  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"58/33012 SHARE IN COM PROP THEREIN.", "", "", 211000, 179000,
				2012, 202000, 185000, 2000, 2000));
		addPropertyTax(new PropertyTax("024-843-571", "STRATA", 0, 15960788,
				"32", "", "LMS4255", "", "606", "193", "", "AQUARIUS MEWS",
				"V6Z 2Z2", "", "LOT 32  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"67/33012 SHARE IN COM PROP THEREIN.", "", "", 254000, 212000,
				2012, 242000, 219000, 2000, 2000));
		addPropertyTax(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		addPropertyTax(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		addPropertyTax(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
	}
	
	private void initializeLocalPropertyTaxes() {
		this.propertyTaxes.add(new PropertyTax("008-519-811", "LAND", 0, 67003480, "3",
				"180", "21435", "540", "", "3818", "", "10TH AVE W", "V6R 2G7",
				"", "LOT 3  BLOCK 180  PLAN 21435  DISTR",
				"ICT LOT 540  NWD OF LOT 2.", "", "", "", 1233000, 218000,
				2012, 1076000, 212000, 1988, 1988));
		this.propertyTaxes.add(new PropertyTax("006-442-536", "LAND", 0, 67403415,
				"18", "180", "VAP3513", "540", "", "3889", "", "11TH AVE W",
				"V6R 2K8", "", "LOT 18  BLOCK 180  PLAN VAP3513  DI",
				"STRICT LOT 540  NWD AMD OF LOT 1.", "", "", "", 2925000,
				184000, 2012, 2274000, 189000, 1935, 1975));
		this.propertyTaxes.add(new PropertyTax("011-899-549", "LAND", 0, 68802011,
				"19", "160", "VAP4414", "540", "", "4693", "", "15TH AVE W",
				"V6R 3B5", "", "LOT 19  BLOCK 160  PLAN VAP4414  DI",
				"STRICT LOT 540  NWD OF LOT 1.", "", "", "", 1570000, 33400,
				2012, 1260000, 37900, 1976, 1976));
		this.propertyTaxes.add(new PropertyTax("024-811-432", "STRATA", 0, 4565044,
				"5", "", "LMS4217", "540", "301", "2522", "", "WATERLOO ST",
				"V6R 3H5", "", "LOT 5  PLAN LMS4217  DISTRICT LOT 5",
				"40  NEW WESTMINSTER UNDIV 817/4428 ",
				"SHARE IN COMM PROP THEREIN.", "", "", 309000, 194000, 2012,
				275000, 200000, 2000, 2000));
		this.propertyTaxes.add(new PropertyTax("013-930-303", "LAND", 0, 9262756, "2",
				"185A", "VAP2301", "526", "", "1406", "", "LABURNUM ST",
				"V6J 3W3", "", "LOT 2  BLOCK 185A  PLAN VAP2301  DI",
				"STRICT LOT 526  NEW WESTMINSTER LOT",
				" 1, BLOCK 185A, PLAN VAP2301, DISTR",
				"ICT LOT 526, NEW WESTMINSTER LAND D", "ISTRICT.", 1885000,
				613000, 2012, 1788000, 45700, 1950, 1950));
		this.propertyTaxes.add(new PropertyTax("006-507-611", "STRATA", 0, 62907776,
				"7", "", "VAS1301", "526", "PH", "2446", "", "POINT GREY RD",
				"", "", "LOT 7  PLAN VAS1301  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 2080/7605",
				" SHARE IN COM PROP THEREIN.", "", "", 881000, 295000, 2012,
				851000, 315000, 1983, 1983));
		this.propertyTaxes.add(new PropertyTax("024-365-351", "LAND", 0, 63109007, "D",
				"195", "VAP15558", "526", "", "2055", "", "YORK AVE",
				"V6J 1E5", "", "LOT D  BLOCK 195  PLAN VAP15558  DI",
				"STRICT LOT 526  NEW WESTMINSTER", "", "", "", 12553000,
				5992000, 2012, 12539000, 6005000, 1966, 1966));
		this.propertyTaxes.add(new PropertyTax("014-957-337", "LAND", 0, 63109061,
				"14", "195", "1123", "526", "", "2029", "", "YORK AVE",
				"V6J 1E4", "", "LOT 14  BLOCK 195  PLAN 1123  DISTR",
				"ICT LOT 526  NWD PART E 1/2, EXC N ", "2 FT NOW LANE.", "",
				"", 1077000, 9300, 2012, 1105000, 7300, 1912, 1925));
		this.propertyTaxes.add(new PropertyTax("023-773-359", "STRATA", 0, 63109064,
				"5", "", "LMS2827", "526", "104", "2036", "", "YORK AVE",
				"V6J 1E6", "", "LOT 5  PLAN LMS2827  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 64/1283 S",
				"HARE IN COM PROP THEREIN.", "", "", 285000, 135000, 2012,
				277000, 140000, 1997, 1997));
		this.propertyTaxes.add(new PropertyTax("015-436-713", "LAND", 0, 63604239,
				"19", "11", "VAP229", "540", "", "3443", "", "2ND AVE W",
				"V6R 1J3", "", "LOT 19  BLOCK 11  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 88400,
				2012, 1366000, 106000, 1912, 1947));
		this.propertyTaxes.add(new PropertyTax("015-433-927", "LAND", 0, 63605307,
				"24", "14", "VAP229", "540", "", "1788", "", "TRUTCH ST",
				"V6K 4G1", "", "LOT 24  BLOCK 14  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 722000,
				2012, 1366000, 683000, 2006, 2006));
		this.propertyTaxes.add(new PropertyTax("015-671-275", "STRATA", 0, 63809784,
				"21", "", "VAS2581", "526", "421", "1820", "", "3RD AVE W",
				"V6J 1K8", "", "LOT 21  PLAN VAS2581  DISTRICT LOT ",
				"526  NEW WESTMINSTER UNDIV 79/1629 ",
				"SHARE IN COM PROP THEREIN.", "", "", 328000, 138000, 2012,
				319000, 144000, 1990, 1990));
		this.propertyTaxes.add(new PropertyTax("028-195-051", "STRATA", 0, 63809794,
				"28", "", "BCS3790", "526", "606", "1808", "", "3RD AVE W",
				"V6J 0C4", "", "LOT 28  PLAN BCS3790  DISTRICT LOT ",
				"526  NWD GROUP 1, TOGETHER WITH AN ",
				"INTEREST IN THE COMMON PROPERTY IN ",
				"PROPORTION TO THE UNIT ENTITLEMENT ",
				"OF THE STRATA LOT AS SHOW ON FORM 1", 250000, 151000, 2012,
				229000, 154000, 2010, 2010));
		this.propertyTaxes.add(new PropertyTax("023-936-631", "STRATA", 0, 15760602,
				"289", "", "LMS2995", "FC", "", "1015", "", "EXPO BLVD",
				"V6Z 2W1", "", "LOT 289  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 72/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 405000, 502000, 2012,
				418000, 512000, 1997, 1997));
		this.propertyTaxes.add(new PropertyTax("023-938-072", "STRATA", 0, 15760602,
				"433", "", "LMS2995", "FC", "2307", "1009", "", "EXPO BLVD",
				"V6Z 2V9", "", "LOT 433  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 42/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 306000, 292000, 2012,
				259000, 298000, 1997, 1997));
		this.propertyTaxes.add(new PropertyTax("018-381-430", "STRATA", 0, 15761251,
				"17", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 17  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("018-381-448", "STRATA", 0, 15761251,
				"18", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 18  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("018-381-456", "STRATA", 0, 15761251,
				"19", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 19  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("018-381-464", "STRATA", 0, 15761251,
				"20", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 20  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("024-843-491", "STRATA", 0, 15960788,
				"24", "", "LMS4255", "", "505", "193", "", "AQUARIUS MEWS",
				"V6Z 2Z2", "", "LOT 24  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"58/33012 SHARE IN COM PROP THEREIN.", "", "", 211000, 179000,
				2012, 202000, 185000, 2000, 2000));
		this.propertyTaxes.add(new PropertyTax("024-843-571", "STRATA", 0, 15960788,
				"32", "", "LMS4255", "", "606", "193", "", "AQUARIUS MEWS",
				"V6Z 2Z2", "", "LOT 32  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"67/33012 SHARE IN COM PROP THEREIN.", "", "", 254000, 212000,
				2012, 242000, 219000, 2000, 2000));
		this.propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		this.propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		this.propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
	}

	private void loadPropertyTaxes() {
		propertyTaxService
				.getPropertyTaxes(new AsyncCallback<List<PropertyTax>>() {
					public void onFailure(Throwable error) {
						handleError(error);
					}

					public void onSuccess(List<PropertyTax> propertyTaxList) {
						System.out.println("Serialized property taxes loaded.");
						setPropertyTaxes(propertyTaxList);
						runVisualizations();

					}
				});
	}

	private void setPropertyTaxes(List<PropertyTax> propertyTaxList) {
		this.propertyTaxes = propertyTaxList;
	}

	private void handleError(Throwable error) {
		System.out.println(error.getMessage());
	}

	private void addPropertyTax(final PropertyTax propertyTax) {
		propertyTaxService.addPropertyTax(propertyTax,
				new AsyncCallback<Void>() {
					public void onFailure(Throwable error) {
						handleError(error);
					}

					public void onSuccess(Void ignore) {
						System.out
								.println("Property Tax successfully initialized.");
					}
				});
	}

	private void displayPropertyTaxes(List<PropertyTax> propertyTaxes) {
		Grid propertyTaxesGrid = new Grid(propertyTaxes.size() + 1, 27);
		propertyTaxesGrid.setStyleName("sample");

		for (int i = 0; i < propertyTaxes.size(); i++) {
			int row = i + 1;
			PropertyTax propertyTax = propertyTaxes.get(i);
			System.out.println(propertyTax.toString());
			propertyTaxesGrid.getRowFormatter().setStyleName(row,
					"columnOverflow");

			propertyTaxesGrid.setText(row, 1, propertyTax.getPid());
			propertyTaxesGrid.setText(row, 2, propertyTax.getLegal_type());
			propertyTaxesGrid.setText(row, 3,
					Long.toString(propertyTax.getFolio()));
			propertyTaxesGrid.setText(row, 4,
					Long.toString(propertyTax.getLand_coordinate()));
			propertyTaxesGrid.setText(row, 5, propertyTax.getLot());
			propertyTaxesGrid.setText(row, 6, propertyTax.getBlock());
			propertyTaxesGrid.setText(row, 7, propertyTax.getPlan());
			propertyTaxesGrid.setText(row, 8, propertyTax.getDistrict_lot());
			propertyTaxesGrid.setText(row, 9,
					propertyTax.getFrom_civic_number());
			propertyTaxesGrid
					.setText(row, 10, propertyTax.getTo_civic_number());
			propertyTaxesGrid.setText(row, 11,
					propertyTax.getStreet_prefix_direction());
			propertyTaxesGrid.setText(row, 12, propertyTax.getStreet_name());
			propertyTaxesGrid.setText(row, 13,
					propertyTax.getProperty_postal_code());
			propertyTaxesGrid.setText(row, 14,
					propertyTax.getRecord_status_code());
			propertyTaxesGrid.setText(row, 15,
					propertyTax.getNarrative_legal_line1());
			propertyTaxesGrid.setText(row, 16,
					propertyTax.getNarrative_legal_line2());
			propertyTaxesGrid.setText(row, 17,
					propertyTax.getNarrative_legal_line3());
			propertyTaxesGrid.setText(row, 18,
					propertyTax.getNarrative_legal_line4());
			propertyTaxesGrid.setText(row, 19,
					propertyTax.getNarrative_legal_line5());
			propertyTaxesGrid.setText(row, 20,
					Double.toString(propertyTax.getCurrent_land_value()));
			propertyTaxesGrid
					.setText(row, 21, Double.toString(propertyTax
							.getCurrent_improvement_value()));
			propertyTaxesGrid.setText(row, 22,
					Double.toString(propertyTax.getAssessment_year()));
			propertyTaxesGrid.setText(row, 23,
					Double.toString(propertyTax.getPrevious_land_value()));
			propertyTaxesGrid.setText(row, 24, Double.toString(propertyTax
					.getPrevious_improvement_value()));
			propertyTaxesGrid.setText(row, 25,
					Double.toString(propertyTax.getYear_built()));
			propertyTaxesGrid.setText(row, 26,
					Double.toString(propertyTax.getBig_improvement_year()));

		}
		mainPanel.add(propertyTaxesGrid);

	}
}
