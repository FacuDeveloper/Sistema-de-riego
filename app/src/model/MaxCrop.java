package model;

public class MaxCrop {

  private String name;
  private int max;

  // Constructor method
  public MaxCrop(String name, int max) {
    this.name = name;
    this.max = max;
  }

  public String getName() {
    return name;
  }

  public int getMax() {
    return max;
  }

  @Override
  public String toString() {
    return String.format("Nombre del cultivo que más plantado fue: %s\nCantidad de veces que fue plantado: %d\n", name, max);
  }

}
