package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Tweeter Service interface.
 * 
 * @author Hubert Ngu
 */
@RemoteServiceRelativePath("Tweeter")
public interface TweeterService extends RemoteService
{
	/**
	 * Updates the service's twitter status with a message.
	 * 
	 * @param message
	 *            The message to update with.
	 */
	public void updateStatus(String message);

	/**
	 * Gets the service's twitter status updates.
	 * 
	 * @return A list of twitter status updates.
	 */
	public ArrayList<String> getStatusFeed();
}
