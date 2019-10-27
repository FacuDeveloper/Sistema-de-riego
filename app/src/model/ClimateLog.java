/*
 * Esta representa un registro que contendra los datos
 * climaticos de los pronosticos recuperados en una
 * fecha dada para unas coordenadas geograficas dadas
 */

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

import weatherApiClasses.ForecastResponse;

import java.util.Calendar;

@Entity
@Table(name="CLIMATE_LOG")
public class ClimateLog {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="CLIMATE_LOG_ID")
  private int id;

  @Column(name="LATITUDE")
  private Double latitude;

  @Column(name="LONGITUDE")
  private Double longitude;

  @Column(name="TIME_ZONE")
  private String timezone;

  @Column(name="DATE")
  @Temporal(TemporalType.DATE)
  private Calendar date;

  @Column(name="PRECIP_INTENSITY")
  private Double precipIntensity;

  @Column(name="PRECIP_PROBABILITY")
  private Double precipProbability;

  // @Column(name="PRECIP_ACCUMULATION")
  // private Double precipAccumulation;

  @Column(name="PRECIP_TYPE")
  private String precipType;

  @Column(name="DEW_POINT")
  private Double dewPoint;

  // @Column(name="HUMIDITY")
  // private Double humidity;

  @Column(name="PRESSURE")
  private Double pressure;

  @Column(name="WIND_SPEED")
  private Double windSpeed;

  @Column(name="CLOUD_COVER")
  private Double cloudCover;

  @Column(name="TEMP_MIN")
  private Double temperatureMin;

  @Column(name="TEMP_MAX")
  private Double temperatureMax;

  // Cantidad total de agua de lluvia
  @Column(name="TOTAL_RAIN_WATER")
  private Double totalRainWater;

  @ManyToOne
  @JoinColumn(name="FK_PARCEL")
  private Parcel parcel;

  // Constructor method
  public ClimateLog() {

  }

	/**
	* Returns value of id
	* @return
	*/
	public int getId() {
		return id;
	}

	/**
	* Returns value of latitude
	* @return
	*/
	public Double getLatitude() {
		return latitude;
	}

	/**
	* Sets new value of latitude
	* @param
	*/
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	* Returns value of longitude
	* @return
	*/
	public Double getLongitude() {
		return longitude;
	}

	/**
	* Sets new value of longitude
	* @param
	*/
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	* Returns value of timezone
	* @return
	*/
	public String getTimezone() {
		return timezone;
	}

	/**
	* Sets new value of timezone
	* @param
	*/
	public void setTimezone(String timezone) {
		this.timezone = timezone;
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
	* Returns value of precipIntensity
	* @return
	*/
	public Double getPrecipIntensity() {
		return precipIntensity;
	}

	/**
	* Sets new value of precipIntensity
	* @param
	*/
	public void setPrecipIntensity(Double precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

  /**
	* Returns value of precipProbability
	* @return
	*/
	public Double getPrecipProbability() {
		return precipProbability;
	}

	/**
	* Sets new value of precipProbability
	* @param
	*/
	public void setPrecipProbability(Double precipProbability) {
		this.precipProbability = precipProbability;
	}

  /**
	* Returns value of precipAccumulation
	* @return
	*/
	// public Double getPrecipAccumulation() {
	// 	return precipAccumulation;
	// }

	/**
	* Sets new value of precipAccumulation
	* @param
	*/
	// public void setPrecipAccumulation(Double precipAccumulation) {
	// 	this.precipAccumulation = precipAccumulation;
	// }

	/**
	 * Returns value of precipType
	 * @return
	 */
	public String getPrecipType() {
		return precipType;
	}

	/**
	 * Sets new value of precipType
	 * @param
	 */
	public void setPrecipType(String precipType) {
		this.precipType = precipType;
	}

	/**
	 * Returns value of dewPoint
	 * @return
	 */
	public Double getDewPoint() {
		return dewPoint;
	}

	/**
	 * Sets new value of dewPoint
	 * @param
	 */
	public void setDewPoint(Double dewPoint) {
		this.dewPoint = dewPoint;
	}

	/**
	 * Returns value of humidity
	 * @return
	 */
	// public Double getHumidity() {
	// 	return humidity;
	// }

	/**
	 * Sets new value of humidity
	 * @param
	 */
	// public void setHumidity(Double humidity) {
	// 	this.humidity = humidity;
	// }

	/**
	 * Returns value of pressure
	 * @return
	 */
	public Double getPressure() {
		return pressure;
	}

	/**
	 * Sets new value of pressure
	 * @param
	 */
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	/**
	 * Returns value of windSpeed
	 * @return
	 */
	public Double getWindSpeed() {
		return windSpeed;
	}

	/**
	* Sets new value of windSpeed
	* @param
	*/
	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	* Returns value of cloudCover
	* @return
	*/
	public Double getCloudCover() {
		return cloudCover;
	}

	/**
	* Sets new value of cloudCover
	* @param
	*/
	public void setCloudCover(Double cloudCover) {
		this.cloudCover = cloudCover;
	}

	/**
	* Returns value of temperatureMin
	* @return
	*/
	public Double getTemperatureMin() {
		return temperatureMin;
	}

	/**
	* Sets new value of temperatureMin
	* @param
	*/
	public void setTemperatureMin(Double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

  /**
	* Returns value of temperatureMax
	* @return
	*/
	public Double getTemperatureMax() {
		return temperatureMax;
	}

	/**
	* Sets new value of temperatureMax
	* @param
	*/
	public void setTemperatureMax(Double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

  /**
  * Returns value of totalRainWater
  * @return
  */
  public Double getTotalRainWater() {
    return totalRainWater;
  }

  /**
  * Sets new value of totalRainWater
  * @param
  */
  public void setTotalRainWater(Double totalRainWater) {
    this.totalRainWater = totalRainWater;
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

  /**
   * Carga el registro climatico (el cual contendra los
   * datos provistos por el pronostico climatico
   * recuperado, el cual es enviado como argumento
   * en la invocacion de este metodo) invocante con los datos del
   * pronostico devuelto como respuesta de la llamada
   * a la API del clima Dark Sky
   *
   * En la cadena de consulta para llamar a la API
   * del clima Dark Sky esta establecido que la unidad
   * en la cual tienen que ser devueltos los datos
   * climaticos es la SI (ver la constante QUERY_STRING
   * de la clase ClimateClient)
   *
   * Unidades SI:
   * Intensidad de precipitación (precipIntensity) [milimetros por hora]
   * Precipitacion acumulada de nieve (precipAccumulation) [centimetros]
   * Temperatura minima (temperatureMin) [°C]
   * Temperatura maxima (temperatureMax) [°C]
   * Punto de rocio (dewPoint) [°C]
   * Velocidad del viento (windSpeed) [metros por segundo]
   * Presion atmosferica (pressure) [milibares] convertida a [kPa]
   *
   * Los siguientes datos no tienen nada que ver
   * con las unidades SI pero estan escritos con el
   * formato en el que los devuelve la llamada a la
   * API del clima Dark Sky:
   * Latitud [grados decimales]
   * Longitud [grados decimales]
   * Tiempo (time) [UNIX TIMESTAMP]
   * Humedad (humidity) [entre 0 y 1]
   *
   * Probabilidad de precipitacion (precipProbability) [entre 0 y 1]
   * - Esta es la probabilidad de que la precipitacion ocurra
   *
   * Nubosidad (cloudCover) [entre 0 y 1]
   *
   * @param forecastResponse este parametro (pronostico como
   * respuesta) contiene todos los datos climaticos devueltos
   * por la llamada a la API del clima Dark Sky
   */
  public void load(ForecastResponse forecastResponse) {
    /*
     * Coordenadas geograficas en grados decimales
     * del lugar sobre el cual se quiere saber
     * sus correspondientes datos climaticos
     */
    latitude = forecastResponse.getLatitude();
    longitude = forecastResponse.getLongitude();
    timezone = forecastResponse.getTimezone();

    /*
     * Fecha para la cual se obtuvieron los datos
     * climaticos en base a las coordenadas geograficas
     * del lugar sobre el cual se queire saber sus
     * correspondientes datos climaticos
     */
    date = Calendar.getInstance();

    /*
     * Convierte los segundos a milisegundos en formato
     * long porque este metodo utiliza la fecha dada
     * por el formato UNIX TIMESTAMP en segundos,
     * en milisegundos en formato long
     *
     * Lo que se logra con esto es convertir la fecha
     * en formato UNIX TIMESTAMP a formato de año, mes
     * y dia, formato que es entendible para el ser humano
     * mientras que el formato UNIX TIMESTAMP no es entendible
     * para el ser humano pero sí lo es para la maquina
     */
    date.setTimeInMillis(forecastResponse.getDaily().getData().get(0).getTime() * 1000L);

    if (forecastResponse.getDaily().getData().get(0).getPrecipProbability() != null) {
      precipProbability = forecastResponse.getDaily().getData().get(0).getPrecipProbability();
    } else {
      precipProbability = 0.0;
    }

    precipType = forecastResponse.getDaily().getData().get(0).getPrecipType();
    dewPoint = forecastResponse.getDaily().getData().get(0).getDewPoint();

    /*
     * Presion atmosferica convertida de milibares a kilopascales
     * por estar multiplicada por 0.1
     *
     * 1 milibar = 0.1 kilopascales
     */
    pressure = forecastResponse.getDaily().getData().get(0).getPressure() * 0.1;
    windSpeed = forecastResponse.getDaily().getData().get(0).getWindSpeed();
    cloudCover = forecastResponse.getDaily().getData().get(0).getCloudCover();
    temperatureMin = forecastResponse.getDaily().getData().get(0).getTemperatureMin();
    temperatureMax = forecastResponse.getDaily().getData().get(0).getTemperatureMax();

    /*
     * La intensidad (en milimetros de agua líquida por hora) de precipitación que
     * ocurre en el momento dado. Este valor depende de la probabilidad
     * (es decir, suponiendo que ocurra alguna precipitación). Esto es la explicacion
     * de la llamada al metodo getPrecipIntensity() el cual retorna la intensidad
     * de la precipitacion.
     *
     * De forma predeterminada la API Dark Sky da este valor en pulgadas y no
     * en milimetros pero en la llamada a la API se establece que la unidad
     * de medida para de este fenomeno climatico sea en milimetros y no
     * en pulgadas.
     *
     * Sabiendo lo que dice el primer parrafo se entiende que si se multiplica
     * este valor (intensidad de la precipitacion) por 24 vamos a obtener como
     * resultado la cantidad total de agua de lluvia
     */
    if (forecastResponse.getDaily().getData().get(0).getPrecipIntensity() != null) {
      precipIntensity = forecastResponse.getDaily().getData().get(0).getPrecipIntensity();
      totalRainWater = forecastResponse.getDaily().getData().get(0).getPrecipIntensity() * 24;
    } else {
      precipIntensity = 0.0;
      totalRainWater = 0.0;
    }

  }

  @Override
  public String toString() {
    /*
     * Se le suma un 1 al valor de la constante MONTH
     * porque los numeros de los meses van desde 0
     * (Enero) a 11 (Diciembre)
     */
    return String.format("Latitud: %f (grados decimales) Longitud: %f (grados decimales)\nFecha: %s\nIntensidad de precipitación: %f (milímetros/hora)\nProbabilidad de precipitación: %f (entre 0 y 1)\nPunto de rocío: %f (°C)\nPresión atmosférica: %f (kPa)\nVelocidad del viento: %f (metros/segundo)\nNubosidad: %f (entre 0 y 1)\nTemperatura mínima: %f (°C)\nTemperatura máxima: %f (°C)\nCantidad total de agua de lluvia: %f (milímetros)",
    latitude, longitude, (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)), precipIntensity, precipProbability, dewPoint, pressure, windSpeed, cloudCover, temperatureMin, temperatureMax, totalRainWater);
  }

}
