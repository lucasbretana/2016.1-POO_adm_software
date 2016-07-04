package edu.poo.util;

import java.util.ArrayList;

public class Tuple<T, E extends Comparable<E>> implements Comparable<Tuple<T,E>>{
  private T t;
  private E e;

  /**
  * Returns value of e
  * @return the E value from this tuple, is the second parameter on constructor
  */
  public E getE() {
    return this.e;
  }

  /**
  * Sets new value of t
  * @param t is the new parameter for the t value, type is T, the first type paramter
  */
  public void setT(T t) {
    this.t = t;
  }

  /**
  * Sets new value of e
  * @param e is the new paramter for the e value, type is E, the second type parameter
  */
  public void setE(E e) {
    this.e = e;
  }

  /**
   * Default constructor
   */
  public Tuple(T t, E e){
    this.t = t;
    this.e = e;
  }

  /**
  * Is the mothod override from the Comparable interface.
  * @param tup is the paramter to compare
  * @return the return value is 0 if this instance is equals to the paramter. Smaller than if it is smaller than the parameter and positivo otherwise.
  */
  @Override
  public int compareTo(Tuple<T,E> tup){
    return this.getE().compareTo(tup.getE());
  }

	/**
	* Returns value of t
	* @return the T value from this tuple, is the first paramter on constructor
	*/
	public T getT() {
		return this.t;
	}
}
