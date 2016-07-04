package edu.poo.util;

final public class MyString{
  private String str = "";
  public MyString(String c){
    this.str = c;
  }

  public String getStr(){
    return this.str;
  }

  public void setStr(String s){
    this.str = s;
  }

  public void concat(String s){
    this.str = this.str.concat(s);
  }
}
