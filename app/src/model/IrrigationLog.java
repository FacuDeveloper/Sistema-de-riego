/*
 * Esta clase representa el registro histórico de riego
 * y es necesaria para la tarea de obtener la cantidad
 * de agua de riego
 */

package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Calendar;

@Entity
@Table(name="IRRIGATION_LOG")
public class IrrigationLog {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="IRRIGATION_LOG_ID")
  private int id;

  @Column(name="DATE", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar wateringDate; // fecha de riego

  /*
   * Riego sugerido
   *
   * Esta variable contiene el resultado de aplicar la
   * formula de la evapotranspiracion del cultivo bajo
   * condiciones estandar (ETc), la cual da la cantidad
   * de agua que se va a evaporar (de un cultivo) en
   * un cultivo se, con lo cual esta variable tiene
   * que recibir su valor en milimetros
   *
   * La cantidad de agua que va a evaporar un cultivo
   * es la cantidad de agua que tenemos que reponer
   * mediante el riego
   *
   * ETc = ETo (evapotranspiracion del cultivo de
   * referencia) * Kc (coeficiente del cultivo)
   *
   * La ETc se determina en funcion de datos climaticos (para la ETo)
   * correspondientes a la ubicacion en donde se encuentra
   * el cultivo sobre el cual se quiere saber la cantidad
   * de agua de riego y en funcion del coeficiente de dicho
   * cultivo (Kc)
   */
  @Column(name="SUGGESTED_IRRIGATION", nullable=false)
  private double suggestedIrrigation;

  /*
   * Riego realizado
   *
   * Este valor sera establecido por el usuario
   * y tiene que estar en milimetros porque el
   * riego para los cultivos se mide en milimetros
   * y esto lo podemos ver en la formula FAO
   * Penman-Monteith, la cual utiliza la unidad
   * de medida milimetros para indicar la cantidad
   * de agua  que se va a evaporar
   */
  @Column(name="IRRIGATION_DONE", nullable=false)
  private double irrigationDone;

  // Constructor method
  public IrrigationLog() {
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
	* Returns value of wateringDate
	* @return wateringDate
	*/
	public Calendar getWateringDate() {
		return wateringDate;
	}

	/**
	* Sets new value of wateringDate
	* @param wateringDate
	*/
	public void setWateringDate(Calendar wateringDate) {
		this.wateringDate = wateringDate;
	}

	/**
	 * Returns value of suggestedIrrigation
	 * @return
	 */
	public double getSuggestedIrrigation() {
		return suggestedIrrigation;
	}

	/**
	 * Sets new value of suggestedIrrigation
	 * @param
	 */
	public void setSuggestedIrrigation(double suggestedIrrigation) {
		this.suggestedIrrigation = suggestedIrrigation;
	}

  /**
   * Returns value of irrigationDone
   * @return
   */
  public double getIrrigationDone() {
    return irrigationDone;
  }

  /**
   * Sets new value of irrigationDone
   * @param
   */
  public void setIrrigationDone(double irrigationDone) {
    this.irrigationDone = irrigationDone;
  }

  @Override
  public String toString() {
    return "IrrigationLog id: " + id + " fecha de riego: " + wateringDate + " riego sugerido (ETc): " + suggestedIrrigation + " riego realizado: " + irrigationDone;
  }

}
