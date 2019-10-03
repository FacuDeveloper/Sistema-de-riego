package servlet;

import java.util.Collection;

/*
 * Esta clase esta hecha para probar con un servicio
 * web la funcion de obtenciond de datos climaticos
 */

public class CoordTime {

  private double latitude;
  private double longitude;
  private long time;

  // Constructor method
  public CoordTime() {
  }

  /*
   * Getters and setters
   */
  public double getLatitude() {
    return latitude;
  }

  public double setLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

}
