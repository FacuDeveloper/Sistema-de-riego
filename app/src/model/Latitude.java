package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Latitude {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="LATITUDE_ID")
  private int id;

  @Column(name="DECIMAL_LATITUDE", unique=true)
  private int decimalLatitude;

  // Constructor method
  public Latitude() {
  }

  /* Getters and setters */

	/**
	* Returns value of id
	* @return id
	*/
	public int getId() {
		return id;
	}

  /**
   * Returns value of decimalLatitude
   * @return decimalLatitude
   */
  public int getLatitude() {
    return decimalLatitude;
  }

  /**
   * Sets new value of decimalLatitude
   * @param decimalLatitude
   */
  public void setLatitude(int decimalLatitude) {
    this.decimalLatitude = decimalLatitude;
  }

}
