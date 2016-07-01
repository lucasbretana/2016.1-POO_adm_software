package edu.poo.build;

import edu.poo.person.Resident;
import edu.poo.economics.BillPerApartment;
import edu.poo.economics.Bill;
import edu.poo.util.MyString;
import edu.poo.util.Tuple;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class Building{
  private String name = "Casa do caralho!";
  private short nFloors = 5;
  private short nAparAndar = 4;
  private Apartment [][]listOfApartments = null;
  private short numOccupiedApartments = 0;
  private Double totInc = new Double(0);
  private Double totOut = new Double(0);
  private File monthlyBillFile = null;
/**
  * Constructor method, creates a 5x4 matrix, that simulating a building
  * @method Building
  * @return [description]
  */
  @Deprecated
  public Building(File f){
    this.listOfApartments = new Apartment[nFloors][nAparAndar];
    if(f == null)
      throw new NullPointerException("The building has to have a file to read and save the monthly accounting!");
    this.monthlyBillFile = f;
  }

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
    this.doAccounting(bill);
  }

  /**
   * Show all the residents thar live on a specific apartment
   * @method listResidents
   * @param  floor         the floot to show the residents
   */
  public String listResidents(short floor) throws IllegalArgumentException{
    if((floor < 1) || (floor > this.nFloors))
      throw new IllegalArgumentException("The floor cannot be smaller than 1 or bigger than " + this.nFloors);
    --floor;
    if(Arrays.stream(this.listOfApartments[floor]).filter(a -> a != null).toArray().length == 0)
      System.out.println("There are no residents on the floor " +floor + "!");
    MyString s = new MyString(new String(""));
    Arrays.stream(this.listOfApartments[floor]).filter(ap -> ap != null).forEach(ap -> ap.getListResidents().stream().forEach(r -> s.concat(r.getName() + ", ")));
    return s.getStr().trim().substring(0, s.getStr().trim().length() - 1);
  }

  /**
   * Show all the residents thar live on a specific apartment
   * @method listResidents
   * @param  ap            the apartment to list the residents
   */
  public String listResidents(Apartment ap) throws NullPointerException{
    if(ap == null)
      throw new NullPointerException("Apartment cannot be null");
    if(ap.getListResidents().isEmpty())
      System.out.println("There are no residents on this apartment");
    MyString s = new MyString(new String(""));
    ap.getListResidents().stream().forEach(r -> System.out.println(r.getName()));
    return s.getStr().trim().substring(0, s.getStr().trim().length() - 1);
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
    return s.getStr().trim().substring(0, s.getStr().trim().length() - 1);
  }

  /**
   * Shows the apartment contability for an specific bill/month.
   * This will creates a file it the month of the bill and a small report
   * @method doAccount
   * @param  b         the bill to calculate incoming, outcoming and also the profit
   */
  private void doAccounting(Bill b){
    // System.out.println("R$ " + String.format("%.2f", this.totInc - this.totOut) + "");
    try(BufferedWriter accountFile = new BufferedWriter(new FileWriter(new File(b.getMonth())))){
      accountFile.write(b.getMonth().toUpperCase() + String.format("%.2f", this.totInc) + "\n");
    }catch(IOException ioEx){
      System.out.println("Could not create the file " + b.getMonth());
      ioEx.printStackTrace();
    }finally{
      System.gc();
    }

    ArrayList<Tuple<String,Float>> months = new ArrayList<Tuple<String,Float>>();

    String line;
    String num;
    String month;
    Float number;
    try(BufferedReader accountFile = new BufferedReader(new FileReader(this.monthlyBillFile))){
      while((line = accountFile.readLine()).equalsIgnoreCase("")){
        // line = accountFile.readLine();
        num = line = line.trim().split(":")[1];
        month = line = line.trim().split(":")[0];
        number = Float.parseFloat(num.trim().replaceFirst("R\\$", ""));
        months.add(new <String,Float>Tuple(month, number));
      }
    }catch(IOException ioEx){
      System.out.println("Could not create the file " + this.monthlyBillFile.getName());
      ioEx.printStackTrace();
    }finally{
      line = null;
      num = null;
      month = null;
      num = null;
      System.gc();
    }

    Arrays.sort(months.toArray());

    try(BufferedWriter accountFile = new BufferedWriter(new FileWriter(this.monthlyBillFile))){
      accountFile.write(b.getMonth().toUpperCase() + " : R$ " + String.format("%.2f", this.totInc) + "\n");
    }catch(IOException ioEx){
      System.out.println("Could not create the file " + this.monthlyBillFile.getName());
      ioEx.printStackTrace();
    }finally{
      System.gc();
    }

    this.totInc = this.totOut = new Double(0);
  }

}
