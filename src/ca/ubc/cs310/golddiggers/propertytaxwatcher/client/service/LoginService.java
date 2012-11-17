package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.LoginInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * LoginService class.
 * 
 * @author Hubert Ngu
 */
@RemoteServiceRelativePath("Login")
public interface LoginService extends RemoteService {
	public LoginInfo login(String requestUri);
}
