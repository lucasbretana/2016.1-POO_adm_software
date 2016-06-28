package thing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Resident implements IOResident{
  private Integer numAp;
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
      this.numAp = Integer.parseInt(line.trim());


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

  public Integer getApartment(){return this.numAp;}
  public String getName(){return this.name;}
  public Boolean hasGarage(){return this.garage;}
  public void setApartment(Integer ap){this.numAp = ap;}
  public void setName(String n) throws IllegalArgumentException{
    if(n.equalsIgnoreCase("")){
      throw new IllegalArgumentException("The name cannot be changed to null!");
    }
    this.name = n;
  }
  public void setGarage(Boolean g){this.garage = g;}

  public Integer getApartmentFromFile(){
    String line;
    try(BufferedReader in = new BufferedReader(new FileReader(this.description))){
      in.mark(1);

      line = in.readLine();
      line = line.replaceFirst(IOResident.APARTMENT, " ");
      this.numAp = Integer.parseInt(line.trim());

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
    return this.numAp;
  }
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

}
