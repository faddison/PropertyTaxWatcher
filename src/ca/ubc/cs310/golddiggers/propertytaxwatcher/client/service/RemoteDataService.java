package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.PropertyTax;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RemoteData")
public interface RemoteDataService extends RemoteService
{
	public ArrayList<PropertyTax> getData();
}
