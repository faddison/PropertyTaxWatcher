package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.LoginInfo;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.LoginService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * LoginServiceImpl class. This class contains the implementation of the
 * LoginService on the server side.
 * 
 * @author Hubert Ngu
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	// Auto generated Serial Version UID.
	private static final long serialVersionUID = 1467762698759200033L;

	/**
	 * Logs a user into the system. If no user is found then the user is not
	 * logged in.
	 * 
	 * @param requestUri
	 *            The requestUri.
	 * @return The LoginInfo of a user.
	 */
	public LoginInfo login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}

		return loginInfo;
	}

}
