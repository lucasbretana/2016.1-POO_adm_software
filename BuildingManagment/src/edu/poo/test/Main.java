package edu.poo.test;

import edu.poo.build.Building;
import edu.poo.person.Resident;
import edu.poo.economics.Bill;
import java.io.File;

public class Main{
  public static void main(String []args){
    Building b = new Building("Por do Sol", new File("AllAccount"));
    b.addResident(new Resident(new File(args[0]), b));
    b.addResident(new Resident(new File(args[1]), b));
    b.addResident(new Resident(new File(args[2]), b));
    b.addResident(new Resident(new File(args[3]), b));
    b.addResident(new Resident(new File(args[4]), b));
    //
    b.sendBill(new Bill(new File(args[5])));
    b.sendBill(new Bill(new File(args[6])));
    b.sendBill(new Bill(new File(args[7])));
    b.sendBill(new Bill(new File(args[8])));

    System.out.println(b.listResidents((short)1));
  }
}
