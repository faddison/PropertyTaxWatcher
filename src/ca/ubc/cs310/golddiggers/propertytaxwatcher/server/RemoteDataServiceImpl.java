package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import java.util.ArrayList;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.client.service.RemoteDataService;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RemoteDataServiceImpl extends RemoteServiceServlet implements RemoteDataService
{
	public ArrayList<PropertyTax> getData() {
		ArrayList<PropertyTax> entityInfo = new ArrayList<PropertyTax>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("PropertyTaxInfo");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			entityInfo.add(parseEntity(result));
		}
		
		return entityInfo;
	}
	
	private PropertyTax parseEntity(Entity entity) {
		String pid = (String) entity.getProperty("PID");
		String legalType = (String) entity.getProperty("LEGAL_TYPE");
		String fromCivicNum = (String) entity.getProperty("FROM_CIVIC_NUMBER");
		String toCivicNum = (String) entity.getProperty("TO_CIVIC_NUMBER");
		String streetName = (String) entity.getProperty("STREET_NAME");
		String postalCode = (String) entity.getProperty("PROPERTY_POSTAL_CODE");
		
		double currentVal = 0.0;
		double currentImproveVal = 0.0;
		int assessYear = 0;
		double prevVal = 0.0;
		double prevImproveVal = 0.0;
		int yearBuilt = 0;
		int bigImproveYear = 0;
		
		if (!((String) entity.getProperty("CURRENT_LAND_VALUE")).equals(""))
			currentVal = Double.parseDouble((String) entity.getProperty("CURRENT_LAND_VALUE"));
		
		if (!((String) entity.getProperty("CURRENT_IMPROVEMENT_VALUE")).equals(""))
			currentImproveVal = Double.parseDouble((String) entity.getProperty("CURRENT_IMPROVEMENT_VALUE"));
		
		if (!((String) entity.getProperty("ASSESSMENT_YEAR")).equals(""))
			assessYear = (int) Double.parseDouble((String) entity.getProperty("ASSESSMENT_YEAR"));
		
		if (!((String) entity.getProperty("PREVIOUS_LAND_VALUE")).equals(""))
			prevVal = Double.parseDouble((String) entity.getProperty("PREVIOUS_LAND_VALUE"));
		
		if (!((String) entity.getProperty("PREVIOUS_IMPROVEMENT_VALUE")).equals(""))
			prevImproveVal = Double.parseDouble((String) entity.getProperty("PREVIOUS_IMPROVEMENT_VALUE"));
		
		if (!((String) entity.getProperty("YEAR_BUILT")).equals(""))
			yearBuilt = (int) Double.parseDouble((String) entity.getProperty("YEAR_BUILT"));
		
		if (!((String) entity.getProperty("BIG_IMPROVEMENT_YEAR")).equals(""))
			bigImproveYear = (int) Double.parseDouble((String) entity.getProperty("BIG_IMPROVEMENT_YEAR"));
			
		
		
		return new PropertyTax(pid, legalType, (long) 0,
			(long) 0, "", "", "",
			"", fromCivicNum,
			toCivicNum, "",
			streetName, postalCode,
			"", "",
			"", "",
			"", "",
			currentVal, currentImproveVal,
			assessYear, prevVal,
			prevImproveVal, yearBuilt,
			bigImproveYear);
	}
}
