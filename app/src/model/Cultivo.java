package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
// import javax.persistence.UniqueConstraint;
// import javax.persistence.Enumerated;
// import javax.persistence.EnumType;

@Entity
public class Cultivo {

  @Id
  @Column(name="CULTIVO_ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(name="NOMBRE")
  private String nombre;

  // @Column(name="TIPO_CULTIVO")
  // private TipoCultivo tipoCultivo;

  @Column(name="KC_INICIAL")
  private double kcInicial;

  @Column(name="KC_MEDIO")
  private double kcMedio;

  @Column(name="KC_FINAL")
  private double kcFinal;

  @Column(name="E_INICIAL")
  private int etInicial;

  @Column(name="E_DESARROLLO")
  private int etDesarrollo;

  @Column(name="E_MEDIA")
  private int etMedia;

  @Column(name="E_FINAL")
  private int etFinal;

  @Column(name="AGOTAMIENTO_CRITICO")
  private double agotamientoCritico;

  // Constructor method
  public Cultivo() {

  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  // public TipoCultivo getTipoCultivo() {
  //     return this.tipoCultivo;
  // }

  // public void setTipoCultivo(TipoCultivo tipoCultivo) {
  //     this.tipoCultivo = tipoCultivo;
  // }

  public double getKcInicial() {
    return kcInicial;
  }

  public void setKcInicial(double kcInicial) {
    this.kcInicial = kcInicial;
  }

  public double getKcMedio() {
    return kcMedio;
  }

  public void setKcMedio(double kcMedio) {
    this.kcMedio = kcMedio;
  }

  public double getKcFinal() {
    return kcFinal;
  }

  public void setKcFinal(double kcFinal) {
    this.kcFinal = kcFinal;
  }

  public int getEtInicial() {
    return etInicial;
  }

  public void setEtInicial(int etInicial) {
    this.etInicial = etInicial;
  }

  public int getEtDesarrollo() {
    return etDesarrollo;
  }

  public void setEtDesarrollo(int etDesarrollo) {
    this.etDesarrollo = etDesarrollo;
  }

  public int getEtMedia() {
    return etMedia;
  }

  public void setEtMedia(int etMedia) {
    this.etMedia = etMedia;
  }

  public int getEtFinal() {
    return etFinal;
  }

  public void setEtFinal(int etFinal) {
    this.etFinal = etFinal;
  }

  @Override
  public String toString() {
    /*
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas
     */
    return String.format("Cultivo: %s\nID: %d\nKc inicial: %.2f\nKc medio: %.2f\nKc final: %.2f\nDuración de la etapa incial: %d días\nDuración de la etapa media: %d días\nDuración de la etapa final: %d días\n",
    nombre, id, kcInicial, kcMedio, kcFinal, (etInicial + etDesarrollo), etMedia, etFinal);
  }

}
