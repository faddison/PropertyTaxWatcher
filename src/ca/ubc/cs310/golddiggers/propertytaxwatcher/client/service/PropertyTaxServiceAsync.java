package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import java.util.List;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PropertyTaxServiceAsync {
	public void addPropertyTax(PropertyTax propertyTax,
			AsyncCallback<Void> async);

	public void removePropertyTax(String pid, AsyncCallback<Void> async);

	public void getPropertyTaxes(AsyncCallback<List<PropertyTax>> asyncCallback);
}