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
 * @return      an instance  os Bill
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
      throw new IllegalArgumentException("There was come problem locating the file (" + desc.getName() + ") on " + desc.getPath() + "!\n" + fileEx.getStackTrace().toString());
      // fileEx.printStackTrace();
    }catch(IOException ioEx){
      throw new IllegalArgumentException("There was come problem locating the file (" + desc.getName() + ") on " + desc.getPath() + "!\n" + fileEx.getStackTrace().toString());
      // ioEx.printStackTrace();
    }catch(NumberFormatException numEx){
      numEx.printStackTrace();
    }finally{
      line = null;
      System.gc();
    }
  }

  public String getMonth(){
    return this.month;
  }
}
