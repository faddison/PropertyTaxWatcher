package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.TweeterService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.TweeterServiceAsync;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.DataTablePanel;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.VisualizationUtils;

public class SearchPage extends Page
{

	// Search fields.
	private VerticalPanel searchPanel = new VerticalPanel();
	private HorizontalPanel currentValPanel = new HorizontalPanel();
	private HorizontalPanel postalCodePanel = new HorizontalPanel();
	private TextBox minCurrentValTextBox = new TextBox();
	private TextBox maxCurrentValTextBox = new TextBox();
	private TextBox postalCodeTextBox = new TextBox();
	private Button searchCurrentValButton = new Button("Search");
	private Button searchPostalCodeButton = new Button("Search");
	private Label currentValLabel = new Label();
	private Label postalCodeLabel = new Label();
	private int[] rowIndices;
	private boolean hasSearch = false;
	private int results = 0;
	private final TabPanel resultTabPanel = new TabPanel();

	private List<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();

	private final TweeterServiceAsync tweeterService = GWT
			.create(TweeterService.class);

	public SearchPage()
	{
		super("Search");
		initializeLocalPropertyTaxes();
	}

	@Override
	public void loadPage()
	{
		super.loadPage();

		final RootPanel root = RootPanel.get();

		// Load the search.
		VisualizationUtils.loadVisualizationApi(new Runnable()
		{
			@Override
			public void run()
			{
				DataTable dataTable = DataTable.create();
				dataTable = initializeDataTable(dataTable);
				dataTable = populateDataTable(dataTable);
				createSearchWidget(dataTable);
				root.add(resultTabPanel);
				resultTabPanel.setSize("600px", "400px");
				resultTabPanel.setVisible(hasSearch);
			}
		}, Table.PACKAGE);
	}

