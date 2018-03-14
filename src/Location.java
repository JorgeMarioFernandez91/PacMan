/**
 * This class represents the location of the pixel on the screen
 */

public class Location 
{
	int x;
	int y;
	
	Location loc;
	
	/**
	 * @param x the location of a pixel in the horizontal plane
	 * @param y the location of a pixel in the vertical plane
	 */
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return returns the x coordinate of the pixel
	 */
	public int xCoord()
	{
		return this.x;
	}
	
	/**
	 * @return returns the y coordinate of the pixel
	 */
	public int yCoord()
	{
		return this.y;
	}
	
	/**
	 * @return returns -1 if x is less than x', or 1 if x is greater than x', or 0 if x is equal to x'
	 */
	public int compareTo(Location p)
	{
		//original location is less if the following is true
		if ((this.x < p.xCoord()) || (this.x == p.xCoord() && this.y < p.yCoord()))
		{
			return -1;
		}
		//if original location is equal to new location return 0
		else if (this.x == p.xCoord() && this.y == p.yCoord())	
		{
			return 0;
		}
		//if new location is bigger than old location
		return 1;
	}
}
