package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.LoginInfo;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.PropertyTaxWatcher;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.LoginService;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.LoginServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * LoginPage class. This class represents the login page on the Property Tax
 * Watcher web app.
 * 
 * @author Hubert Ngu
 */
public class LoginPage extends Page
{

	private static final String LOGIN_MESSAGE = "<div class = \"hero-unit\"><center>Please sign in to your Google Account "
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
		VerticalPanel layout = new VerticalPanel();
		RootPanel root = RootPanel.get();
		root.clear();
		
		layout.setStylePrimaryName("container-narrow");
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		// Assemble login panel.
		HTML loginLabel = new HTML(LOGIN_MESSAGE);
		layout.add(loginLabel);
		
		Anchor signInLink = new Anchor("Sign In");
		signInLink.setStylePrimaryName("btn");
		signInLink.setHref(PropertyTaxWatcher.loginInfo.getLoginUrl());
		layout.add(signInLink);
		
		root.add(layout);
	}

}
