package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PropertyTaxServiceAsync {
	public void addPropertyTax(PropertyTax propertyTax,
			AsyncCallback<Void> async);

	public void removePropertyTax(String pid, AsyncCallback<Void> async);

	public void getPropertyTaxes(AsyncCallback<ArrayList<PropertyTax>> async);
}