	private void createSearchWidget(final DataTable dataTable)
	{
		currentValPanel.add(currentValLabel);
		currentValPanel.add(minCurrentValTextBox);
		currentValPanel.add(maxCurrentValTextBox);
		currentValPanel.add(searchCurrentValButton);

		postalCodePanel.add(postalCodeLabel);
		postalCodePanel.add(postalCodeTextBox);
		postalCodePanel.add(searchPostalCodeButton);

		searchPanel.add(currentValPanel);
		searchPanel.add(postalCodePanel);

		currentValLabel.setText("search property taxes via current land value");
		postalCodeLabel.setText("search property taxes via postal code");

		RootPanel.get().add(searchPanel);

		// Listen for mouse events on the search button.
		searchCurrentValButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{

				double min;
				double max;
				boolean hasMin = true;
				boolean hasMax = true;

				String minMessage = minCurrentValTextBox.getText();
				String maxMessage = maxCurrentValTextBox.getText();
				if (!minMessage.matches("^[0-9\\.]{0,}$"))
				{
					Window.alert("'" + minMessage + "' is not a valid value.");
					minCurrentValTextBox.selectAll();
					return;
				} else if (!maxMessage.matches("^[0-9\\.]{0,}$"))
				{
					Window.alert("'" + maxMessage + "' is not a valid value.");
					maxCurrentValTextBox.selectAll();
					return;
				}

				if (!maxMessage.isEmpty())
				{
					max = Double.parseDouble(maxMessage);
				} else
				{
					hasMax = false;
					max = 0;
				}
				if (!minMessage.isEmpty())
				{
					min = Double.parseDouble(minMessage);
				} else
				{
					hasMin = false;
					min = 0;
				}

				if (hasMax && min <= max)
				{
					setCurrentLandValIndices(dataTable, min, max);
				} else if (!hasMax)
				{
					setCurrentLandValIndices(dataTable, min);
				} else
				{
					Window.alert("'" + minMessage + "' is higher than '"
							+ maxMessage + "', not a valid bound.");
					minCurrentValTextBox.selectAll();
					maxCurrentValTextBox.selectAll();
					return;
				}
				hasSearch = true;
				displayResult(dataTable);

				// Tweet the search!
				String message = PropertyTaxWatcher.loginInfo == null ? "FIXME"
						: PropertyTaxWatcher.loginInfo.getNickname()
								+ " searched for property tax values from "
								+ min + "-" + max + "!";
				tweet(message);
			}
		});

		searchPostalCodeButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{

				String postal = postalCodeTextBox.getText().toUpperCase()
						.trim().replaceAll("\\s", "");
				if (!postal.matches("^[0-9A-Z\\.]{1,6}$")
						&& !isValidPostalCode(postal))
				{
					Window.alert("'" + postal + "' is not a valid postal code.");
					postalCodeTextBox.selectAll();
					return;
				}
				setPostalCodeIndices(dataTable, postal);
				hasSearch = true;
				displayResult(dataTable);

				// Tweet the search!
				String message = PropertyTaxWatcher.loginInfo == null ? "FIXME"
						: PropertyTaxWatcher.loginInfo.getNickname()
								+ " searched for property tax values with postal code: "
								+ postal + "";
				tweet(message);
			}
		});
	}

	private void displayResult(final DataTable dataTable)
	{

		DataView searchView = DataView.create(dataTable);
		searchView.setRows(rowIndices);
		resultTabPanel.add(new DataTablePanel(searchView), "Search Result "
				+ results);
		resultTabPanel.setVisible(hasSearch);
		resultTabPanel.selectTab(results);
		results++;
	}

	private boolean isValidPostalCode(String postal)
	{
		for (int i = 0; i < postal.length(); i++)
		{
			char ch = postal.charAt(i);

			if (i % 2 == 0)
			{
				if (ch >= 'a' && ch <= 'z')
					return false;
			} else
			{
				if (ch >= '0' && ch <= '9')
					return false;
			}
		}
		return true;
	}

	private void setCurrentLandValIndices(final DataTable dataTable, double min)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < dataTable.getNumberOfRows(); i++)
		{
			if (dataTable.getValueDouble(i, 19) >= min)
				list.add(i);
		}
		rowIndices = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			rowIndices[i] = list.get(i).intValue();
		}
		// rowIndices = list.toArray();
		return;
	}

	private void setCurrentLandValIndices(final DataTable dataTable,
			double min, double max)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < dataTable.getNumberOfRows(); i++)
		{
			double val = dataTable.getValueDouble(i, 19);
			if (val >= min && val <= max)
				list.add(i);
		}
		rowIndices = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			rowIndices[i] = list.get(i).intValue();
		}
		// rowIndices = list.toArray();
		return;
	}

	private void setPostalCodeIndices(final DataTable dataTable, String postal)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < dataTable.getNumberOfRows(); i++)
		{
			if (postal.equalsIgnoreCase(dataTable.getValueString(i, 12).trim()
					.replaceAll("\\s", "")))
				list.add(i);
		}
		rowIndices = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			rowIndices[i] = list.get(i).intValue();
		}
		// rowIndices = list.toArray();
		return;
	}

	private DataTable populateDataTable(DataTable dataTable)
	{
		dataTable.addRows(propertyTaxes.size());

		for (int row = 0; row < propertyTaxes.size(); row++)
		{
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

	private DataTable initializeDataTable(DataTable dataTable)
	{
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

	private void initializeLocalPropertyTaxes()
	{
		this.propertyTaxes.add(new PropertyTax("008-519-811", "LAND", 0,
				67003480, "3", "180", "21435", "540", "", "3818", "",
				"10TH AVE W", "V6R 2G7", "",
				"LOT 3  BLOCK 180  PLAN 21435  DISTR",
				"ICT LOT 540  NWD OF LOT 2.", "", "", "", 1233000, 218000,
				2012, 1076000, 212000, 1988, 1988));
		this.propertyTaxes.add(new PropertyTax("006-442-536", "LAND", 0,
				67403415, "18", "180", "VAP3513", "540", "", "3889", "",
				"11TH AVE W", "V6R 2K8", "",
				"LOT 18  BLOCK 180  PLAN VAP3513  DI",
				"STRICT LOT 540  NWD AMD OF LOT 1.", "", "", "", 2925000,
				184000, 2012, 2274000, 189000, 1935, 1975));
		this.propertyTaxes.add(new PropertyTax("011-899-549", "LAND", 0,
				68802011, "19", "160", "VAP4414", "540", "", "4693", "",
				"15TH AVE W", "V6R 3B5", "",
				"LOT 19  BLOCK 160  PLAN VAP4414  DI",
				"STRICT LOT 540  NWD OF LOT 1.", "", "", "", 1570000, 33400,
				2012, 1260000, 37900, 1976, 1976));
		this.propertyTaxes.add(new PropertyTax("024-811-432", "STRATA", 0,
				4565044, "5", "", "LMS4217", "540", "301", "2522", "",
				"WATERLOO ST", "V6R 3H5", "",
				"LOT 5  PLAN LMS4217  DISTRICT LOT 5",
				"40  NEW WESTMINSTER UNDIV 817/4428 ",
				"SHARE IN COMM PROP THEREIN.", "", "", 309000, 194000, 2012,
				275000, 200000, 2000, 2000));
		this.propertyTaxes.add(new PropertyTax("013-930-303", "LAND", 0,
				9262756, "2", "185A", "VAP2301", "526", "", "1406", "",
				"LABURNUM ST", "V6J 3W3", "",
				"LOT 2  BLOCK 185A  PLAN VAP2301  DI",
				"STRICT LOT 526  NEW WESTMINSTER LOT",
				" 1, BLOCK 185A, PLAN VAP2301, DISTR",
				"ICT LOT 526, NEW WESTMINSTER LAND D", "ISTRICT.", 1885000,
				613000, 2012, 1788000, 45700, 1950, 1950));
		this.propertyTaxes.add(new PropertyTax("006-507-611", "STRATA", 0,
				62907776, "7", "", "VAS1301", "526", "PH", "2446", "",
				"POINT GREY RD", "", "", "LOT 7  PLAN VAS1301  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 2080/7605",
				" SHARE IN COM PROP THEREIN.", "", "", 881000, 295000, 2012,
				851000, 315000, 1983, 1983));
		this.propertyTaxes.add(new PropertyTax("024-365-351", "LAND", 0,
				63109007, "D", "195", "VAP15558", "526", "", "2055", "",
				"YORK AVE", "V6J 1E5", "",
				"LOT D  BLOCK 195  PLAN VAP15558  DI",
				"STRICT LOT 526  NEW WESTMINSTER", "", "", "", 12553000,
				5992000, 2012, 12539000, 6005000, 1966, 1966));
		this.propertyTaxes.add(new PropertyTax("014-957-337", "LAND", 0,
				63109061, "14", "195", "1123", "526", "", "2029", "",
				"YORK AVE", "V6J 1E4", "",
				"LOT 14  BLOCK 195  PLAN 1123  DISTR",
				"ICT LOT 526  NWD PART E 1/2, EXC N ", "2 FT NOW LANE.", "",
				"", 1077000, 9300, 2012, 1105000, 7300, 1912, 1925));
		this.propertyTaxes.add(new PropertyTax("023-773-359", "STRATA", 0,
				63109064, "5", "", "LMS2827", "526", "104", "2036", "",
				"YORK AVE", "V6J 1E6", "",
				"LOT 5  PLAN LMS2827  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 64/1283 S",
				"HARE IN COM PROP THEREIN.", "", "", 285000, 135000, 2012,
				277000, 140000, 1997, 1997));
		this.propertyTaxes.add(new PropertyTax("015-436-713", "LAND", 0,
				63604239, "19", "11", "VAP229", "540", "", "3443", "",
				"2ND AVE W", "V6R 1J3", "",
				"LOT 19  BLOCK 11  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 88400,
				2012, 1366000, 106000, 1912, 1947));
		this.propertyTaxes.add(new PropertyTax("015-433-927", "LAND", 0,
				63605307, "24", "14", "VAP229", "540", "", "1788", "",
				"TRUTCH ST", "V6K 4G1", "",
				"LOT 24  BLOCK 14  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 722000,
				2012, 1366000, 683000, 2006, 2006));
		this.propertyTaxes.add(new PropertyTax("015-671-275", "STRATA", 0,
				63809784, "21", "", "VAS2581", "526", "421", "1820", "",
				"3RD AVE W", "V6J 1K8", "",
				"LOT 21  PLAN VAS2581  DISTRICT LOT ",
				"526  NEW WESTMINSTER UNDIV 79/1629 ",
				"SHARE IN COM PROP THEREIN.", "", "", 328000, 138000, 2012,
				319000, 144000, 1990, 1990));
		this.propertyTaxes.add(new PropertyTax("028-195-051", "STRATA", 0,
				63809794, "28", "", "BCS3790", "526", "606", "1808", "",
				"3RD AVE W", "V6J 0C4", "",
				"LOT 28  PLAN BCS3790  DISTRICT LOT ",
				"526  NWD GROUP 1, TOGETHER WITH AN ",
				"INTEREST IN THE COMMON PROPERTY IN ",
				"PROPORTION TO THE UNIT ENTITLEMENT ",
				"OF THE STRATA LOT AS SHOW ON FORM 1", 250000, 151000, 2012,
				229000, 154000, 2010, 2010));
		this.propertyTaxes.add(new PropertyTax("023-936-631", "STRATA", 0,
				15760602, "289", "", "LMS2995", "FC", "", "1015", "",
				"EXPO BLVD", "V6Z 2W1", "",
				"LOT 289  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 72/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 405000, 502000, 2012,
				418000, 512000, 1997, 1997));
		this.propertyTaxes.add(new PropertyTax("023-938-072", "STRATA", 0,
				15760602, "433", "", "LMS2995", "FC", "2307", "1009", "",
				"EXPO BLVD", "V6Z 2V9", "",
				"LOT 433  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 42/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 306000, 292000, 2012,
				259000, 298000, 1997, 1997));
		this.propertyTaxes.add(new PropertyTax("018-381-430", "STRATA", 0,
				15761251, "17", "", "LMS1008", "", "", "1200", "",
				"PACIFIC BLVD", "", "", "LOT 17  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("018-381-448", "STRATA", 0,
				15761251, "18", "", "LMS1008", "", "", "1200", "",
				"PACIFIC BLVD", "", "", "LOT 18  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("018-381-456", "STRATA", 0,
				15761251, "19", "", "LMS1008", "", "", "1200", "",
				"PACIFIC BLVD", "", "", "LOT 19  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("018-381-464", "STRATA", 0,
				15761251, "20", "", "LMS1008", "", "", "1200", "",
				"PACIFIC BLVD", "", "", "LOT 20  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		this.propertyTaxes.add(new PropertyTax("024-843-491", "STRATA", 0,
				15960788, "24", "", "LMS4255", "", "505", "193", "",
				"AQUARIUS MEWS", "V6Z 2Z2", "",
				"LOT 24  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"58/33012 SHARE IN COM PROP THEREIN.", "", "", 211000, 179000,
				2012, 202000, 185000, 2000, 2000));
		this.propertyTaxes.add(new PropertyTax("024-843-571", "STRATA", 0,
				15960788, "32", "", "LMS4255", "", "606", "193", "",
				"AQUARIUS MEWS", "V6Z 2Z2", "",
				"LOT 32  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"67/33012 SHARE IN COM PROP THEREIN.", "", "", 254000, 212000,
				2012, 242000, 219000, 2000, 2000));
		this.propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0,
				19064895, "10", "51", "VAP197", "200A", "", "175", "",
				"BROADWAY E", "V5T 1W2", "",
				"LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		this.propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0,
				19064895, "10", "51", "VAP197", "200A", "", "175", "",
				"BROADWAY E", "V5T 1W2", "",
				"LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		this.propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0,
				19064895, "10", "51", "VAP197", "200A", "", "175", "",
				"BROADWAY E", "V5T 1W2", "",
				"LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
	}
}