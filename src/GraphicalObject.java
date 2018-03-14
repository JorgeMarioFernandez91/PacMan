/**
 * This class represents the graphical objects in the game
 */

public class GraphicalObject implements GraphicalObjectADT
{
	private int id;
	
	private int width;
	
	private int height;
	
	private String type;
	
	private Location pos;
	
	private BinarySearchTree tree;
	
	/**
	 * @param id a numerical representation of the graphical oject
	 * @param width the width of the graphical object
	 * @param height the height of the graphical object
	 * @param type a string representation of the graphical object
	 * @param pos the x,y coordination of the graphical object
	 */
	public GraphicalObject (int id, int width, int height, String type, Location pos)
	{
		this.id = id;
		this.width = width;
		this.height = height;
		this.type = type;
		this.pos = pos;
		
		this.tree = new BinarySearchTree();
	}
	
	/*
	 * Returns the width of this figure
	 */
	public int getWidth()
	{
		return this.width;
	}

	/*
	 * Returns the height of this figure
	 */
	public int getHeight()
	{
		return this.height;
	}

	/*
	 * Returns the type of this figure
	 */
	public String getType()
	{
		return this.type;
	}

	/*
	 * Returns the id of this figure
	 */
	public int getId()
	{
		return this.id;
	}

	/*
	 * Returns the offset or position of this figure
	 */
	public Location getOffset()
	{
		return this.pos;
	}

	/*
	 * Changes the offset of this figure to the specified value.
	 */
	public void setOffset(Location value)
	{
		this.pos = value;
	}

	/*
	 * Change the type of this figure to the specified value.
	 */
	public void setType(String t)
	{
		this.type = t;
	}

	/*
	 * Adds the given Pixel object into the binary search tree associated with
	 * this figure. A DuplicatedKeyException is thrown if the figure already has
	 * a Pixel with the same key as pix.
	 */
	public void addPixel(Pixel pix) throws DuplicatedKeyException
	{
		this.tree.put(this.tree.getRoot(), pix);
	}

	/*
	 * Returns true if this figure intersects the one specified in the
	 * parameter; it returns false otherwise.
	 */
	public boolean intersects(GraphicalObject fig)
	{
		Pixel thisPix;
		Pixel largestPix;
		Location figureOffset;
		
		int xActualPos;
		int yActualPos;
		//if boundaries of figures do overlap then set flags to true
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false; 
		
		//find the x,y coordinates of the four corners of the pacman
		Location pacmanTopLeft = new Location(this.getOffset().xCoord(), this.getOffset().yCoord());
		Location pacmanTopRight = new Location(this.getOffset().xCoord() + this.getWidth(), this.getOffset().yCoord());
		Location pacmanBottomLeft = new Location(this.getOffset().xCoord(), this.getOffset().yCoord() +this.getHeight());
		Location pacmanBottomRight = new Location(this.getOffset().xCoord() + this.getWidth(), this.getOffset().yCoord() +this.getHeight());
		//find the x,y coordinates of the four corners of the other figures
		Location figTopLeft = new Location(fig.getOffset().xCoord(), fig.getOffset().yCoord());
		Location figTopRight = new Location(fig.getOffset().xCoord() + fig.getWidth(), fig.getOffset().yCoord());
		Location figBottomLeft = new Location(fig.getOffset().xCoord(), fig.getOffset().yCoord() + fig.getHeight());
		Location figBottomRight = new Location(fig.getOffset().xCoord() + fig.getWidth(), fig.getOffset().yCoord() + fig.getHeight());
		
		//checks the position of the top right corner of the pacman and the top left corner of the fig
		if(pacmanTopRight.compareTo(figTopLeft) == 1 || pacmanTopRight.compareTo(figTopLeft) == 0 
		   && figTopLeft.compareTo(pacmanTopRight) == -1 || figTopLeft.compareTo(pacmanTopRight) == 0)	
		{
			flag1 = true;
		}
		//checks the position of the top left corner of the pacman and the top right corner of the fig
		if(pacmanTopLeft.compareTo(figTopRight) == -1 || pacmanTopLeft.compareTo(figTopRight) == 0 
		   && figTopRight.compareTo(pacmanTopLeft) == 1 || figTopRight.compareTo(pacmanTopLeft) == 0)
		{
			flag2 = true;
		}
		//checks the position of the bottom right corner of the pacman and the bottom left corner of the fig
		if(pacmanBottomRight.compareTo(figBottomLeft) == 1 || pacmanBottomRight.compareTo(figBottomLeft) == 0 
		   && figBottomRight.compareTo(pacmanBottomLeft) == 1 || figBottomRight.compareTo(pacmanBottomLeft) == 0)
		{
			flag3 = true;
		}
		//checks the position of the bottom left corner of the pacman and the bottom right corner of the fig
		if(pacmanBottomLeft.compareTo(figBottomRight) == -1 || pacmanBottomLeft.compareTo(figBottomRight) == 0 
		   && figBottomLeft.compareTo(pacmanBottomRight) == -1 || figBottomLeft.compareTo(pacmanBottomRight) == 0)
		{
			flag4 = true;
		}
		
		//if true then the following will compare the actual shapes within the object squares
		if (flag1 && flag2 && flag3 && flag4)
		{			
			try {
				//find the smallest pixel in the tree, this will be where the for loop begins
				thisPix = this.tree.smallest(this.tree.getRoot());
				//find the largest pixel in the tree, this is where the for loop will end
				largestPix = this.tree.largest(this.tree.getRoot());
				//search all pixels, starting from the smallest; comparing pixels stored to pixels of figures if they equal then break out; find the next successor
				for (thisPix.getLocation()  ; thisPix.getLocation().compareTo(largestPix.getLocation()) != 0 ; thisPix = this.tree.successor(this.tree.getRoot(), thisPix.getLocation()))
				{
					//find the x,y offset of the pacman pixels in relation to the offset of the other objects
					xActualPos = thisPix.getLocation().xCoord() + this.getOffset().xCoord() - fig.getOffset().xCoord();
					yActualPos = thisPix.getLocation().yCoord() + this.getOffset().yCoord() - fig.getOffset().yCoord();
					//this is the new location of the proper offset
					figureOffset = new Location(xActualPos, yActualPos);
					//if the new offstep of the pacman is in the figure tree then return true
					if (fig.findPixel(figureOffset) != null)
					{
						return true;
					}
				}
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
		//if there is no collision then return false, if false is returned then the pacman is allowed to continue moving on the screen
		return false;
	}
	
	/**
	 * @return returns the Pixel the pixel which matches the location p
	 */
	private Pixel findPixel(Location p)
	{
		return this.tree.get(this.tree.getRoot(), p);
	}

}
