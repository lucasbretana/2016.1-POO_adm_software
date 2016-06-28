package test;

import java.io.File;
import thing.*;

public class Main{
  public static void main(String []args){
    // Resident fulano = new Resident(new File(args[0]));
    // System.out.println(fulano.getName() + "\n" + fulano.getApartment() + "\n" + fulano.hasGarage());
    // new java.util.Scanner(System.in).nextLine();
    // System.out.println(fulano.getNameFromFile() + "\n" + fulano.getApartmentFromFile() + "\n" + fulano.hasGarageFromFile());
    Bill junho = new Bill(new File(args[0]));
    System.out.println(junho.getMonth());
  }
}
