package stateless;

import javax.ejb.Stateless;
import javax.ejb.Schedule;
import javax.ejb.EJB;

import javax.persistence.Query;

import java.util.Collection;

import model.ClimateLog;
import model.Parcel;

@Stateless
public  class AutomaticDataExtractor {

  // inject a reference to the ParcelServiceBean
  @EJB ParcelServiceBean parcelService;

  /**
   * Hora de ejecucion: 8 AM
   *
   * @param second="0"
   * @param minute="0"
   * @param hour="8"
   */
  // @Schedule(second="0", minute="0", hour="8")
  // @Schedule(second="*/2", minute="*", hour="*", persistent=false)
  public void execute() {
    Collection<Parcel> parcels = parcelService.findAll();

    for (Parcel currentParcel : parcels) {

    }

  }

}
