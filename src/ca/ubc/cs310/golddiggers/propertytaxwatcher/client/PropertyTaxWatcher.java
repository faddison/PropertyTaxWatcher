package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;

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

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	// private final GreetingServiceAsync greetingService = GWT
	// .create(GreetingService.class);

	private FlexTable propertyTaxesFlexTable = new FlexTable();
	private Grid propertyTaxesGrid;
	private ArrayList<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();
	private final PropertyTaxServiceAsync propertyTaxService = GWT
			.create(PropertyTaxService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		loadTestPropertyTaxes();
		propertyTaxesGrid = new Grid(propertyTaxes.size() + 1, 27);
		displayPropertyTaxes();
		RootPanel.get("propertyTaxList").add(propertyTaxesGrid);
	}

	private void loadTestPropertyTaxes() {
		ArrayList<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();

		propertyTaxes.add(new PropertyTax("008-519-811", "LAND", 0, 67003480,
				"3", "180", "21435", "540", "", "3818", "", "10TH AVE W",
				"V6R 2G7", "", "LOT 3  BLOCK 180  PLAN 21435  DISTR",
				"ICT LOT 540  NWD OF LOT 2.", "", "", "", 1233000, 218000,
				2012, 1076000, 212000, 1988, 1988));
		propertyTaxes.add(new PropertyTax("006-442-536", "LAND", 0, 67403415,
				"18", "180", "VAP3513", "540", "", "3889", "", "11TH AVE W",
				"V6R 2K8", "", "LOT 18  BLOCK 180  PLAN VAP3513  DI",
				"STRICT LOT 540  NWD AMD OF LOT 1.", "", "", "", 2925000,
				184000, 2012, 2274000, 189000, 1935, 1975));
		propertyTaxes.add(new PropertyTax("011-899-549", "LAND", 0, 68802011,
				"19", "160", "VAP4414", "540", "", "4693", "", "15TH AVE W",
				"V6R 3B5", "", "LOT 19  BLOCK 160  PLAN VAP4414  DI",
				"STRICT LOT 540  NWD OF LOT 1.", "", "", "", 1570000, 33400,
				2012, 1260000, 37900, 1976, 1976));
		propertyTaxes.add(new PropertyTax("024-811-432", "STRATA", 0, 4565044,
				"5", "", "LMS4217", "540", "301", "2522", "", "WATERLOO ST",
				"V6R 3H5", "", "LOT 5  PLAN LMS4217  DISTRICT LOT 5",
				"40  NEW WESTMINSTER UNDIV 817/4428 ",
				"SHARE IN COMM PROP THEREIN.", "", "", 309000, 194000, 2012,
				275000, 200000, 2000, 2000));
		propertyTaxes.add(new PropertyTax("013-930-303", "LAND", 0, 9262756,
				"2", "185A", "VAP2301", "526", "", "1406", "", "LABURNUM ST",
				"V6J 3W3", "", "LOT 2  BLOCK 185A  PLAN VAP2301  DI",
				"STRICT LOT 526  NEW WESTMINSTER LOT",
				" 1, BLOCK 185A, PLAN VAP2301, DISTR",
				"ICT LOT 526, NEW WESTMINSTER LAND D", "ISTRICT.", 1885000,
				613000, 2012, 1788000, 45700, 1950, 1950));
		propertyTaxes.add(new PropertyTax("006-507-611", "STRATA", 0, 62907776,
				"7", "", "VAS1301", "526", "PH", "2446", "", "POINT GREY RD",
				"", "", "LOT 7  PLAN VAS1301  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 2080/7605",
				" SHARE IN COM PROP THEREIN.", "", "", 881000, 295000, 2012,
				851000, 315000, 1983, 1983));
		propertyTaxes.add(new PropertyTax("024-365-351", "LAND", 0, 63109007,
				"D", "195", "VAP15558", "526", "", "2055", "", "YORK AVE",
				"V6J 1E5", "", "LOT D  BLOCK 195  PLAN VAP15558  DI",
				"STRICT LOT 526  NEW WESTMINSTER", "", "", "", 12553000,
				5992000, 2012, 12539000, 6005000, 1966, 1966));
		propertyTaxes.add(new PropertyTax("014-957-337", "LAND", 0, 63109061,
				"14", "195", "1123", "526", "", "2029", "", "YORK AVE",
				"V6J 1E4", "", "LOT 14  BLOCK 195  PLAN 1123  DISTR",
				"ICT LOT 526  NWD PART E 1/2, EXC N ", "2 FT NOW LANE.", "",
				"", 1077000, 9300, 2012, 1105000, 7300, 1912, 1925));
		propertyTaxes.add(new PropertyTax("023-773-359", "STRATA", 0, 63109064,
				"5", "", "LMS2827", "526", "104", "2036", "", "YORK AVE",
				"V6J 1E6", "", "LOT 5  PLAN LMS2827  DISTRICT LOT 5",
				"26  NEW WESTMINSTER UNDIV 64/1283 S",
				"HARE IN COM PROP THEREIN.", "", "", 285000, 135000, 2012,
				277000, 140000, 1997, 1997));
		propertyTaxes.add(new PropertyTax("015-436-713", "LAND", 0, 63604239,
				"19", "11", "VAP229", "540", "", "3443", "", "2ND AVE W",
				"V6R 1J3", "", "LOT 19  BLOCK 11  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 88400,
				2012, 1366000, 106000, 1912, 1947));
		propertyTaxes.add(new PropertyTax("015-433-927", "LAND", 0, 63605307,
				"24", "14", "VAP229", "540", "", "1788", "", "TRUTCH ST",
				"V6K 4G1", "", "LOT 24  BLOCK 14  PLAN VAP229  DIST",
				"RICT LOT 540  NEW WESTMINSTER", "", "", "", 1625000, 722000,
				2012, 1366000, 683000, 2006, 2006));
		propertyTaxes.add(new PropertyTax("015-671-275", "STRATA", 0, 63809784,
				"21", "", "VAS2581", "526", "421", "1820", "", "3RD AVE W",
				"V6J 1K8", "", "LOT 21  PLAN VAS2581  DISTRICT LOT ",
				"526  NEW WESTMINSTER UNDIV 79/1629 ",
				"SHARE IN COM PROP THEREIN.", "", "", 328000, 138000, 2012,
				319000, 144000, 1990, 1990));
		propertyTaxes.add(new PropertyTax("028-195-051", "STRATA", 0, 63809794,
				"28", "", "BCS3790", "526", "606", "1808", "", "3RD AVE W",
				"V6J 0C4", "", "LOT 28  PLAN BCS3790  DISTRICT LOT ",
				"526  NWD GROUP 1, TOGETHER WITH AN ",
				"INTEREST IN THE COMMON PROPERTY IN ",
				"PROPORTION TO THE UNIT ENTITLEMENT ",
				"OF THE STRATA LOT AS SHOW ON FORM 1", 250000, 151000, 2012,
				229000, 154000, 2010, 2010));
		propertyTaxes.add(new PropertyTax("023-936-631", "STRATA", 0, 15760602,
				"289", "", "LMS2995", "FC", "", "1015", "", "EXPO BLVD",
				"V6Z 2W1", "", "LOT 289  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 72/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 405000, 502000, 2012,
				418000, 512000, 1997, 1997));
		propertyTaxes.add(new PropertyTax("023-938-072", "STRATA", 0, 15760602,
				"433", "", "LMS2995", "FC", "2307", "1009", "", "EXPO BLVD",
				"V6Z 2V9", "", "LOT 433  PLAN LMS2995  DISTRICT LOT",
				" FC  NEW WESTMINSTER UNDIV 42/19193",
				" SHARE IN COM PROP THEREIN.", "", "", 306000, 292000, 2012,
				259000, 298000, 1997, 1997));
		propertyTaxes.add(new PropertyTax("018-381-430", "STRATA", 0, 15761251,
				"17", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 17  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		propertyTaxes.add(new PropertyTax("018-381-448", "STRATA", 0, 15761251,
				"18", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 18  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"18/10000 SHARE IN COM PROP THEREIN.", "", "", 8800, 6100,
				2012, 8400, 6500, 1993, 1993));
		propertyTaxes.add(new PropertyTax("018-381-456", "STRATA", 0, 15761251,
				"19", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 19  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		propertyTaxes.add(new PropertyTax("018-381-464", "STRATA", 0, 15761251,
				"20", "", "LMS1008", "", "", "1200", "", "PACIFIC BLVD", "",
				"", "LOT 20  PLAN LMS1008  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"23/10000 SHARE IN COM PROP THEREIN.", "", "", 7100, 7800,
				2012, 6500, 8400, 1993, 1993));
		propertyTaxes.add(new PropertyTax("024-843-491", "STRATA", 0, 15960788,
				"24", "", "LMS4255", "", "505", "193", "", "AQUARIUS MEWS",
				"V6Z 2Z2", "", "LOT 24  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"58/33012 SHARE IN COM PROP THEREIN.", "", "", 211000, 179000,
				2012, 202000, 185000, 2000, 2000));
		propertyTaxes.add(new PropertyTax("024-843-571", "STRATA", 0, 15960788,
				"32", "", "LMS4255", "", "606", "193", "", "AQUARIUS MEWS",
				"V6Z 2Z2", "", "LOT 32  PLAN LMS4255  NEW WESTMINST",
				"ER DISTRICT LOT FALSE CREEK, UNDIV ",
				"67/33012 SHARE IN COM PROP THEREIN.", "", "", 254000, 212000,
				2012, 242000, 219000, 2000, 2000));
		propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));
		propertyTaxes.add(new PropertyTax("015-539-199", "LAND", 0, 19064895,
				"10", "51", "VAP197", "200A", "", "175", "", "BROADWAY E",
				"V5T 1W2", "", "LOT 10  BLOCK 51  PLAN VAP197  DIST",
				"RICT LOT 200A  NEW WESTMINSTER LOT ",
				"10 EXC S 10FT NOW ROAD, LOT 9, BLOC",
				"K 51, PLAN VAP197, DISTRICT LOT 200",
				"A, NEW WESTMINSTER LAND DISTRICT, L", 2479000, 10457000, 2012,
				2266000, 7302000, 1912, 1984));

		this.propertyTaxes = propertyTaxes;
	}

	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
	}

	private void addPropertyTax(final PropertyTax propertyTax) {
		propertyTaxService.addPropertyTax(propertyTax,
				new AsyncCallback<Void>() {
					public void onFailure(Throwable error) {
						handleError(error);
					}

					public void onSuccess(Void ignore) {
						// displayPropertyTax(propertyTax);
					}
				});
	}

	private void displayPropertyTaxes() {
		propertyTaxesGrid.setStyleName("sample");

		for (int i = 0; i < this.propertyTaxes.size(); i++) {
			int row = i + 1;
			PropertyTax propertyTax = this.propertyTaxes.get(i);

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

	}
}
