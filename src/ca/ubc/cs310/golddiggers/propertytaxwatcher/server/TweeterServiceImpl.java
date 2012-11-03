package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import twitter4j.TwitterException;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.TweeterService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.social.twitter.Tweeter;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TweeterServiceImpl extends RemoteServiceServlet implements
		TweeterService
{
	// Auto generated serial version UID.
	private static final long serialVersionUID = -8579983943482804237L;

	private static Tweeter tweeter = new Tweeter();

	@Override
	public void updateStatus(String message)
	{
		try
		{
			tweeter.updateStatus(message);
		} catch (TwitterException e)
		{
			e.printStackTrace();
		}
	}

}
