package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Field {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="FIELD_ID")
  private int id;

  @Column(name="NAME", nullable=false)
  private String name;

  @Column(name="LONGITUDE", nullable=false)
  private double longitude;

  @Column(name="LATITUDE", nullable=false)
  private double latitude;

  @Column(name="AREA", nullable=false)
  private int area; // superficie

  // @Column(name="COUNTRY", nullable=false)
  // private String country;

  // @Column(name="PROVINCE", nullable=false)
  // private String province;

  // Constructor method
  public Field() {
  }

  /* Getters and setters */

	/**
	 * Returns value of id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns value of name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets new value of name
	 * @param
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns value of longitude
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets new value of longitude
	 * @param
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Returns value of latitude
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets new value of latitude
	 * @param
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Returns value of area
	 * @return
	 */
	public int getArea() {
		return area;
	}

	/**
	 * Sets new value of area
	 * @param
	 */
	public void setArea(int area) {
		this.area = area;
	}

	/**
	 * Returns value of country
	 * @return
	 */
	// public String getCountry() {
	// 	return country;
	// }

	/**
	 * Sets new value of country
	 * @param
	 */
	// public void setCountry(String country) {
	// 	this.country = country;
	// }

	/**
	 * Returns value of province
	 * @return
	 */
	// public String getProvince() {
	// 	return province;
	// }

	/**
	 * Sets new value of province
	 * @param
	 */
	// public void setProvince(String province) {
	// 	this.province = province;
	// }

  @Override
  public String toString() {
    return "Field id: " + id + " name: " + name + " longitude " + longitude + " latitude: " + latitude;
  }

}
