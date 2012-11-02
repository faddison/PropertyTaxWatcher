package ca.ubc.cs310.golddiggers.propertytaxwatcher.server.social;

import org.apache.commons.lang.NotImplementedException;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.social.twitter.Tweeter;

/**
 * Gets instances of social media interfaces.
 * 
 * @author Hubert Ngu
 */
public class SocialMediaFactory
{
	private static Tweeter tweeter;

	public SocialMediaFactory()
	{
		throw new NotImplementedException(
				"This class is not meant to be initialized");
	}

	public static Tweeter getTweeterInstance()
	{
		if (tweeter == null)
		{
			tweeter = new Tweeter();
		}
		return tweeter;
	}

}
