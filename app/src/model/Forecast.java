package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import weatherApiClasses.ForecastResponse;

@Entity
public class Forecast {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="FORECAST_ID")
  private int id;

  @Column(name="LATITUDE")
  private Double latitude;

  @Column(name="LONGITUDE")
  private Double longitude;

  @Column(name="TIME_ZONE")
  private String timezone;

  @Column(name="TIME")
  private Integer time;

  // @Column(name="SUMMARY")
  // private String summary;

  // @Column(name="ICON")
  // private String icon;

  @Column(name="PRECIP_INTENSITY")
  private Double precipIntensity;

  @Column(name="PRECIP_PROBABILITY")
  private Double precipProbability;

  @Column(name="PRECIP_ACCUMULATION")
  private Double precipAccumulation;

  @Column(name="PRECIP_TYPE")
  private String precipType;

  @Column(name="DEW_POINT")
  private Double dewPoint;

  @Column(name="HUMIDITY")
  private Double humidity;

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
	* Returns value of time
	* @return
	*/
	public Integer getTime() {
		return time;
	}

	/**
	* Sets new value of time
	* @param
	*/
	public void setTime(Integer time) {
		this.time = time;
	}

	/**
	* Returns value of summary
	* @return
	*/
	// public String getSummary() {
	// 	return summary;
	// }

	/**
	* Sets new value of summary
	* @param
	*/
	// public void setSummary(String summary) {
	// 	this.summary = summary;
	// }

	/**
	* Returns value of icon
	* @return
	*/
	// public String getIcon() {
	// 	return icon;
	// }

	/**
	* Sets new value of icon
	* @param
	*/
	// public void setIcon(String icon) {
	// 	this.icon = icon;
	// }

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
	public Double getPrecipAccumulation() {
		return precipAccumulation;
	}

	/**
	* Sets new value of precipAccumulation
	* @param
	*/
	public void setPrecipAccumulation(Double precipAccumulation) {
		this.precipAccumulation = precipAccumulation;
	}

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
	public Double getHumidity() {
		return humidity;
	}

	/**
	* Sets new value of humidity
	* @param
	*/
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

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
   * Carga el pronostico invocante
   * @param forecastResponse
   */
  public void load(ForecastResponse forecastResponse) {
    latitude = forecastResponse.getLatitude();
    longitude = forecastResponse.getLongitude();
    timezone = forecastResponse.getTimezone();
    time = forecastResponse.getDaily().getData().get(0).getTime();
    // summary = forecastResponse.getDaily().getData().get(0).getSummary();
    // icon = forecastResponse.getDaily().getData().get(0).getIcon();
    precipIntensity = forecastResponse.getDaily().getData().get(0).getPrecipIntensity();
    precipProbability = forecastResponse.getDaily().getData().get(0).getPrecipProbability();
    precipAccumulation = forecastResponse.getDaily().getData().get(0).getPrecipAccumulation();
    precipType = forecastResponse.getDaily().getData().get(0).getPrecipType();
    dewPoint = forecastResponse.getDaily().getData().get(0).getDewPoint();
    humidity = forecastResponse.getDaily().getData().get(0).getHumidity();
    pressure = forecastResponse.getDaily().getData().get(0).getPressure();
    windSpeed = forecastResponse.getDaily().getData().get(0).getWindSpeed();
    cloudCover = forecastResponse.getDaily().getData().get(0).getCloudCover();
    temperatureMin = forecastResponse.getDaily().getData().get(0).getTemperatureMin();
    temperatureMax = forecastResponse.getDaily().getData().get(0).getTemperatureMax();
  }

}
