package edu.poo.thing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.List;
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

  public Short getTotalApartments(){
    return new Integer(this.nFloors * this.nAparAndar).shortValue();
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
        a.getListResidents().forEach(r -> r.chargeBill(b));
        this.totInc += b.getTotal();
      });
    this.totOut += bill.getSum();
    this.doAccount(bill);
  }

  public String listResidents(short floor){
    String list = "";
    for(Apartment ap : this.listOfApartments[floor])
      if(ap != null)
      // for(Resident r : ap.getListResidents())
      //   if(r != null)
          String.join(ap.getListResidents().toArray(), ",");
          // list += r.getName() + ", ";

    // Arrays.stream(this.listOfApartments[floor]).filter(a -> a != null).forEach(a -> a.getListResidents().stream().forEach(r -> System.out.print(r.getName() + ", ")));
    // List<String> list = Arrays.stream(this.listOfApartments[floor]).filter(a -> a != null).forEach(a -> a.getListResidents().map(r -> r.getName()));

    // System.out.print(" here on " + String.joind(Arrays.stream(this.listOfApartments[floor]).filter(a -> a != null).forEach(a -> a.getListResidents()), ",") + "");
    return null;
  }

  private void doAccount(Bill b){
    // System.out.println("R$ " + String.format("%.2f", this.totInc - this.totOut) + "");
    try(BufferedWriter accountFile = new BufferedWriter(new FileWriter(new File(b.getMonth())))){
      accountFile.write(b.getMonth().toUpperCase() + "\n");
      accountFile.write("Inc:\t ++ R$\t" + String.format("%.2f", this.totInc) + "\n");
      accountFile.write("Out:\t -- R$\t" + String.format("%.2f", this.totOut) + "\n");
      accountFile.write("Pro:\t == R$\t" + String.format("%.2f", this.totInc - this.totOut) + "\n");
      this.totInc = this.totOut = new Double(0);
    }catch(IOException ioEx){
      System.out.println("Could not create the file " + b.getMonth());
      ioEx.printStackTrace();
    }finally{
      System.gc();
    }
  }

}
