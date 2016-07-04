package edu.poo.economics;

import edu.poo.*;
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

    if(!desc.exists()) throw new IllegalArgumentException("The file " + desc.getName() + " does not exists!");

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

      this.admin = getSum().floatValue();

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
   * @method getEnergy
   * @return energy bill value
   */
  public Float getEnergy(){
    return this.energy;
  }

	/**
	* Sets new value of water bill
	* @param the nem water bill value
	*/
	public void setWater(Float water) {
		this.water = water;
	}

	/**
	* Sets new value of energy bill
	* @param the new value for energy
	*/
	public void setEnergy(Float energy) {
		this.energy = energy;
	}

	/**
	* @return value of gas bill
	*/
	public Float getGas() {
		return gas;
	}

	/**
	* Sets new value of gas
	* @param the new value of gas bill
	*/
	public void setGas(Float gas) {
		this.gas = gas;
	}

	/**
	* @return value of lobby bill
	*/
	public Float getLobby() {
		return lobby;
	}

	/**
	* Sets new value of lobby
	* @param the lobby bill value
	*/
	public void setLobby(Float lobby) {
		this.lobby = lobby;
	}

	/**
	* @return value of admin bill
	*/
	public Float getAdmin() {
		return admin;
	}

  /**
   * Sums up all the taxes for this month
   * @return the sum os taxes for this month
   */
  public Double getSum(){
    return new Double(this.water + this.energy + this.gas + this.lobby);
  }

	/**
	* Sets new value of admin, is calculated using all others bills information
	*/
	public void setAdmin() {
		this.admin = getSum().floatValue();
	}

	/**
	* Returns value of description, withc is the file that descrives this bill
	* @return a File object, with bill data
	*/
	public File getDescription() {
		return description;
	}
}
