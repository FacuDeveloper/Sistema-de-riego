package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Calendar;

@Entity
public class Parcel {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="PARCEL_ID")
  private int id;

  // TODO: Establecer clave compuesta con este atributo y el identificador del usuario
  @Column(name="PARCEL_NAME", nullable=false)
  private String name;

  @Column(name="AREA", nullable=false)
  private double hectare;

  @Column(name="LATITUDE", nullable=false)
  private double latitude; // en grados decimales

  @Column(name="LONGITUDE", nullable=false)
  private double longitude; // en grados decimales

  @Column(name="ACTIVE", nullable=false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="FK_USER")
  private Usuario user;

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
	* Returns value of name
	* @return name
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param name
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns value of hectare
	 * @return
	 */
	public double getHectare() {
		return hectare;
	}

	/**
	 * Sets new value of hectare
	 * @param
	 */
	public void setHectare(double hectare) {
		this.hectare = hectare;
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
   * Returns value of active
   * @return
   */
  public boolean getActive() {
    return active;
  }

  /**
   * Sets new value of active
   * @param
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Returns value of user
   * @return
   */
  public Usuario getUser() {
    return user;
  }

  /**
   * Sets new value of user
   * @param
   */
  public void setUser(Usuario user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format("ID: %d\nNombre: %s\nHectárea: %f\nLatitud: %f\nLongitud: %f\nActiva: %b\nUsuario ID: %d\n",
    id, hectare, latitude, longitude, active, user.getId());
  }

	// public int hashCode() {
	// 	final int prime = 31;
	// 	int result = 1;
	// 	result = prime * result + id;
	// 	result = prime * result + identificationNumber;
	// 	return result;
	// }

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
	// @Override
	// public boolean equals(Object obj) {
	// 	if (this == obj)
	// 		return true;
	// 	if (obj == null)
	// 		return false;
	// 	if (getClass() != obj.getClass())
	// 		return false;
	// 	Parcel other = (Parcel) obj;
	// 	if (id != other.id)
	// 		return false;
	// 	if (identificationNumber != other.identificationNumber)
	// 		return false;
	// 	return true;
	// }

}
