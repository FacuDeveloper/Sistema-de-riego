package util;

import java.util.Calendar;

public class UtilDate {

  // Constructor method
  private UtilDate() {

  }

  /**
   * @param  givenDate
   * @return cadena de caracteres que tiene el
   * formato DD-MM-YYYY de la fecha dada
   */
  public static String formatDate(Calendar givenDate) {
    return (givenDate.get(Calendar.DAY_OF_MONTH) + "-" + (givenDate.get(Calendar.MONTH) + 1) + "-" + givenDate.get(Calendar.YEAR));
  }

  /**
   * @return la fecha anterior a la fecha actual del sistema
   */
  public static Calendar getYesterdayDate() {
    Calendar currentDate = Calendar.getInstance();
    Calendar yesterdayDate = Calendar.getInstance();

    /*
     * Si la fecha actual es el primero de Enero, entonces
     * la fecha anterior a la fecha actual es el 31 de Diciembre
     * del año anterior al año de la fecha actual
     *
     * Si la fecha actual no es el primero de Enero, entonces
     * la fecha anterior a la fecha actual es el dia anterior
     * a la fecha actual y ambas fechas pertenecen al mismo
     * año
     */
    if (currentDate.get(Calendar.DAY_OF_YEAR) == 1) {
      yesterdayDate.set(Calendar.DAY_OF_YEAR, 365);
      yesterdayDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR) - 1);
    } else {
      yesterdayDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) - 1);
    }

    return yesterdayDate;
  }

  /**
   * @return la fecha siguiente a la fecha actual del sistema
   */
  public static Calendar getTomorrowDate() {
    Calendar currentDate = Calendar.getInstance();
    Calendar tomorrowDate = Calendar.getInstance();

    /*
     * Si la fecha actual es el ultimo dia de Diciembre,
     * es decir, 31 de Diciembre, entonces la fecha siguiente
     * a la fecha actual es el 1 de Enero del año siguiente
     * a la fecha actual
     *
     * Si la fecha actual no es el ultimo dia de Diciembre,
     * es decir, 31 de Diciembre, entonces la fecha siguiente
     * a la fecha actual es el dia de la fecha actual mas un
     * dia
     */
    if ((currentDate.get(Calendar.DAY_OF_YEAR)) == 365) {
      tomorrowDate.set(Calendar.DAY_OF_YEAR, 1);
      tomorrowDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR) + 1);
    } else {
      tomorrowDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) + 1);
    }

    return tomorrowDate;
  }

}
