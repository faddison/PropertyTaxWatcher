package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Hubert Ngu
 */
public interface TweeterServiceAsync
{
	public void updateStatus(String message, AsyncCallback<Void> async);

	public void getStatusFeed(AsyncCallback<ArrayList<String>> async);
}
