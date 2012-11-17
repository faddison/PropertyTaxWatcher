package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.LoginInfo;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.LoginService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.LoginServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * LoginPage class. This class represents the login page on the Property Tax
 * Watcher web app.
 * 
 * @author Hubert Ngu
 */
public class LoginPage extends Page
{

	private static final String LOGIN_MESSAGE = "Please sign in to your Google Account "
			+ "to access the PropertyTaxWatcher application.";

	public LoginPage()
	{
		super("Login");
	}

	@Override
	public void loadPage()
	{
		// Load the login options.
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>()
				{
					public void onFailure(Throwable error)
					{
						System.out.println(error.getMessage());
					}

					public void onSuccess(LoginInfo result)
					{
						PropertyTaxWatcher.loginInfo = result;
						if (PropertyTaxWatcher.loginInfo.isLoggedIn())
						{
							PropertyTaxWatcher.HOME_PAGE.loadPage();
						} else
						{
							loadLoginPage();
						}
					}
				});
	}

	private void loadLoginPage()
	{
		RootPanel root = RootPanel.get();
		root.clear();

		// Assemble login panel.
		Label loginLabel = new Label(LOGIN_MESSAGE);
		root.add(loginLabel);

		Anchor signInLink = new Anchor("Sign In");
		signInLink.setHref(PropertyTaxWatcher.loginInfo.getLoginUrl());
		root.add(signInLink);
	}

}
