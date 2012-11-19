package ca.ubc.cs310.golddiggers.propertytaxwatcher.server.social.twitter;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Tweeter class. This class is responsible for setting up the application's
 * connection to the Twitter API via Twitter4j. This is also the backend
 * implementation used for tweeting and updating statuses.
 * 
 * @author Hubert Ngu
 */
public class Tweeter
{
	// TODO: Fix permission denied for logging on the app engine.
	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(Tweeter.class);

	// The underlying Twitter4j instance.
	private Twitter twitter;

	// These keys were produced using the golddiggers310 Twitter account.
	private static final String CONSUMER_KEY = "S2kJzMGLchDwbi5pc8n7A";
	private static final String CONSUMER_SECRET = "jtHQzLV1GQ8rRGOK2OcPxdv4lYZNWQ0X8OEV5GW5Y";
	private static final String OAUTH_ACCESS_TOKEN = "921500394-wDcbdF241spKkpUEtvN4474EwoKQ38opTWZ9PVPC";
	private static final String OAUTH_ACCESS_TOKEN_SECRET = "1TmKDkOBGBcOmIxsjZSNCugCkUUTotFJiCbDRRgRXmo";

	/**
	 * Constructs a new Tweeter object. The constructor takes care of creating
	 * an instance of the underlying Twitter4J.Twitter object.
	 */
	public Tweeter()
	{
		// Set up the application keys.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CONSUMER_KEY);
		cb.setOAuthConsumerSecret(CONSUMER_SECRET);
		cb.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);

		// Create a Twitter object from the configuration builder.
		TwitterFactory tf = new TwitterFactory(cb.build());
		this.twitter = tf.getInstance();
	}

	/**
	 * Updates status on Twitter.
	 * 
	 * @param message
	 *            The status message.
	 * @throws TwitterException
	 *             If the status failed to update.
	 */
	public void updateStatus(String message) throws TwitterException
	{
		twitter.updateStatus(message);
		// LOGGER.debug("Successfully updated status to {}.", message);
	}

	/**
	 * Gets a list of Twitter status updates. This method only grabs the message
	 * from the Status object.
	 * 
	 * @return List of twitter status updates.
	 * @throws TwitterException
	 *             If the stauses fail to be retrieved.
	 */
	public ArrayList<String> getStatusFeed() throws TwitterException
	{
		ArrayList<String> messages = new ArrayList<String>();

		ResponseList<Status> statuses = twitter.getUserTimeline();
		for (Status status : statuses)
		{
			messages.add(status.getText());
		}

		// LOGGER.debug("Successfully grabbed {} statuses.", messages.size());
		return messages;
	}

	/**
	 * This is just a simple test to see if updateStatus works!
	 * 
	 * See the result at: www.twitter.com/golddiggers310
	 */
	public static void main(String[] args) throws TwitterException
	{
		Tweeter t = new Tweeter();
		t.updateStatus("Check out golddiggers310.appspot.com!");
	}
}
