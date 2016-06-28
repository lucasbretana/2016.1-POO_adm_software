package thing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Bill implements IOBill{
  private String month;
  private Float water;
  private Float energy;
  private Float gas;
  private Float lobby;
  private Float admin;
  private File description = null;

/**
 * Constructor method, creates a bill using the information from a file
 * @method Bill
 * @param  desc is the file that descrives the bill it self
 * @return   an instance  os Bill
 * @throws IllegalArgumentException when the file that decrive the Bill is not valid
 */
  public Bill(File desc) throws IllegalArgumentException{
    if( desc == null ) throw new IllegalArgumentException("The file used to create a resident is not valid!");
    this.description = desc;

    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(desc))){
      in.mark(1);
      line = in.readLine();
      this.month = line.replaceFirst(IOBill.MONTH, " ").trim();

      line = in.readLine();
      this.water = Float.parseFloat(line.replaceFirst(IOBill.WATER, " ").trim().replaceAll(",", "."));

      line = in.readLine();
      this.energy = Float.parseFloat(line.replaceFirst(IOBill.ENERGY, " ").trim().replaceAll(",", "."));

      line = in.readLine();
      this.gas = Float.parseFloat(line.replaceFirst(IOBill.GAS, " ").trim().replaceAll(",", "."));

      line = in.readLine();
      this.lobby = Float.parseFloat(line.replaceFirst(IOBill.LOBBY, " ").trim().replaceAll(",", "."));

      this.admin = new Double((this.water + this.energy + this.gas + this.lobby) * 0.1).floatValue();

      in.reset();
    }catch(FileNotFoundException fileEx){
      throw new IllegalArgumentException("There was a problem locating the file (" + desc.getName() + ") on " + desc.getPath() + "!\n" + fileEx.getStackTrace().toString());
      // fileEx.printStackTrace();
    }catch(IOException ioEx){
      throw new IllegalArgumentException("There was a problem reading the file (" + desc.getName() + ")!\n" + ioEx.getStackTrace().toString());
      // ioEx.printStackTrace();
    }catch(NumberFormatException numEx){
      throw new IllegalArgumentException("There was a problem with the format of some of the information in the file (" + desc.getName() + ")!\n" + numEx.getStackTrace().toString());
      // numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
  }

  /**
   * Gets the month from memory
   * @method getMonth
   * @return the month if this bill
   */
  public String getMonth(){
    return this.month;
  }
  /**
   * Change the month name
   * @method setMonth
   * @param  m is the new name of the month
   * @IllegalArgumentException if the new month name is not valid
   */
  public void setMonth(String m) throws IllegalArgumentException{
    if(m.trim().equalsIgnoreCase("")){
      throw new IllegalArgumentException("The month value cannot be null!");
    }
    this.month = m;
  }
  /**
   * Returns the water value
   * @method getWater
   * @return [description]
   */
  public Float getWater(){
    return this.water;
  }

  /**
   *
   * @method getEnergy
   * @return [description]
   */
  public Float getEnergy(){
    return this.energy;
  }

	/**
	* Sets new value of water
	* @param
	*/
	public void setWater(Float water) {
		this.water = water;
	}

	/**
	* Sets new value of energy
	* @param
	*/
	public void setEnergy(Float energy) {
		this.energy = energy;
	}

	/**
	* Returns value of gas
	* @return
	*/
	public Float getGas() {
		return gas;
	}

	/**
	* Sets new value of gas
	* @param
	*/
	public void setGas(Float gas) {
		this.gas = gas;
	}

	/**
	* Returns value of lobby
	* @return
	*/
	public Float getLobby() {
		return lobby;
	}

	/**
	* Sets new value of lobby
	* @param
	*/
	public void setLobby(Float lobby) {
		this.lobby = lobby;
	}

	/**
	* Returns value of admin
	* @return
	*/
	public Float getAdmin() {
		return admin;
	}

	/**
	* Sets new value of admin
	* @param
	*/
	public void setAdmin(Float admin) {
		this.admin = new Double((this.water + this.energy + this.gas + this.lobby) * 0.1).floatValue();
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
