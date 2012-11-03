package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Tweeter")
public interface TweeterService extends RemoteService
{
	public void updateStatus(String message);
}
