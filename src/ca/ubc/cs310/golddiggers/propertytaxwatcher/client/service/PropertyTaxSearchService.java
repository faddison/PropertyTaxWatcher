package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.SearchParameter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Search")
public interface PropertyTaxSearchService extends RemoteService
{
	public ArrayList<PropertyTax> searchProperty(SearchParameter para);
}
