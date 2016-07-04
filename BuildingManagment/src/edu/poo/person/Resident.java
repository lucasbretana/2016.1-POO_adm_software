package edu.poo.person;

import edu.poo.build.Building;
import edu.poo.economics.BillPerApartment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Resident extends Taxpayer implements IOResident{
  private Short apartment;
  private String name;
  private Boolean garage;
  private File description = null;
  private Building building = null;

/**
 * Constructor method, creates a resident using the information from a file
 * @param  desc     file that contains the resident information
 */
  public Resident(File desc, Building b) throws IllegalArgumentException, NullPointerException{
    if( desc == null ) throw new NullPointerException("The file used to create a resident is null!");
    if( b == null ) throw new NullPointerException("I cannot live in a null building!");

    if(!desc.exists()) throw new IllegalArgumentException("The file used to create a resident does not exists!");
    this.description = desc;

    String line = null;
    try(BufferedReader in = new BufferedReader(new FileReader(desc))){
      this.building = b;
      in.mark(1);
      line = in.readLine();
      line = line.replaceFirst(IOResident.APARTMENT, " ");
      this.apartment = Short.parseShort(line.trim());

      line = in.readLine();
      line = line.replaceFirst(IOResident.NAME, " ");
      this.name = line.trim();

      line = in.readLine();
      line = line.replaceFirst(IOResident.GARAGE, " ");
      this.garage = line.trim().equalsIgnoreCase(IOResident.YES);

      in.reset();
    }catch(FileNotFoundException fileExc){
      // erro ao abrir o arquivo
      fileExc.printStackTrace();
    }catch(IOException ioExc){
      // erro ao ler do arquivo
      ioExc.printStackTrace();
    }catch(NumberFormatException numEx){
      System.out.println("There was come problem with the file " + this.description.getName() + " that descrives the resident!\nThe \"" + line.toString() + "\" is not a valid apartment number!");
      numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
  }

/**
 * Tell this resident about a new conjunt of bill that has to be paid
 * @param  b          the conjunt of bills that has to be paid
 */
  public void chargeBill(BillPerApartment b){
    // System.out.println("I'm " + this.getName().split(" ")[0] + ", \tand I receive my bill on total of " + String.format( "%.2f", b.getTotal()) + " from month " + b.getMonth() + "\ton apartment " + this.getApartment() + ((this.hasGarage()) ? "\t with\t" : "\t without") + " \tgarage!");
    System.out.println("################################################################################");
    System.out.println("###\t### " + this.getName().split(" ")[0].toUpperCase() + " apt. " + this.getApartment() + " ###\t###");
    System.out.println("###\t### " + b.getMonth().toUpperCase());
    System.out.println("########");
    System.out.println("Total do prédio: \tR$\t" + String.format("%.2f", b.getFullTotal()));
    System.out.println("Apartamentos: \t\t" + this.building.getNumOccupiedApartments() + " / "+ this.building.getTotalApartments());
    System.out.println("Taxa Adm.: \t\t+ R$\t" + String.format("%.2f", b.getAdmin()));
    System.out.println("Taxa Garagem: \t\t+ R$\t" + String.format("%.2f", b.getGarage()));
    System.out.println("########\t\t _____________");
    System.out.println("TOTAL: \t\t\t= R$\t" + String.format( "%.2f", b.getTotal()));
    System.out.println("################################################################################");
  }

	/**
	* Returns value of apartment
	* @return the apartment number
	*/
	public Short getApartment() {
		return apartment;
	}

  /**
   * Returns the value of apartment that is in the file and reload information to the instance
   * @method getApartmentFromFile
   * @return the number of the apartment from the file
   */
  private Short getApartmentFromFile(){
    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(this.description))){
      in.mark(1);

      line = in.readLine();
      line = line.replaceFirst(IOResident.APARTMENT, " ");
      this.apartment = Short.parseShort(line.trim());

      in.reset();
    }catch(FileNotFoundException fileExc){
      // erro ao abrir o arquivo
      fileExc.printStackTrace();
    }catch(IOException ioExc){
      // erro ao ler do arquivo
      ioExc.printStackTrace();
    }catch(NumberFormatException numEx){
      numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
    return this.apartment;
  }


	/**
	* Sets new value of apartment
	* @param apartment the apartment number
	*/
	public void setApartment(Short apartment) {
		this.apartment = apartment;
	}

	/**
	* Returns value of name
	* @return the name
	*/
	public String getName() {
		return name;
	}

  /**
   * Look in the file for the name of the resident and reload
   * @method getNameFromFile
   * @return the name of the resident
   */
  private String getNameFromFile(){
    String line = null;
    try(BufferedReader in = new BufferedReader(new FileReader(this.description))){
      in.mark(1);
      line = in.readLine();

      line = in.readLine();
      line = line.replaceFirst(IOResident.NAME, " ");
      this.name = line.trim();

      in.reset();
    }catch(FileNotFoundException fileExc){
      // erro ao abrir o arquivo
      fileExc.printStackTrace();
    }catch(IOException ioExc){
      // erro ao ler do arquivo
      ioExc.printStackTrace();
    }catch(NumberFormatException numEx){
      numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
    return this.name;
  }

	/**
	* Sets new value of name
	* @param n is the new name
	*/
  public void setName(String n) throws IllegalArgumentException{
    if(n.equalsIgnoreCase("")){
      throw new IllegalArgumentException("The name cannot be changed to null!");
    }
    this.name = n;
  }

	/**
	* Returns value of garage
	* @return the information about having or not a garage
	*/
	public Boolean hasGarage() {
		return garage;
	}

  /**
   * Returns value of garaga, that is in the file
   * @method hasGarageFromFile
   * @return the boolean information saying if this resident has a garage
   */
  private Boolean hasGarageFromFile(){
    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(this.description))){
      in.mark(1);
      line = in.readLine();

      line = in.readLine();

      line = in.readLine();
      line = line.replaceFirst(IOResident.GARAGE, " ");
      this.garage = line.trim().equalsIgnoreCase(IOResident.YES);

      in.reset();
    }catch(FileNotFoundException fileExc){
      // erro ao abrir o arquivo
      fileExc.printStackTrace();
    }catch(IOException ioExc){
      // erro ao ler do arquivo
      ioExc.printStackTrace();
    }catch(NumberFormatException numEx){
      numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
    return this.garage;
  }

	/**
	* Sets new value of garage
	* @param garage the info about having or not a garage
	*/
	public void setGarage(Boolean garage) {
		this.garage = garage;
	}

	/**
	* Returns value of description
	* @return the file that descrives this resident
	*/
	public File getDescription() {
		return description;
	}

	/**
	* Sets new value of description
	* @param description is the file that descrives this resident
	*/
	public void setDescription(File description) {
		this.description = description;
	}

}
