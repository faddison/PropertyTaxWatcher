package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RemoteDataServiceAsync
{
	public void getData(AsyncCallback<ArrayList<PropertyTax>> async);
}
