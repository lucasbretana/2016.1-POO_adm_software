package edu.poo.thing;

import java.util.ArrayList;
import java.util.Arrays;

public class Building{
  private String name = "Casa do caralho!";
  private short nFloors = 5;
  private short nAparAndar = 4;
  private Apartment [][]listOfApartments = null;
  private short numOccupiedApartments = 0;
  private Double totInc = new Double(0);
  private Double totOut = new Double(0);
/**
  * Constructor method, creates a 5x4 matrix, that simulating a building
  * @method Building
  * @return [description]
  */
  public Building(){
   this.listOfApartments = new Apartment[nFloors][nAparAndar];
  }

  public Building(String name){
    this();
    if(name.equalsIgnoreCase(""))
      System.err.println("Using default name!");
    else
      this.name = name;
  }

  /**
   * @method getName
   * @return return the name os the building
   */
  public String getName(){
    return this.name;
  }

  public Short getNumOccupiedApartments(){
    return this.numOccupiedApartments;
  }

  /**
   * Creates and adds to the bulding a new apartment
   * @method addApartment
   * @param floor         is the floor where it should add the new apartment
   * @param num           is the number of the apartment on that floor
   * @param res           is the resident (the first) that lives on that apartment
   */
  private void addApartment(short floor, short num, Resident res){
    this.numOccupiedApartments++;
    this.listOfApartments[floor][num] = new Apartment(res, this);
  }

  /**
   * Add a new resident to it's apartment, if it is the first resident, then also creates the new apartment
   * The information about the number and floor of the apartment is in the resident object
   * @method addResident
   * @param  res         the resident that should be added to the apartment
   * @see addApartment
   */
  public void addResident(Resident res){
    short floor = (short) (res.getApartment() / 100); // -> 104 / 100 = 1,04 => 1
    short num = (short) (res.getApartment() - floor * 100); // -> 104 - 100 = 4
    --floor;
    --num;

    if(this.listOfApartments[floor][num] == null)
      addApartment(floor, num, res);
    else
      this.listOfApartments[floor][num].addResident(res);
  }

  /**
   * Send a request of bill to all the residents on each apartment
   * @method sendBill
   * @param  bill     the bill that has to be charged
   */
  public void sendBill(Bill bill){
    for(int i=0 ; i<nFloors ; i++)
      Arrays.stream(this.listOfApartments[i]).filter(a -> a != null).forEach(a ->{
        BillPerApartment b = new BillPerApartment(bill, a);
        this.totInc += b.getTotal();
        this.totOut += bill.getSum();
        a.getListResidents().forEach(r -> r.chargeBill(b));
      });
  }

  // private void doAccount(Bill b){
  //   Double in = b.getSum() + b.getAdmin();
  // }

}
