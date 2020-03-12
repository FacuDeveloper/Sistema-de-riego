package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import java.util.Calendar;

import util.UtilDate;

@Entity
@Table(name="STATISTICAL_REPORT")
public class StatisticalReport {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="STATISTICAL_REPORT_ID")
  private int id;

  /*
   * Fecha en la cual se creo el informe
   * estadistico
   */
  @Column(name="DATE", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar date;

  // Cultivo que mas se planto
  @Column(name="CULTIVO_MAS_PLANTADO", nullable=false)
  private String cropMostPlanted;

  // Cultivo que menos se planto
  @Column(name="CULTIVO_MENOS_PLANTADO", nullable=false)
  private String cropLeastPlanted;

  // Cultivo que mas tiempo estuvo en la parcela
  @Column(name="CULTIVO_MAS_TIEMPO_PARCELA", nullable=false)
  private String cropLongest;

  // Cultivo que menos tiempo estuvo en la parcela
  @Column(name="CULTIVO_MENOS_TIEMPO_PARCELA", nullable=false)
  private String cropLess;

  // Cantidad de tiempo en el cual la parcela no tuvo nada
  @Column(name="TIEMPO_SIN_TENER_CULTIVO", nullable=false)
  private int timeWithoutCrop;

  @ManyToOne
  @JoinColumn(name="FK_PARCEL", nullable=false)
  private Parcel parcel;

  // Constructor method
  public StatisticalReport() {

  }

	/**
	 * Returns value of id
	 * @return
	 */
	public int getId() {
		return id;
	}

  /**
   * Returns value of date
   * @return
   */
  public Calendar getDate() {
    return date;
  }

  /**
   * Sets new value of date
   * @param
   */
  public void setDate(Calendar date) {
    this.date = date;
  }

  /**
   * Returns value of cropMostPlanted
   * @return
   */
  public String getCropMostPlanted() {
    return cropMostPlanted;
  }

  /**
   * Sets new value of cropMostPlanted
   * @param
   */
  public void setCropMostPlanted(String cropMostPlanted) {
    this.cropMostPlanted = cropMostPlanted;
  }

	/**
	 * Returns value of cropLeastPlanted
	 * @return
	 */
	public String getLeastPlanted() {
		return cropLeastPlanted;
	}

	/**
	 * Sets new value of cropLeastPlanted
	 * @param
	 */
	public void setLeastPlanted(String cropLeastPlanted) {
		this.cropLeastPlanted = cropLeastPlanted;
	}

  /**
	 * Returns value of cropLongest
	 * @return
	 */
	public String getCropLongest() {
		return cropLongest;
	}

	/**
	 * Sets new value of cropLongest
	 * @param
	 */
	public void setCropLongest(String cropLongest) {
		this.cropLongest = cropLongest;
	}

	/**
	 * Returns value of cropLess
	 * @return
	 */
	public String getCropLess() {
		return cropLess;
	}

	/**
	 * Sets new value of cropLess
	 * @param
	 */
	public void setCropLess(String cropLess) {
		this.cropLess = cropLess;
	}

	/**
	 * Returns value of timeWithoutCrop
	 * @return
	 */
	public int getTimeWithoutCrop() {
		return timeWithoutCrop;
	}

	/**
	 * Sets new value of timeWithoutCrop
	 * @param
	 */
	public void setTimeWithoutCrop(int timeWithoutCrop) {
		this.timeWithoutCrop = timeWithoutCrop;
	}

	/**
	 * Returns value of parcel
	 * @return
	 */
	public Parcel getParcel() {
		return parcel;
	}

	/**
	 * Sets new value of parcel
	 * @param
	 */
	public void setParcel(Parcel parcel) {
		this.parcel = parcel;
	}

  @Override
  public String toString() {
    return String.format("ID: %d\nCultivo más plantado: %s\nCultivo menos plantado: %s\nCultivo que más tiempo estuvo en la parcela: %s\nCultivo que menos tiempo estuvo en la parcela: %s\nTiempo en el que la parcela estuvo sin cultivo: %d\nParcela: %s, ID = %d\n",
    id, cropMostPlanted, cropLeastPlanted, cropLongest, cropLess, timeWithoutCrop, parcel.getName(), parcel.getId());
  }

}
