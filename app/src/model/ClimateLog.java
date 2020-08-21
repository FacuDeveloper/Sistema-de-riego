/*
 * Esta clase representa un registro que contendra los datos
 * climaticos de los pronosticos recuperados en una
 * fecha dada para unas coordenadas geograficas dadas
 *
 * Nota
 * Las unidades de medida de los fenomenos climaticos
 * dependen de las unidades de medida en la cual son
 * pedidos en la llamada a la API del clima llamada
 * Dark Sky
 *
 * En nuestro caso, en la llamada a la API especificamos
 * que deseamos que los fenomenos climaticos utilicen
 * la unidad SI, la cual establece lo siguiente:
 *
 * precipIntensity: Milimetros por hora.
 * temperatureMin: Grados centigrados.
 * temperatureMax: Grados centigrados.
 * dewPoint: Grados centigrados.
 * windSpeed: Metros por segundo.
 * pressure: Hectopascales.
 *
 * La presion atmosferica la tenemos que convertir
 * de hectopascales a kilopascales porque la formula
 * de la ETo (evapotranspiracion del cultivo de
 * referencia) utiliza la presion atmosferica en
 * kilopascales
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
import javax.persistence.UniqueConstraint;

import java.util.Calendar;

import weatherApiClasses.ForecastResponse;

import util.UtilDate;

@Entity
@Table(name="REGISTRO_CLIMATICO", uniqueConstraints={@UniqueConstraint(columnNames={"DATE", "FK_PARCEL"})})
public class ClimateLog {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="CLIMATE_LOG_ID")
  private int id;

  /*
   * Fecha en la cual los datos climaticos
   * en base a unas coordenadas geograficas
   * han sido solicitados
   */
  @Column(name="DATE", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar date;

  /*
   * Zona horaria
   *
   * El nombre de la zona horaria de la
   * IANA para la ubicacion solicitada
   */
  @Column(name="TIME_ZONE", nullable=false)
  private String timezone;

  /*
   * Intensidad de precipitacion [milimetros]
   *
   * La intensidad (en milimetros de agua liquida por hora)
   * de precipitacion que ocurre en el momento dado
   *
   * Este valor depende de la probabilidad (es decir,
   * suponiendo que ocurre alguna precipitacion)
   *
   * En la cadena de consulta para la llamada a la API
   * del clima esta establecida que la unidad de medida
   * en la cual los fenomenos climaticos tienen que ser
   * devueltos como respuesta a la llamada es la SI, la
   * cual especifica que la intensidad de la precipitacion
   * esta dada en milimetros
   */
  @Column(name="PRECIP_INTENSITY", nullable=false)
  private Double precipIntensity;

  /*
   * Probabilidad de la precipitacion
   *
   * La probabilidad de que ocurra una precipitacion,
   * entre 0 y 1, inclusive
   */
  @Column(name="PRECIP_PROBABILITY", nullable=false)
  private Double precipProbability;

  /*
   * Punto de rocio
   *
   * Este valor es provisto por la API
   * del servicio climatico en la unidad
   * de medida [°C]
   */
  @Column(name="DEW_POINT", nullable=false)
  private Double dewPoint;

  /*
   * Presion atmosferica
   *
   * La presion del aire a nivel del mar
   */
  @Column(name="PRESSURE", nullable=false)
  private Double pressure;

  /*
   * Velocidad del viento
   *
   * Con el uso de la unidad SI, este
   * fenomeno es medido en metros por segundo
   */
  @Column(name="WIND_SPEED", nullable=false)
  private Double windSpeed;

  /*
   * Nubosidad
   *
   * El porcentaje de cielo olcuido por nubes,
   * entre 0 y 1, inclusive
   */
  @Column(name="CLOUD_COVER", nullable=false)
  private Double cloudCover;

  /*
   * Temperatura minima [°C]
   *
   * Con el uso de la unidad SI, este
   * fenomeno es medido en grados
   * centigrados
   */
  @Column(name="TEMP_MIN", nullable=false)
  private Double temperatureMin;

  /*
   * Temperatura maxima [°C]
   *
   * Con el uso de la unidad SI, este
   * fenomeno es medido en grados
   * centigrados
   */
  @Column(name="TEMP_MAX", nullable=false)
  private Double temperatureMax;

  /*
   * Cantidad total de agua de lluvia [milimetros]
   * en el dia de hoy
   *
   * Este valor es calculado multiplicando el
   * valor de precipIntensity por 24 ya que precipIntensity
   * es la intensidad (en milimetros de agua liquida por hora)
   * que ocurre en el momento dado
   */
  @Column(name="RAIN_WATER", nullable=false)
  private double rainWater;

  /*
   * Cantidad de agua acumulada [milimetros] en el dia de
   * hoy, la cual es agua del dia de hoy a favor para el
   * dia de mañana
   *
   * Este valor se calcula haciendo la diferencia entre
   * la ETc del dia de ayer o la ETo del dia de ayer
   * (en caso de que en el dia de ayer no haya habido
   * un cultivo sembrado en la parcela dada, por ende, la
   * ETc es cero), la cantidad de agua de lluvia del dia
   * de ayer, la cantidad de agua acumulada del dia de ayer
   * y la cantidad de agua utilizada en los riegos realizados
   * en el dia de hoy por parte del usuario cliente
   */
  @Column(name="WATER_ACCUMULATED", nullable=false)
  private double waterAccumulated;

  /*
   * Evapotranspiracion del cultivo de referencia (ETo)
   *
   * Este valor es calculado haciendo uso de los fenomenos
   * climaticos en la formula de la ETo y su valor esta
   * medido en [mm/dia]
   *
   * El cultivo de referencia es el pasto
   *
   * Para ver la formula de la ETo dirigase a la pagina
   * numero 25 del libro FAO numero 56
   */
  @Column(name="ETO", nullable=false)
  private double eto;

  /*
   * Evapotranspiracion del cultivo bajo condiciones estandar (ETc)
   *
   * Este es calculado utilizando el coeficiente de un cultivo
   * (kc) en particular en la siguiente multiplicacion ETc = kc * ETo
   *
   * El valor de la ETc [mm/dia] nos indica la cantidad de agua que
   * se le tiene que reponer a un cultivo dado, mediante el riego
   *
   * Para ver la formula de la ETc dirigase a la pagina numero 6
   * del libro FAO numero 56
   */
  @Column(name="ETC", nullable=false)
  private double etc;

  @ManyToOne
  @JoinColumn(name="FK_PARCEL", nullable=false)
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
   * Returns value of rainWater
   * @return
   */
  public double getRainWater() {
    return rainWater;
  }

  /**
   * Sets new value of rainWater
   * @param
   */
  public void setRainWater(double rainWater) {
    this.rainWater = rainWater;
  }

  /**
   * Returns value of waterAccumulated
   * @return
   */
  public double getWaterAccumulated() {
    return waterAccumulated;
  }

  /**
   * Sets new value of waterAccumulated
   * @param
   */
  public void setWaterAccumulated(double waterAccumulated) {
    this.waterAccumulated = waterAccumulated;
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
	public void setEto(Double eto) {
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
  public void setEtc(Double etc) {
    this.etc = etc;
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
   * Temperatura minima (temperatureMin) [°C]
   * Temperatura maxima (temperatureMax) [°C]
   * Punto de rocio (dewPoint) [°C]
   * Velocidad del viento (windSpeed) [metros por segundo]
   * Presion atmosferica (pressure) [hectopascales] convertida a [kPa]
   *
   * @param forecastResponse (pronostico) este parametro contiene
   * todos los datos climaticos devueltos por la llamada a la API
   * del clima Dark Sky
   */
  public void load(ForecastResponse forecastResponse) {
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
     * por el formato UNIX TIMESTAMP (el cual utiliza segundos),
     * en milisegundos en formato long
     *
     * Lo que se logra con esto es convertir la fecha
     * en formato UNIX TIMESTAMP a formato de año, mes
     * y dia, formato que es entendible para el ser humano
     * mientras que el formato UNIX TIMESTAMP no es entendible
     * para el ser humano pero sí lo es para la maquina
     */
    date.setTimeInMillis(forecastResponse.getDaily().getData().get(0).getTime() * 1000L);

    timezone = forecastResponse.getTimezone();

    /*
     * La probabilidad de que ocurra la precipitación, entre 0 y 1, inclusive.
     */
    precipProbability = forecastResponse.getDaily().getData().get(0).getPrecipProbability();

    /*
     * Punto de rocio [°C]
     */
    dewPoint = forecastResponse.getDaily().getData().get(0).getDewPoint();

    /*
     * Presion atmosferica convertida de hectopascales a kilopascales
     * por estar multiplicada por 0.1
     *
     * 1 hectopascales = 0.1 kilopascales
     */
    pressure = forecastResponse.getDaily().getData().get(0).getPressure() * 0.1;

    /*
     * Velocidad del viento [metros por segundo]
     */
    windSpeed = forecastResponse.getDaily().getData().get(0).getWindSpeed();

    /*
     * El porcentaje de cielo olcuido por nubes,
     * entre 0 y 1, inclusive
     */
    cloudCover = forecastResponse.getDaily().getData().get(0).getCloudCover();

    // [°C]
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
    precipIntensity = forecastResponse.getDaily().getData().get(0).getPrecipIntensity();

    /*
     * El tipo de precipitación que ocurre en el momento dado. Si se define, esta
     * propiedad tendrá uno de los siguientes valores: "rain", "snow" o "sleet"
     * (que se refiere a lluvia helada, gránulos de hielo y "mezcla invernal" respectivamente).
     * (Si precipIntensity es cero, entonces esta propiedad no se definirá. Además, debido
     * a la falta de datos en nuestras fuentes, la información histórica de precipType
     * generalmente se estima, en lugar de observarse).
     *
     * Si el tipo de precipitacion esta definido y es lluvia ("rain" en ingles) se calcula la cantidad
     * total de agua de lluvia en el dia dado multiplicando precipIntensity por 24
     * ya que precipIntensity es la intensidad (en milimetros de agua liquida por hora)
     * de precipitacion que ocurre en el momento dado
     *
     * En caso contrario, la cantidad de agua de lluvia del dia de hoy es cero
     */
    if ((forecastResponse.getDaily().getData().get(0).getPrecipType() != null) && (forecastResponse.getDaily().getData().get(0).getPrecipType().equals("rain"))) {
      rainWater = precipIntensity * 24;
    } else {
      rainWater = 0.0;
    }

  }

  @Override
  public String toString() {
    return String.format("ID: %d\nLatitud: %f (grados decimales) Longitud: %f (grados decimales)\nZona horaria: %s\nFecha: %s\nIntensidad de precipitación: %f (milímetros/hora)\nProbabilidad de precipitación: %f (entre 0 y 1)\nPunto de rocío: %f (°C)\nPresión atmosférica: %f (kPa)\nVelocidad del viento: %f (metros/segundo)\nNubosidad: %f (entre 0 y 1)\nTemperatura mínima: %f (°C)\nTemperatura máxima: %f (°C)\nCantidad total de agua de lluvia: %f (milímetros)\nCantidad de agua acumulada: %f\n",
    id, parcel.getLatitude(), parcel.getLongitude(), timezone, UtilDate.formatDate(date), precipIntensity, precipProbability, dewPoint, pressure, windSpeed, cloudCover, temperatureMin, temperatureMax, rainWater, waterAccumulated);
  }

}
