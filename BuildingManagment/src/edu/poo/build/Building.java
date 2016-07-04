package edu.poo.build;

import edu.poo.person.Resident;
import edu.poo.economics.BillPerApartment;
import edu.poo.economics.Bill;
import edu.poo.economics.IOBill;
import edu.poo.util.IOAble;
import edu.poo.util.MyString;
import edu.poo.util.RemoveException;
import edu.poo.util.Tuple;
import edu.poo.util.IOAble;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class Building implements IOAble{
  private String name = "Casa do caralho!";
  private short nFloors = 5;
  private short nAparAndar = 4;
  private Apartment [][]listOfApartments = null;
  private short numOccupiedApartments = 0;
  private Double totInc = new Double(0);
  private Double totOut = new Double(0);
  private File monthlyBillFile = null;

  /**
   * Constructor method, creates a nFloorsxnAparAndar matrix, that simulating a building, using the default name
   * @method Building
   * @param f         the to read and write the report about the months
   * @return          an instance of building
   */
  @Deprecated
  public Building(File f){
    this.listOfApartments = new Apartment[nFloors][nAparAndar];
    if(f == null)
      throw new NullPointerException("The building has to have a file to read and save the monthly accounting!");
    this.monthlyBillFile = f;
  }

  /**
   * Constructor method, creates a nFloorsxnAparAndar matrix, that simulating a building, using the giving name
   * @method Building
   * @param name      the name of the buidling
   * @param f         the to read and write the report about the months
   * @return          an instance of building
   */
  public Building(String name, File f) throws NullPointerException{
    this(f);
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
   * Remove all resident from the apartment and them remove the apartment is self.
   * @method removeApartment
   * @param  floor          the floor of the apartment to be removed
   * @param num             the number, on the floor, of the apartment to be removed
   * @throws NullPointerException if there is no apartment instanced with those numbers (floor and num)
   * @throws IllegalArgumentException if the floor or the num are not valid
   */
  public void removeApartment(short floor, short num) throws NullPointerException, IllegalArgumentException{
    if((floor > this.nFloors) || (floor < 1)) throw new IllegalArgumentException("Floor must be between 1 and " + this.nFloors + "!");

    if((num > this.nAparAndar) || (num < 1)) throw new IllegalArgumentException("Number of the apartment must be between 1 and " + this.nAparAndar + "!");

    --floor;
    --num;

    if(this.listOfApartments[floor][num] == null) throw new NullPointerException("The apartment " + ((floor * 100) + num) + " is empty! This is inconsistent with the system!");

    this.listOfApartments[floor][num].getListResidents().stream().forEach(r -> {
      try{
        this.removeResident(r);
      }catch(RemoveException rmException){
        rmException.printStackTrace();
        System.gc();
        System.exit(1);
      }});
  }

  /**
   * Remove a resident from it's apartment. If there is no one else on the apartment after the removal, then remove also the apartment.
   * @method removeResident
   * @param  res            the resident to be removed
   * @throws IllegalArgumentException if the floor or the num are not valid
   */
  public void removeResident(Resident res) throws RemoveException{
    if(res == null) throw new NullPointerException("The resident cannot be null!");

    Short floor = (short) (res.getApartment() / 100);
    Short num = (short) (res.getApartment() - floor * 100);

    if((floor > this.nFloors) || (floor < 1)) throw new IllegalArgumentException("Floor must be between 1 and " + this.nFloors + "!");

    if((num > this.nAparAndar) || (num < 1)) throw new IllegalArgumentException("Number of the apartment must be between 1 and " + this.nAparAndar + "!");

    --floor;
    --num;

    if(!this.listOfApartments[floor][num].getListResidents().removeIf(r -> ( (r.getName().equals(res.getName())) && (r.hasGarage() == res.hasGarage()) ) ))
      throw new RemoveException("Could not remove the resident " + res.getName() + " from the apartment " + res.getApartment());

    if(this.listOfApartments[floor][num].getListResidents().size() == 0){
      this.listOfApartments[floor][num].setListResidents(null);
      this.listOfApartments[floor][num] = null;
    }

    System.gc();
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
   * Send a request of bill to all the residents on each apartment.
   * Calls the doAccounting passing bill.
   * Calls the doReportOfMonths passing bill.
   * @method sendBill
   * @param  bill     the bill that has to be charged
   */
  public void sendBill(Bill bill){
    for(short i=0 ; i<nFloors ; i++)
      Arrays.stream(this.listOfApartments[i]).filter(a -> a != null).forEach(a ->{
        BillPerApartment b = new BillPerApartment(bill, a);
        a.getListResidents().forEach(r -> r.chargeBill(b));
        this.totInc += b.getTotal();
      });
    this.totOut = bill.getSum();
    this.doAccounting(bill);
    this.doReportOfMonths(bill);
    this.totInc = new Double(0);
    this.totOut = new Double(0);
  }

  /**
   * Show all the residents thar live on a specific apartment
   * @method listResidentsByFloor
   * @param  floor         the floor to show the residents
   */
  public String listResidentsByFloor(short floor) throws IllegalArgumentException{
    if((floor < 1) || (floor > this.nFloors))
      throw new IllegalArgumentException("The floor cannot be smaller than 1 or bigger than " + this.nFloors);

    --floor;

    MyString s = new MyString(new String(""));
    Arrays.stream(this.listOfApartments[floor]).filter(ap -> ap != null).forEach(ap -> ap.getListResidents().stream().forEach(r -> s.concat(r.getName() + ", ")));

    if(s.getStr().length() > 0){
      s.setStr(s.getStr().trim().substring(0, s.getStr().trim().length() - 1));
    }else{
      System.out.println("There are no residents on the floor " + floor + "!");
    }

    return s.getStr();
  }

  /**
   * Show all the residents thar live on a specific apartment number.
   * @method listResidentsByApartNumber
   * @param  ap            the apartment number to list the residents
   */
  public String listResidentsByApartNumber(short ap) throws NullPointerException{
    Short floor = (short) (ap / 100);
    Short num = (short) (ap - floor * 100);
    if((floor > this.nFloors) || (floor < 1)) throw new IllegalArgumentException("Floor must be between 1 and " + this.nFloors + "!");

    if((num > this.nAparAndar) || (num < 1)) throw new IllegalArgumentException("Number of the apartment must be between 1 and " + this.nAparAndar + "!");

    --floor;
    --num;

    MyString s = new MyString(new String(""));

    this.listOfApartments[floor][num].getListResidents().stream().forEach(r -> s.concat(r.getName() +  ", "));

    if(s.getStr().length() > 0){
      s.setStr(s.getStr().trim().substring(0, s.getStr().trim().length() - 1));
    }else{
      System.out.println("There are no residents on the apartment number " + ap + "!");
    }
    return s.getStr();
  }

  /**
   * Show all the residents that has ou not a garage, dependes on the paramter
   * @method listResidents
   * @param  hasGar        if it is true, then show the residents that have a garage else show the ones that doesn't have
   */
  public String listResidents(boolean hasGar){
    MyString s = new MyString(new String(""));
    for(int floor=0 ; floor<this.nFloors ; floor++)
      Arrays.stream(this.listOfApartments[floor]).filter(ap -> ap != null).forEach(ap -> ap.getListResidents().stream().filter(res -> res.hasGarage() == hasGar).forEach(r -> s.concat(r.getName() + ", ")));
    if(s.getStr().length() > 0){
      s.setStr(s.getStr().trim().substring(0, s.getStr().trim().length() - 1));
    }else{
      System.out.println("There are no residents " + ((hasGar) ? "with" : "without") + " garage!");
    }
    return s.getStr();
  }

  /**
   * Shows the apartment contability for an specific bill/month.
   * This will creates a file it the month of the bill and a small report
   * @method doAccount
   * @param  b         the bill to calculate incoming, outcoming and also the profit
   */
  private void doAccounting(Bill b){
    // System.out.println("R$ " + String.format("%.2f", this.totInc - this.totOut) + "");
    try(BufferedWriter accountFile = new BufferedWriter(new FileWriter(new File(IOAble.LOCATION + b.getMonth() + IOAble.EXTENSION )))){
      accountFile.write(b.getMonth().toUpperCase() + "\n");
      accountFile.write("Inc: R$ " +  String.format("%.2f", this.totInc) + "\n");
      accountFile.write("Out: R$ " +  String.format("%.2f", this.totOut) + "\n");
      accountFile.write("Pro: R$ " + String.format("%.2f", this.totInc - this.totOut) + "\n");
    }catch(IOException ioEx){
      System.out.println("Could not create the file " + b.getMonth());
      ioEx.printStackTrace();
    }finally{
      System.gc();
    }
  }

  /**
   * Creates a report about the monthsbills with they respectives amount of collection.
   * The lines will be order with the amount of collection in that month.
   * @method doReportOfMonths
   * @param  b                the bill added
   */
  private void doReportOfMonths(Bill b){
    ArrayList<Tuple<String,Float>> months = new ArrayList<Tuple<String,Float>>();

    String line;
    String num;
    String month;
    Float number;
    if(this.monthlyBillFile.exists()){
      try(BufferedReader accountFile = new BufferedReader(new FileReader(this.monthlyBillFile))){
        while((line = accountFile.readLine()) != null){
          num = line.trim().split(":")[1];
          month = line.trim().split(":")[0];
          number = Float.parseFloat(num.trim().replaceFirst("R\\$", ""));
          months.add(new Tuple<String,Float>(month, number));
        }
      }catch(IOException ioEx){
        System.out.println("Could not create the file " + this.monthlyBillFile.getName());
        ioEx.printStackTrace();
        line = null;
        num = null;
        month = null;
        num = null;
        System.gc();
        System.exit(1);
      }finally{
        line = null;
        num = null;
        month = null;
        num = null;
        System.gc();
      }
    }

    months.add(new Tuple<String,Float>(b.getMonth().toUpperCase(), b.getSum().floatValue()));

    java.util.Collections.sort(months);

    try(BufferedWriter accountFile = new BufferedWriter(new FileWriter(this.monthlyBillFile))){
      // Works both ways
      // Old for each, still pretty cool
      // for(Tuple<String,Float> t : months){
      //       accountFile.write(t.getT().toString().toUpperCase() + " : R$ " + String.format("%.2f", t.getE()) + "\n");
      //       System.err.println(t.getT().toString().toUpperCase() + " : R$ " + String.format("%.2f", t.getE()) + "\n");
      //       System.err.println(accountFile.toString());
      // }

      // new forEach, uses lambda expression
      months.stream().forEach((t) -> {
        try{
          accountFile.write(t.getT().toString().toUpperCase() + " : R$ " + String.format("%.2f", t.getE()) + "\n");
        }catch(IOException ioEx){
          System.err.println("Could not manipulate the file " + this.monthlyBillFile.getName());
          ioEx.printStackTrace();
          System.gc();
          System.exit(1);
        }
      });
    }catch(IOException ioEx){
      System.out.println("Could not open the file " + this.monthlyBillFile.getName());
      ioEx.printStackTrace();
    }finally{
      System.gc();
    }
  }

}
