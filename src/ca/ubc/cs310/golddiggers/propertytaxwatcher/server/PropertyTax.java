package ca.ubc.cs310.golddiggers.propertytaxwatcher.server;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class PropertyTax implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private String pid;
	private String legal_type;
	private long folio;
	private long land_coordinate;
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
	private double current_land_value;
	private double current_improvement_value;
	private int assessment_year;
	private double previous_land_value;
	private double previous_improvement_value;
	private int year_built;
	private int big_improvement_year;

	/**
	 * @param pid
	 * @param legal_type
	 * @param folio
	 * @param land_coordinate
	 * @param lot
	 * @param block
	 * @param plan
	 * @param district_lot
	 * @param from_civic_number
	 * @param to_civic_number
	 * @param street_prefix_direction
	 * @param street_name
	 * @param property_postal_code
	 * @param record_status_code
	 * @param narrative_legal_line1
	 * @param narrative_legal_line2
	 * @param narrative_legal_line3
	 * @param narrative_legal_line4
	 * @param narrative_legal_line5
	 * @param current_land_value
	 * @param current_improvement_value
	 * @param assessment_year
	 * @param previous_land_value
	 * @param previous_improvement_value
	 * @param year_built
	 * @param big_improvement_year
	 */
	public PropertyTax(String pid, String legal_type, long folio,
			long land_coordinate, String lot, String block, String plan,
			String district_lot, String from_civic_number,
			String to_civic_number, String street_prefix_direction,
			String street_name, String property_postal_code,
			String record_status_code, String narrative_legal_line1,
			String narrative_legal_line2, String narrative_legal_line3,
			String narrative_legal_line4, String narrative_legal_line5,
			double current_land_value, double current_improvement_value,
			int assessment_year, double previous_land_value,
			double previous_improvement_value, int year_built,
			int big_improvement_year) {
		super();
		this.pid = pid;
		this.legal_type = legal_type;
		this.folio = folio;
		this.land_coordinate = land_coordinate;
		this.lot = lot;
		this.block = block;
		this.plan = plan;
		this.district_lot = district_lot;
		this.from_civic_number = from_civic_number;
		this.to_civic_number = to_civic_number;
		this.street_prefix_direction = street_prefix_direction;
		this.street_name = street_name;
		this.property_postal_code = property_postal_code;
		this.record_status_code = record_status_code;
		this.narrative_legal_line1 = narrative_legal_line1;
		this.narrative_legal_line2 = narrative_legal_line2;
		this.narrative_legal_line3 = narrative_legal_line3;
		this.narrative_legal_line4 = narrative_legal_line4;
		this.narrative_legal_line5 = narrative_legal_line5;
		this.current_land_value = current_land_value;
		this.current_improvement_value = current_improvement_value;
		this.assessment_year = assessment_year;
		this.previous_land_value = previous_land_value;
		this.previous_improvement_value = previous_improvement_value;
		this.year_built = year_built;
		this.big_improvement_year = big_improvement_year;
	}

	public PropertyTax() {
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the legal_type
	 */
	public String getLegal_type() {
		return legal_type;
	}

	/**
	 * @param legal_type
	 *            the legal_type to set
	 */
	public void setLegal_type(String legal_type) {
		this.legal_type = legal_type;
	}

	/**
	 * @return the folio
	 */
	public long getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(long folio) {
		this.folio = folio;
	}

	/**
	 * @return the land_coordinate
	 */
	public long getLand_coordinate() {
		return land_coordinate;
	}

	/**
	 * @param land_coordinate
	 *            the land_coordinate to set
	 */
	public void setLand_coordinate(long land_coordinate) {
		this.land_coordinate = land_coordinate;
	}

	/**
	 * @return the lot
	 */
	public String getLot() {
		return lot;
	}

	/**
	 * @param lot
	 *            the lot to set
	 */
	public void setLot(String lot) {
		this.lot = lot;
	}

	/**
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block
	 *            the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * @return the plan
	 */
	public String getPlan() {
		return plan;
	}

	/**
	 * @param plan
	 *            the plan to set
	 */
	public void setPlan(String plan) {
		this.plan = plan;
	}

	/**
	 * @return the district_lot
	 */
	public String getDistrict_lot() {
		return district_lot;
	}

	/**
	 * @param district_lot
	 *            the district_lot to set
	 */
	public void setDistrict_lot(String district_lot) {
		this.district_lot = district_lot;
	}

	/**
	 * @return the from_civic_number
	 */
	public String getFrom_civic_number() {
		return from_civic_number;
	}

	/**
	 * @param from_civic_number
	 *            the from_civic_number to set
	 */
	public void setFrom_civic_number(String from_civic_number) {
		this.from_civic_number = from_civic_number;
	}

	/**
	 * @return the to_civic_number
	 */
	public String getTo_civic_number() {
		return to_civic_number;
	}

	/**
	 * @param to_civic_number
	 *            the to_civic_number to set
	 */
	public void setTo_civic_number(String to_civic_number) {
		this.to_civic_number = to_civic_number;
	}

	/**
	 * @return the street_prefix_direction
	 */
	public String getStreet_prefix_direction() {
		return street_prefix_direction;
	}

	/**
	 * @param street_prefix_direction
	 *            the street_prefix_direction to set
	 */
	public void setStreet_prefix_direction(String street_prefix_direction) {
		this.street_prefix_direction = street_prefix_direction;
	}

	/**
	 * @return the street_name
	 */
	public String getStreet_name() {
		return street_name;
	}

	/**
	 * @param street_name
	 *            the street_name to set
	 */
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	/**
	 * @return the property_postal_code
	 */
	public String getProperty_postal_code() {
		return property_postal_code;
	}

	/**
	 * @param property_postal_code
	 *            the property_postal_code to set
	 */
	public void setProperty_postal_code(String property_postal_code) {
		this.property_postal_code = property_postal_code;
	}

	/**
	 * @return the record_status_code
	 */
	public String getRecord_status_code() {
		return record_status_code;
	}

	/**
	 * @param record_status_code
	 *            the record_status_code to set
	 */
	public void setRecord_status_code(String record_status_code) {
		this.record_status_code = record_status_code;
	}

	/**
	 * @return the narrative_legal_line1
	 */
	public String getNarrative_legal_line1() {
		return narrative_legal_line1;
	}

	/**
	 * @param narrative_legal_line1
	 *            the narrative_legal_line1 to set
	 */
	public void setNarrative_legal_line1(String narrative_legal_line1) {
		this.narrative_legal_line1 = narrative_legal_line1;
	}

	/**
	 * @return the narrative_legal_line2
	 */
	public String getNarrative_legal_line2() {
		return narrative_legal_line2;
	}

	/**
	 * @param narrative_legal_line2
	 *            the narrative_legal_line2 to set
	 */
	public void setNarrative_legal_line2(String narrative_legal_line2) {
		this.narrative_legal_line2 = narrative_legal_line2;
	}

	/**
	 * @return the narrative_legal_line3
	 */
	public String getNarrative_legal_line3() {
		return narrative_legal_line3;
	}

	/**
	 * @param narrative_legal_line3
	 *            the narrative_legal_line3 to set
	 */
	public void setNarrative_legal_line3(String narrative_legal_line3) {
		this.narrative_legal_line3 = narrative_legal_line3;
	}

	/**
	 * @return the narrative_legal_line4
	 */
	public String getNarrative_legal_line4() {
		return narrative_legal_line4;
	}

	/**
	 * @param narrative_legal_line4
	 *            the narrative_legal_line4 to set
	 */
	public void setNarrative_legal_line4(String narrative_legal_line4) {
		this.narrative_legal_line4 = narrative_legal_line4;
	}

	/**
	 * @return the narrative_legal_line5
	 */
	public String getNarrative_legal_line5() {
		return narrative_legal_line5;
	}

	/**
	 * @param narrative_legal_line5
	 *            the narrative_legal_line5 to set
	 */
	public void setNarrative_legal_line5(String narrative_legal_line5) {
		this.narrative_legal_line5 = narrative_legal_line5;
	}

	/**
	 * @return the current_land_value
	 */
	public double getCurrent_land_value() {
		return current_land_value;
	}

	/**
	 * @param current_land_value
	 *            the current_land_value to set
	 */
	public void setCurrent_land_value(double current_land_value) {
		this.current_land_value = current_land_value;
	}

	/**
	 * @return the current_improvement_value
	 */
	public double getCurrent_improvement_value() {
		return current_improvement_value;
	}

	/**
	 * @param current_improvement_value
	 *            the current_improvement_value to set
	 */
	public void setCurrent_improvement_value(double current_improvement_value) {
		this.current_improvement_value = current_improvement_value;
	}

	/**
	 * @return the assessment_year
	 */
	public int getAssessment_year() {
		return assessment_year;
	}

	/**
	 * @param assessment_year
	 *            the assessment_year to set
	 */
	public void setAssessment_year(int assessment_year) {
		this.assessment_year = assessment_year;
	}

	/**
	 * @return the previous_land_value
	 */
	public double getPrevious_land_value() {
		return previous_land_value;
	}

	/**
	 * @param previous_land_value
	 *            the previous_land_value to set
	 */
	public void setPrevious_land_value(double previous_land_value) {
		this.previous_land_value = previous_land_value;
	}

	/**
	 * @return the previous_improvement_value
	 */
	public double getPrevious_improvement_value() {
		return previous_improvement_value;
	}

	/**
	 * @param previous_improvement_value
	 *            the previous_improvement_value to set
	 */
	public void setPrevious_improvement_value(double previous_improvement_value) {
		this.previous_improvement_value = previous_improvement_value;
	}

	/**
	 * @return the year_built
	 */
	public int getYear_built() {
		return year_built;
	}

	/**
	 * @param year_built
	 *            the year_built to set
	 */
	public void setYear_built(int year_built) {
		this.year_built = year_built;
	}

	/**
	 * @return the big_improvement_year
	 */
	public int getBig_improvement_year() {
		return big_improvement_year;
	}

	/**
	 * @param big_improvement_year
	 *            the big_improvement_year to set
	 */
	public void setBig_improvement_year(int big_improvement_year) {
		this.big_improvement_year = big_improvement_year;
	}

}
