package edu.poo.test;

import edu.poo.build.Building;
import edu.poo.person.Resident;
import edu.poo.person.IOResident;
import edu.poo.economics.Bill;
import edu.poo.economics.IOBill;
import edu.poo.util.RemoveException;
import edu.poo.util.IOAble;
import java.io.File;
import java.util.Scanner;

public class Main{
  private static Scanner entry = new Scanner(System.in);
  public static void main(String []args){
    int op = 5;
    Building b = new Building("Por do Sol", new File(IOAble.LOCATION + "AllAccount" + IOAble.EXTENSION));
    Scanner entry = new Scanner(System.in);
    while(op != 0){
      System.out.println("0 -- Sair");
      System.out.println("1 -- Incluir morador");
      System.out.println("2 -- Remover morador");
      System.out.println("3 -- Listar moradores por andar");
      System.out.println("4 -- Listar moradores por apartmento");
      System.out.println("5 -- Listar moradores com garagem");
      System.out.println("6 -- Nova conta");

      op = entry.nextInt();

      switch (op) {
        case 1: addResident(b);
        break;
        case 2: removeResident(b);
        break;
        case 3: listByFloor(b);
        break;
        case 4:listByApartment(b);
        break;
        case 5:listWithGarage(b);
        break;
        case 6: chargeBill(b);
        break;
        case 0: {
          // entry.nextLine();
          System.gc();
          System.exit(0);
        }
        break;
        default: System.out.println("Inválido!");
      }
    }
    entry.close();
  }

  protected static void addResident(Building b){
    Resident res = null;
    String f = null;
    while((f == null) || (f.equalsIgnoreCase(""))){
      System.out.println("Digite o arquivo do residente a ser incluido: ");
      f = entry.nextLine();
    }
    try{
      res = new Resident(new File(IOResident.LOCATION + f + IOAble.EXTENSION), b);
      b.addResident(res);
    }catch(NullPointerException nuEx){
      System.err.println("The file cannot be null!" + nuEx.getMessage().toString());
      addResident(b);
    }catch(IllegalArgumentException ilEx){
      System.err.println("The file does not exists!" + ilEx.getMessage().toString());
      addResident(b);
    }finally{
      res = null;
      f = null;
    }
  }

  protected static void removeResident(Building b){
    Resident res;
    System.out.println("Digite o arquivo do residente a ser removido: ");
    String f = entry.nextLine();
    while((f == null) || (f.equalsIgnoreCase(""))){
      System.out.println("Digite o arquivo do residente a ser removido: ");
      f = entry.nextLine();
    }
    res = new Resident(new File(IOResident.LOCATION + f + IOAble.EXTENSION), b);

    try{
      b.removeResident(res);
      res = null;
    }catch(RemoveException rmEx){
      System.out.println("This resident cannot be removed!\n" + rmEx.getMessage().toString());
    }finally{
      res = null;
      System.gc();
    }
  }

  protected static void listByFloor(Building b){
    System.out.println("Digite o andar do qual deseja listar os moradores: ");
    short f = entry.nextShort();
    try{
      System.out.println(b.listResidentsByFloor(f));
    }catch(IllegalArgumentException ilEx){
      System.err.println("This floor is not valid!" + ilEx.getMessage().toString());
      listByFloor(b);
    }finally{
      System.gc();
    }
  }

  protected static void listByApartment(Building b){
    System.out.println("Digite o numero do apartmento a listar os moradores: ");
    short ap = entry.nextShort();
    try{
      System.out.println(b.listResidentsByApartNumber(ap));
    }catch(IllegalArgumentException ilEx){
      System.err.println("This apartmetn number is not valid!\n" + ilEx.getMessage().toString());
      listByApartment(b);
    }finally{
      System.gc();
    }
  }

  protected static void listWithGarage(Building b){
    System.out.println(b.listResidents(true));
  }

  protected static void chargeBill(Building b){
    Bill bill;
    System.out.println("Digite o arquivo da conta: ");
    String a = entry.nextLine();
    try{
      bill = new Bill(new File(IOBill.LOCATION + a + IOAble.EXTENSION));
      b.sendBill(bill);
    // }catch(FileException ioEx){
    //   System.out.println("This file cannot be open!");
    //   chargeBill(b, s);
    }catch(IllegalArgumentException ilEx){
      System.err.println(ilEx.getMessage().toString());
      chargeBill(b);
    }finally{
      bill = null;
      a = null;
      System.gc();
    }
  }
}
