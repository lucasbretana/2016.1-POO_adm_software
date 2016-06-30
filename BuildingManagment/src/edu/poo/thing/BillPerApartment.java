package edu.poo.thing;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public class BillPerApartment{
  private Double garage = new Double(0);
  private Bill base = null;
  private Apartment place = null;

  public BillPerApartment(Bill b, Apartment a){
    this.base = b;
    this.place = a;
  }

  public Double getGarage(){
    return (this.place.getListResidents().stream().filter((r)->r.hasGarage()).toArray().length > 0) ? new Double(this.getAdmin() * 0.4) : new Double(0);
  }

  /**
   * Gets the month from memory
   * @method getMonth
   * @return the month if super bill
   */
  public String getMonth(){
    return base.getMonth();
  }

  /**
   * Returns the water value
   * @method getWater
   * @return [description]
   */
  public Float getWater(){
    return base.getWater() / this.place.getBuilding().getNumOccupiedApartments();
  }

  /**
   * @method getEnergy
   * @return energy bill value
   */
  public Float getEnergy(){
    return base.getEnergy() / this.place.getBuilding().getNumOccupiedApartments();
  }

  /**
  * @return value of gas bill
  */
  public Float getGas() {
    return base.getGas() / this.place.getBuilding().getNumOccupiedApartments();
  }

  /**
  * @return value of lobby bill
  */
  public Float getLobby() {
    return base.getLobby() / this.place.getBuilding().getNumOccupiedApartments();
  }

  /**
  * @return value of admin bill
  */
  public Float getAdmin() {
    return new Double(this.getSum() * 0.1).floatValue();
  }

  /**
   * Sums up all the bills, for this apartment
   * @return a sum of the bills, without the admin neither the garage taxes
   * @see getTotal
   */
  private Double getSum(){
    return new Double(this.base.getSum() / this.place.getBuilding().getNumOccupiedApartments());
  }

  /**
   * Sums up all the taxes. including admin and garage, is applicable, taxes
   * @return the total of taxes for this apartment
   */
  public Double getTotal(){
    return getSum() + getAdmin() + getGarage();
  }

  public Double getFullTotal(){
    return this.base.getSum();
  }
}
