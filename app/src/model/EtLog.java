/*
 * Esta clase es necesaria porque contiene la cantidad
 * de agua de riego que se tiene que utilizar para regar
 * hoy el cultivo dado en funcion de lo que sucedio el
 * dia de ayer
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
@Table(name="ET_LOG")
public class EtLog {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ET_LOG_ID")
  private int id;

  @Column(name="DATE", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar date;

  /*
   * Evapotranspiracion del cultivo de referencia (ETo)
   *
   * La ETo esta medida en milimetros por dia
   */
  @Column(name="ETo", nullable=false)
  private double eto;

  /*
   * Evapotranspiracion del cultivo bajo condiciones
   * estandar (ETc)
   *
   * ETc = ETo * Kc
   *
   * La ETc esta medida en milimetros por dia
   */
  @Column(name="ETc", nullable=false)
  private double etc;

  @Column(name="WATER_REMAINING", nullable=false)
  private double waterRemaining; // agua restante en el suelo

  @Column(name="ID_CROP", nullable=false)
  private int idCrop;

  @Column(name="CROP_NAME", nullable=false)
  private String cropName;

  @Column(name="CROP_STAGE", nullable=false)
  private String cropStage;

  @Column(name="CROP_COEFFICIENT", nullable=false)
  private double cropCoefficient; // kc

  // Constructor method
  public EtLog() {
    
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
	* Returns value of date
	* @return date
	*/
	public Calendar getDate() {
		return date;
	}

	/**
	* Sets new value of date
	* @param date
	*/
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * Returns value of eto
	 * @return
	 */
	public double getEto() {
		return eto;
	}

	/**
	 * Sets new value of eto
	 * @param
	 */
	public void setEto(double eto) {
		this.eto = eto;
	}

  /**
   * Returns value of etc
   * @return
   */
  public double getEtc() {
    return etc;
  }

  /**
   * Sets new value of etc
   * @param
   */
  public void setEtc(double etc) {
    this.etc = etc;
  }

  /**
   * Returns value of waterRemaining
   * @return
   */
  public double getWaterRemaining() {
    return waterRemaining;
  }

  /**
   * Sets new value of waterRemaining
   * @param
   */
  public void setWaterRemaining(double waterRemaining) {
    this.waterRemaining = waterRemaining;
  }

  /**
   * Returns value of idCrop
   * @return
   */
  public int getIdCrop() {
    return idCrop;
  }

  /**
   * Sets new value of idCrop
   * @param
   */
  public void setIdCrop(int idCrop) {
    this.idCrop = idCrop;
  }

  /**
   * Returns value of cropName
   * @return
   */
  public String getCropName() {
    return cropName;
  }

  /**
   * Sets new value of cropName
   * @param
   */
  public void setCropName(String cropName) {
    this.cropName = cropName;
  }

  /**
   * Returns value of cropStage
   * @return
   */
  public String getCropStage() {
    return cropStage;
  }

  /**
   * Sets new value of cropStage
   * @param
   */
  public void setCropStage(String cropStage) {
    this.cropStage = cropStage;
  }

  /**
   * Returns value of cropCoefficient
   * @return
   */
  public double getCropCoefficient() {
    return cropCoefficient;
  }

  /**
   * Sets new value of cropCoefficient
   * @param
   */
  public void setCropCoefficient(double cropCoefficient) {
    this.cropCoefficient = cropCoefficient;
  }

  @Override
  public String toString() {
    /*
     * Se le suma un 1 al valor de la constante MONTH
     * porque los numeros de los meses van desde 0
     * (Enero) a 11 (Diciembre)
     */
    return String.format("ID de registro de ET: %d\nFecha: %s\nETo: %f (mm/día)\nETc: %f (mm/día)\nCoeficiente del cultivo (Kc): %f\nID del cultivo: %d\nNombre del cultivo: %s\nEtapa del cultivo: %s\nAgua restante: %f",
    id, (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)), eto, etc, cropCoefficient, idCrop, cropName, cropStage, waterRemaining);
  }

}
