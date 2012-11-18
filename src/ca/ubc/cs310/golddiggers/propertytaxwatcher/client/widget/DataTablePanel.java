package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.formatters.ArrowFormat;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;

public class DataTablePanel extends FlowPanel
{

	public DataTablePanel(AbstractDataTable table)
	{
		final Table viz = new Table();
		this.add(viz);
		Options options = Table.Options.create();
		options.setShowRowNumber(true);

		ArrowFormat formatter = createFormatter();
		formatter.format((DataTable) table, 1);
		viz.draw(table, options);

	}
	
	private ArrowFormat createFormatter()
	{
		ArrowFormat.Options options = ArrowFormat.Options.create();
		options.setBase(1.5);
		return ArrowFormat.create(options);
	}

}
