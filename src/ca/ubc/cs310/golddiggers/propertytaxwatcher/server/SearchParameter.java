package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import java.io.Serializable;

public class SearchParameter implements Serializable
{
	private static final long serialVersionUID = -5433609373903585225L;
	
	private String postal_code;
	private boolean has_postal_code;
	private double min_current_land_value;
	private boolean has_min_current_land_value;
	private double max_current_land_value;
	private boolean has_max_current_land_value;

	public SearchParameter()
	{
		super();
		this.has_postal_code = false;
		this.has_min_current_land_value = false;
		this.has_max_current_land_value = false;
	}

	public void setPostalCode(String postal)
	{
		this.postal_code = postal;
		this.has_postal_code = true;
	}

	public String getPostalCode()
	{
		return postal_code;
	}

	public boolean hasPostalCode()
	{
		return has_postal_code;
	}

	public void setMinCurrentLandValue(double min)
	{
		this.min_current_land_value = min;
		this.has_min_current_land_value = true;
	}

	public double getMinCurrentLandValue()
	{
		return min_current_land_value;
	}

	public boolean hasMinCurrentLandValue()
	{
		return has_min_current_land_value;
	}

	public void setMaxCurrentLandValue(double max)
	{
		this.max_current_land_value = max;
		this.has_max_current_land_value = true;
	}

	public double getMaxCurrentLandValue()
	{
		return max_current_land_value;
	}

	public boolean hasMaxCurrentLandValue()
	{
		return has_max_current_land_value;
	}
}
