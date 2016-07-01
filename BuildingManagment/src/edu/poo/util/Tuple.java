package edu.poo.util;

import java.util.ArrayList;

public class Tuple<T, E extends Comparable<E>> implements Comparable<E>{
  private T t;
  private E e;

  /**
  * Returns value of e
  * @return
  */
  public E getE() {
    return this.e;
  }

  /**
  * Sets new value of t
  * @param
  */
  public void setT(T t) {
    this.t = t;
  }

  /**
  * Sets new value of e
  * @param
  */
  public void setE(E e) {
    this.e = e;
  }

  public Tuple(T t, E e){
    this.t = t;
    this.e = e;
  }

  @Override
  int compareTo(E e){
    return this.getE().compareTo(e);
  }

	/**
	* Returns value of t
	* @return
	*/
	public T getT() {
		return this.t;
	}
}
