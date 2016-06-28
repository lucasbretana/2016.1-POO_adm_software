package thing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Resident implements IOResident{
  private Integer apartment;
  private String name;
  private Boolean garage;
  private File description = null;

/**
 * Constructor method, creates a resident using the information from a file
 * @method Resident
 * @param  desc     file that contains the resident information
 * @return          a instance of Resident class
 */
  public Resident(File desc) throws IllegalArgumentException{

    if( desc == null ) throw new IllegalArgumentException("The file used to create a resident is not valid!");
    this.description = desc;

    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(desc))){
      in.mark(1);
      line = in.readLine();
      line = line.replaceFirst(IOResident.APARTMENT, " ");
      this.apartment = Integer.parseInt(line.trim());


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
      numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
  }

	/**
	* Returns value of apartment
	* @return
	*/
	public Integer getApartment() {
		return apartment;
	}

  /**
   * Returns the value of apartment that is in the file
   * @method getApartmentFromFile
   * @return [description]
   */
  public Integer getApartmentFromFile(){
    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(this.description))){
      in.mark(1);

      line = in.readLine();
      line = line.replaceFirst(IOResident.APARTMENT, " ");
      this.apartment = Integer.parseInt(line.trim());

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
	* @param
	*/
	public void setApartment(Integer apartment) {
		this.apartment = apartment;
	}

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

  /**
   * Look in the file for the name of the resident and reload
   * @method getNameFromFile
   * @return the name of the resident
   */
  public String getNameFromFile(){
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
	* @return
	*/
	public Boolean hasGarage() {
		return garage;
	}

  /**
   * Returns value of garaga, that is in the file
   * @method hasGarageFromFile
   * @return the boolean information saying if this resident has a garage
   */
  public Boolean hasGarageFromFile(){
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
	* @param
	*/
	public void setGarage(Boolean garage) {
		this.garage = garage;
	}

	/**
	* Returns value of description
	* @return
	*/
	public File getDescription() {
		return description;
	}

	/**
	* Sets new value of description
	* @param
	*/
	public void setDescription(File description) {
		this.description = description;
	}
}
