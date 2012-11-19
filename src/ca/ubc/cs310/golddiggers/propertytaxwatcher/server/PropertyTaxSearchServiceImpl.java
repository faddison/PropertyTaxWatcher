package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.PropertyTaxSearchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PropertyTaxSearchServiceImpl extends RemoteServiceServlet
		implements PropertyTaxSearchService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String createParameter(SearchParameter para)
	{
		// String parameter = "com.google.appengine.api.users.User u";
		String parameter = "";

		if (para.hasPostalCode())
		{
			parameter += ", String postal";
		}
		if (para.hasMinCurrentLandValue())
		{
			parameter += ", double minCurrentLandVal";
		}
		if (para.hasMaxCurrentLandValue())
		{
			parameter += ", double maxCurrentLandVal";
		}
		return parameter.replaceFirst(", ", "");
	}

	@Override
	public ArrayList<PropertyTax> searchProperty(SearchParameter para)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
