/**
 * This class extends Exception and is used to throw an exception if a key does not exist
 *
 */

public class InexistentKeyException extends Exception{
	
	public InexistentKeyException (String config)
	  {
		super ("The " + config + "does not exist!");
	  }

}
