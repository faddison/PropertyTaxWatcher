package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.SearchParameter;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PropertyTaxSearchServiceAsync
{
	public void searchProperty(SearchParameter para,
			AsyncCallback<ArrayList<PropertyTax>> asyncCallback);
}
