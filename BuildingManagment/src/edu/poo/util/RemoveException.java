package edu.poo.util;

public class RemoveException extends Exception{
  static final long serialVersionUID = 42L; // the answer

  public RemoveException(){
    super();
  }

  public RemoveException(String msg){
    super(msg + "\nThere was a problem removeing!\n");
  }

  public RemoveException(String msg, Throwable th){
    super(msg + "\nThere was a problem removeing!\n", th);
  }

  public RemoveException(Throwable th){
    super(th);
  }
}
