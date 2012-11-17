package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.LoginInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * LoginServiceAsync interface.
 * 
 * @author Hubert Ngu
 */
public interface LoginServiceAsync {

	/**
	 * Login method that must be implemented.
	 * 
	 * @param requestUrl
	 *            The request url for the login.
	 * @param async
	 *            An asynchronous call back.
	 */
	public void login(String requestUrl, AsyncCallback<LoginInfo> async);

}
