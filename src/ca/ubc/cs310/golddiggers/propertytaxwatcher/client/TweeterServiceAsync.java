package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TweeterServiceAsync
{
	public void updateStatus(String message, AsyncCallback<Void> async);
}
