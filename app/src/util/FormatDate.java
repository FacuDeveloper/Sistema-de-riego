/*
 * Esta clase tiene la finalidad de brindar un metodo
 * que devuelva una cadena de caracteres con el formato
 * DD-MM-YYYY de una fecha dada
 */

package util;

import java.util.Calendar;

public class FormatDate {

  // Constructor method
  private FormatDate() {

  }

  /**
   * @param  givenDate
   * @return cadena de caracteres que tiene el
   * formato DD-MM-YYYY de la fecha dada
   */
  public static String formatDate(Calendar givenDate) {
    return (givenDate.get(Calendar.DAY_OF_MONTH) + "-" + (givenDate.get(Calendar.MONTH) + 1) + "-" + givenDate.get(Calendar.YEAR));
  }

}
