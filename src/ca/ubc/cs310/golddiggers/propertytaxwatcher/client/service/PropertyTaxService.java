package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import java.util.List;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.exception.NotLoggedInException;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("PropertyTax")
public interface PropertyTaxService extends RemoteService {
	public void addPropertyTax(PropertyTax propertyTax)
			throws NotLoggedInException;

	public void removePropertyTax(String pid) throws NotLoggedInException;

	public List<PropertyTax> getPropertyTaxes()
			throws NotLoggedInException;
}