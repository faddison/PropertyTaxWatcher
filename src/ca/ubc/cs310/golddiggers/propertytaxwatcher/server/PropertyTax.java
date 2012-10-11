package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class PropertyTax 
{
	private String pid;
	private String legal_type;
	private String folio;
	private String land_coordinate;
	private String lot;
	private String block;
	private String plan;
	private String district_lot;
	private String from_civic_number;
	private String to_civic_number;
	private String street_prefix_direction;
	private String street_name;
	private String property_postal_code;
	private String record_status_code;
	private String narrative_legal_line1;
	private String narrative_legal_line2;
	private String narrative_legal_line3;
	private String narrative_legal_line4;
	private String narrative_legal_line5;
	private String current_land_value;
	private String current_improvement_value;
	private String assessment_year;
	private String previous_land_value;
	private String previous_improvement_value;
	private String year_built;
	private String big_improvement_year;

}
