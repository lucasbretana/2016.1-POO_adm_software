package edu.poo.economics;

public interface IOBill{
    /**	Is the string that define the line of the month name */
  public final static String MONTH = "Mês: ";

  /**	Is the string that define the line of the water bill */
  public final static String WATER = "Água: R\\$ ";

  /**	Is the string that define the line of the power bill */
  public final static String ENERGY = "Luz: R\\$ ";

  /**	Is the string that define the line of the gas bill */
  public final static String GAS = "Gás: R\\$ ";

  /**	Is the string that define the line of the lobby */
  public final static String LOBBY = "Portaria: R\\$ ";

  /** The default directorie for the bills files */
  public final static String LOCATION = "../extra/Bills/";
}
