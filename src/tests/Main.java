package tests;

import java.io.FileReader;
import java.io.File;


/**
 * Used only to run some tests, do not implement real code in here
 * */
public class Main{

	/**
	 * Stupid Flandes, I meand, main function
	 * @param String args[] : 0 - true or false, indicates if is in debug mode
	 *               					1 - the Residet file name
	 * */
	public static void main(String []args){
		Resident fulano = new Resident(new File(args[1]));
	}
}
