/*
 * Este modelo de datos representara/contendra los errores de las fechas
 * de las instancias de parcela que pueden haber al momento
 * de crear o modificar una instancia de parcela
 */

package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="ERROR_FECHA")
public class DateError {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="DATE_ERROR_ID")
  private int id;

  @Column(name="NAME")
  private String name;

  @Column(name="DESCRIPTION")
  private String description;

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
	 * Returns value of description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

}
