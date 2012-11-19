package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.widget;

import java.util.HashMap;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.DataTable;



public class GwtMapWidget extends Composite
{
	
	private MapWidget map;
	private Geocoder geocoder;
	private DataTable table;
	private HashMap<String, LatLng> latLngMap = new HashMap<String, LatLng>();
	
	public GwtMapWidget(String apiKey)
	{
		
		Maps.loadMapsApi(apiKey, "2", false, new Runnable() 
		{

			@Override
			public void run()
			{
				System.out.println("creating map");
				map = new MapWidget(LatLng.newInstance(49.2505, -123.1119), 13);
			    map.setSize("600px", "400px");
			    geocoder = new Geocoder();				
			}
		      
		});
	}
	
	public MapWidget getMapWidget()
	{
		return this.map;
	}
	
	private void addMapMarker(LatLng point)
	{
		System.out.println("Added map marker");
		map.addOverlay(new Marker(point));
	}
	
	public void getLatLng(String zipcode)
	{
		final String address = zipcode;
		geocoder.getLatLng(address, new LatLngCallback() {
		      public void onFailure()
		      {
		        System.out.println(address + " not found");
		      }

		      public void onSuccess(LatLng point) 
		      {
		    	latLngMap.put(address, point);
		        addMapMarker(point);
		      }
		    });
	}
	
	private void addMapMarkers(DataTable dataTable)
	{
		for (int i = 0; i < dataTable.getNumberOfRows(); i++)
		{
			String zipcode = dataTable.getValueString(i, 12);
			if (latLngMap.containsKey(zipcode))
				addMapMarker(latLngMap.get(zipcode));
			else				
				getLatLng(zipcode);
		}
	}
}
