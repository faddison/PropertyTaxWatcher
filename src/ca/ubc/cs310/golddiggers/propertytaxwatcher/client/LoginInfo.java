package ca.ubc.cs310.golddiggers.propertytaxwatcher.client;

import java.io.Serializable;

/**
 * LoginInfo class which represents the information of a User logged into the
 * system.
 * 
 * @author Hubert Ngu
 */
public class LoginInfo implements Serializable {

	// Auto generated Serial Version UID.
	private static final long serialVersionUID = 2094106848237949737L;

	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	private String nickname;

	/**
	 * Is the user logged in?
	 * 
	 * @return True if the user is logged in; else false.
	 */
	public boolean isLoggedIn() {
		return this.loggedIn;
	}

	/**
	 * Sets the logged in status.
	 * 
	 * @param loggedIn
	 *            The login status of the user.
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Get the login URL.
	 * 
	 * @return The login URL.
	 */
	public String getLoginUrl() {
		return this.loginUrl;
	}

	/**
	 * Sets the login URL.
	 * 
	 * @param loginUrl
	 *            The login URL to set.
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Gets the logout URL.
	 * 
	 * @return The logout URL.
	 */
	public String getLogoutUrl() {
		return this.logoutUrl;
	}

	/**
	 * Sets the logout URL.
	 * 
	 * @param logoutUrl
	 *            The logout URL to set.
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Get the email address of the user.
	 * 
	 * @return The email address.
	 */
	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * Sets the email address of the user.
	 * 
	 * @param emailAddress
	 *            The email address of the user.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the nickname of the user.
	 * 
	 * @return The nickname.
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * Sets the nickname of the user.
	 * 
	 * @param nickname
	 *            The nickname of the user.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
