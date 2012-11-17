package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.TweeterService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.TweeterServiceAsync;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget.DataTablePanel;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;

/**
 * SearchPage class. This class represents the search page on the Property Tax
 * Watch web app.
 * 
 * @author Hubert Ngu
 */
public class SearchPage extends Page
{
	// Search fields.
	private HorizontalPanel searchPanel = new HorizontalPanel();
	private VerticalPanel currentValPanel = new VerticalPanel();
	private VerticalPanel postalCodePanel = new VerticalPanel();
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
		PropertyTaxWatcher.initializeLocalPropertyTaxes(propertyTaxes);
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
				DataTable dataTable = DataTable.create();
				dataTable = PropertyTaxWatcher.initializeDataTable(dataTable);
				dataTable = PropertyTaxWatcher.populateDataTable(dataTable,
						propertyTaxes);
				createSearchWidget(dataTable);
				RootPanel.get().add(resultTabPanel);
				resultTabPanel.setSize("600px", "400px");
				resultTabPanel.setVisible(hasSearch);
			}
		}, Table.PACKAGE);
	}

	private void createSearchWidget(final DataTable dataTable)
	{
		searchPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		currentValPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		postalCodePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		currentValPanel.add(currentValLabel);
		currentValPanel.add(minCurrentValTextBox);
		currentValPanel.add(maxCurrentValTextBox);
		currentValPanel.add(searchCurrentValButton);

		postalCodePanel.add(postalCodeLabel);
		postalCodePanel.add(postalCodeTextBox);
		postalCodePanel.add(searchPostalCodeButton);

		searchPanel.add(currentValPanel);
		searchPanel.add(postalCodePanel);

		currentValLabel.setText("Search over a range of property values: ");
		postalCodeLabel.setText("Search for property values in a postal code:");
		minCurrentValTextBox.setText("Minimum Value");
		maxCurrentValTextBox.setText("Maximum Value");
		
		searchPanel.setStylePrimaryName("container-narrow");
		searchPanel.addStyleName("row");
		currentValPanel.setStylePrimaryName("span4");
		currentValPanel.addStyleName("hero-unit");
		postalCodePanel.setStylePrimaryName("span4");
		postalCodePanel.addStyleName("hero-unit");
		searchCurrentValButton.setStylePrimaryName("btn btn-success");
		searchPostalCodeButton.setStylePrimaryName("btn btn-success");

		RootPanel.get().add(searchPanel);

		// Listen for mouse events on the search button.
		searchCurrentValButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{

				double min;
				double max;
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
				String message = PropertyTaxWatcher.loginInfo.getNickname()
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
				String message = PropertyTaxWatcher.loginInfo.getNickname()
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
}
