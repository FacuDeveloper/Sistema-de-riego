package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Parcel {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="PARCEL_ID")
  private int id;

  @Column(name="IDENTIFICATION_NUMBER", unique=true)
  private int identificationNumber;

  @Column(name="AREA", nullable=false)
  private int area; // superficie

  // @ManyToOne
  // @JoinColumn(name="FIELD_ID")
  // private Field field;

  // Constructor method
  public Parcel() {
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
	* Sets new value of id
	* @param
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	* Returns value of identificationNumber
	* @return identificationNumber
	*/
	public int getIdentificationNumber() {
		return identificationNumber;
	}

	/**
	* Sets new value of identificationNumber
	* @param identificationNumber
	*/
	public void setIdentificationNumber(int identificationNumber) {
		this.identificationNumber = identificationNumber;
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
   * Returns field
   * @return
   */
  // public Field getField() {
  //   return field;
  // }

  /**
	 * Sets new value of field
	 * @param
	 */
	// public void setField(Field field) {
	// 	this.field = field;
	// }

  @Override
  public String toString() {
    return "Parcel id: " + id + " identificacion number: " + identificationNumber + " area: " + area;
  }

  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + identificationNumber;
		return result;
	}

  /*
   * Este metodo es necesario para la eliminacion de
   * parcelas.
   *
   * Si este metodo no existe, un objeto de tipo Collection
   * en su metodo remove con Parcel (ver clase Field)
   * va a utilizar el metodo equals() de la clase Object
   * el cual compara referencias y no los contenidos de los
   * objetos involucrados en el uso del metodo equals, con
   * lo cual va a eliminar objetos de tipo Parcel que tengan
   * la misma referencia, esto es, el mismo objeto.
   *
   * Entonces, para que un objeto Collection elimine objetos
   * en funcion de sus contenidos, se tienen que definir
   * las condiciones de igualdad mediante la sobre escritura
   * del metodo equals, de la clase Object, en la clase de los
   * objetos que se desean comparar.
   */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parcel other = (Parcel) obj;
		if (id != other.id)
			return false;
		if (identificationNumber != other.identificationNumber)
			return false;
		return true;
	}

}
