package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.PropertyTaxSearchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PropertyTaxSearchServiceImpl extends RemoteServiceServlet implements
		PropertyTaxSearchService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	@SuppressWarnings("unchecked")
	public ArrayList<PropertyTax> searchProperty(SearchParameter para){
		PersistenceManager pm = getPersistenceManager();
		List<PropertyTax> propertyTaxes = null;
		System.out.println("Remote reached!");
		try {
			Query q = pm.newQuery(PropertyTax.class);
			q.setFilter(createFilter(para));
			q.declareParameters(createParameter(para));
			q.setOrdering("this.current_land_value asc, this.property_postal_code asc");

			try{
				if (para.hasPostalCode() && para.hasMinCurrentLandValue() 
						&& para.hasMaxCurrentLandValue()){
					
					propertyTaxes = (List<PropertyTax>) q.execute(para.getPostalCode(), 
							para.getMinCurrentLandValue(), para.getMaxCurrentLandValue());
				}
				else if (!para.hasPostalCode() && para.hasMinCurrentLandValue() 
						&& para.hasMaxCurrentLandValue()){
					
					propertyTaxes = (List<PropertyTax>) q.execute(para.getMinCurrentLandValue(), 
							para.getMaxCurrentLandValue());
				}
				else if (para.hasPostalCode() && !para.hasMinCurrentLandValue() 
						&& para.hasMaxCurrentLandValue()){
	
					propertyTaxes = (List<PropertyTax>) q.execute(para.getPostalCode(), 
							para.getMaxCurrentLandValue());
				}
				else if (para.hasPostalCode() && para.hasMinCurrentLandValue() 
						&& !para.hasMaxCurrentLandValue()){
	
					propertyTaxes = (List<PropertyTax>) q.execute(para.getPostalCode(), 
							para.getMinCurrentLandValue());
				}
				else if (para.hasPostalCode() && !para.hasMinCurrentLandValue() 
						&& !para.hasMaxCurrentLandValue()){
	
					propertyTaxes = (List<PropertyTax>) q.execute(para.getPostalCode());
				}
				else if (!para.hasPostalCode() && para.hasMinCurrentLandValue() 
						&& !para.hasMaxCurrentLandValue()){
	
					propertyTaxes = (List<PropertyTax>) q.execute(para.getMinCurrentLandValue());
				}
				else if (!para.hasPostalCode() && !para.hasMinCurrentLandValue() 
						&& para.hasMaxCurrentLandValue()){
	
					propertyTaxes = (List<PropertyTax>) q.execute(para.getMaxCurrentLandValue());
				}
			}
			finally{
				q.closeAll();
			}

			propertyTaxes.size();
			System.out.println("Remote excuted!");
		} 
		finally {
			pm.close();
		}
		return new ArrayList<PropertyTax>(propertyTaxes);
	}
	
	private String createParameter(SearchParameter para){
		//String parameter = "com.google.appengine.api.users.User u";
		String parameter = "";
		
		if (para.hasPostalCode()){
			parameter += ", String postal";
		}
		if (para.hasMinCurrentLandValue()){
			parameter += ", double minCurrentLandVal";
		}
		if (para.hasMaxCurrentLandValue()){
			parameter += ", double maxCurrentLandVal";
		}
		return parameter.replaceFirst(", ", "");
	}

	private String createFilter(SearchParameter para){
		//String filter = "user == u";
		String filter = "";
		
		if (para.hasPostalCode()){
			
			filter += " && this.property_postal_code == postal";
			/*
			filter += " && this.property_postal_code.substring(0,2)." +
					"matches(postal.substring(0,2)) && "+
					"this.property_postal_code.substring(4,6)."+
					"matches(postal.substring(3,5))";
					*/
			/*
			filter += " && this.property_postal_code." +
					"matches(postal.substring(0,2)+\" \"+postal.substring(3,5))";
					*/
		}
		if (para.hasMinCurrentLandValue()){
			filter += " && this.current_land_value >= minCurrentLandVal";
		}
		if (para.hasMaxCurrentLandValue()){
			filter += " && this.current_land_value <= maxCurrentLandVal";
		}

		return filter.replaceFirst(" && ", "");
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
