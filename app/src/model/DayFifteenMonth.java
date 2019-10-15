/*
 * Esta clase representa el numero del dia del año de cada dia 15 de cada
 * mes del año
 *
 * Por ejemplo, el dia 15 de Enero es el dia numero 15 del año
 * mientras que el dia 15 de Febrero es el dia numero 46 del año
 *
 * Para ver los numeros de cada dia del año ver el cuado A2.5 de
 * la pagina 215 del Anexo dos del libro FAO 56
 */

package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="DAY_FIFTEEN_MONTH")
public class DayFifteenMonth {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="DAY_ID")
  private int id;

  @Column(name="NUMBER_DAY")
  private int dayNumberFifteen;

  // Constructor method
  public DayFifteenMonth() {
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
  * Returns value of dayNumberFifteen
  * @return dayNumberFifteen
  */
  public int getDayNumber() {
    return dayNumberFifteen;
  }

  /**
   * Sets new value of dayNumberFifteen
   * @param dayNumberFifteen
   */
  public void setDayNumber(int dayNumberFifteen) {
    this.dayNumberFifteen = dayNumberFifteen;
  }

}
