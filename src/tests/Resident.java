package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import debug.Library;

public class Resident{
  private String name;
  private boolean garage;
  private int apartment;

  private String fileDescriptorName;
  private FileReader fileDescriptor;
  /**
   * Empty constructor
   */
  Resident(){ }

  Resident(File resInfo){
    this.fileDescriptorName = resInfo.getName();
    Scanner in;
    try{
      this.fileDescriptor = new FileReader(resInfo);
      in = new Scanner(this.fileDescriptor);
      while(in.hasNextLine()){
        Library.echo(in.nextLine());
      }
    }catch(FileNotFoundException fileEx){
      System.err.println("The file " + this.fileDescriptorName + " was not found on " + resInfo.getAbsolutePath() + "!");
      fileEx.printStackTrace();
    }finally{

    }
  }

  /**
   * Public access to the name of the resident
   * @return gets the name from the resident
   */
  public String getName(){
    return this.name;
  }
  /**
   * Change the name of the Resident
   * @param newName the new name for the resident
   * @throw IllegalArgumentException is throw if the name is NULL or empty
   */
  private void setName(String newName){
    if(!newName.trim().equalsIgnoreCase(""))
      throw new IllegalArgumentException("Name cannot be NULL");
    this.name = newName;
  }

  /**
   * Public acess to see if the resident has a garage
    * @return return true if the resident has a garage
   */
  public Boolean hasGarage(){
    return this.garage;
  }
  private void setGarage(Boolean g){
    this.garage = g;
  }
  /**
   * Public acess to the apartment number
   * @return the apartment number
   */
  private Integer getApartment(){
    return this.apartment;
  }
  /**
   * Sets the newAp as the new resident apartment number
   * @param newAp the number of the apartment as an int
   */
  public void setApartment(Integer newAp){
    this.apartment = newAp;
  }
  /**
  * Sets the newAp as the new resident apartment number
  * @param newAp the number of the apartment as an String, uses Inter.parseInt.
  * @see setApartment(Integer)
   */
  public void setApartment(String newAp){
    this.setApartment(Integer.parseInt(newAp));
  }

}
