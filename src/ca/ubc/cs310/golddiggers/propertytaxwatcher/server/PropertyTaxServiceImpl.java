package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.exception.NotLoggedInException;
import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.PropertyTaxService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PropertyTaxServiceImpl extends RemoteServiceServlet implements
		PropertyTaxService
{

	private static final long serialVersionUID = -8874788298711784539L;

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public void addPropertyTax(PropertyTax propertyTax)
			throws NotLoggedInException
	{
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try
		{
			pm.makePersistent(propertyTax);
		} finally
		{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void removePropertyTax(String pid) throws NotLoggedInException
	{
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try
		{
			long deleteCount = 0;
			Query q = pm.newQuery(PropertyTax.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			List<PropertyTax> propertyTaxes = (List<PropertyTax>) q
					.execute(getUser());
			for (PropertyTax propertyTax : propertyTaxes)
			{
				if (pid.equals(propertyTax.getPid()))
				{
					deleteCount++;
					pm.deletePersistent(propertyTax);
				}
			}
			if (deleteCount != 1)
			{
				System.out
						.println("Deleted " + deleteCount + " Property Taxes");
			}
		} finally
		{
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PropertyTax> getPropertyTaxes()
			throws NotLoggedInException
	{
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		ArrayList<PropertyTax> propertyTaxes = new ArrayList<PropertyTax>();
		try
		{
			Query q = pm.newQuery(PropertyTax.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			propertyTaxes = (ArrayList<PropertyTax>) q.execute(getUser());

		} finally
		{
			pm.close();
		}
		return propertyTaxes;
	}

	private void checkLoggedIn() throws NotLoggedInException
	{
		if (getUser() == null)
		{
			throw new NotLoggedInException("Not logged in.");
		}
	}

	private User getUser()
	{
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private PersistenceManager getPersistenceManager()
	{
		return PMF.getPersistenceManager();
	}
}