package edu.poo.thing;

import java.util.ArrayList;
import java.util.Arrays;

public class Apartment{
  private ArrayList<Resident> listOfResidents = null;
  private short num;
  private Building building;

  public Apartment(Resident res, Building b){
    this.listOfResidents = new ArrayList<Resident>();
    this.listOfResidents.add(res);
    this.building = b;
  }

  public Short getNum(){
    return this.num;
  }

  public Building getBuilding(){
    return this.building;
  }

  public void addResident(Resident beutrano){
    this.listOfResidents.add(beutrano);
  }

  public ArrayList<Resident> getListResidents(){
    return this.listOfResidents;
  }

  public void setListResidents(ArrayList<Resident> newList){
    this.listOfResidents = newList;
  }

  // @Override
  // public String toString(){
  //   String list = new String("");
  //   // this.listOfResidents.stream().filter(r -> r != null).forEach(r -> {
  //   //   String aa = new String(r.getName());
  //   //   list += aa;
  //   // });
  //   for(Resident r : this.listOfResidents){
  //     list += (r != null) ? r.getName() + ", " : "";
  //   }
  //   list.toCharArray()[list.lastIndexOf(",")] = " ".toCharArray()[0];
  //   return new String(list).trim();
  // }
}
