package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Month {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="MONTH_ID")
  private int id;

  @Column(name="NAME_MONTH", unique=true)
  private String nameMonth;

  // Constructor method
  public Month() {
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
   * Returns value of nameMonth
   * @return nameMonth
   */
  public String getMonth() {
    return nameMonth;
  }

}
