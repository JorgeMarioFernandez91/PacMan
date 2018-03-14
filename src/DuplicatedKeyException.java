/**
 * This class extends Exception and is used to throw an exception if a key already exists in the tree
 *
 */
public class DuplicatedKeyException extends Exception{
	
	public DuplicatedKeyException (String config)
	  {
		super ("The " + config + "already exists in the dictionary!");
	  }

}
