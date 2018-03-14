public class BinaryNode 
{	
	private BinaryNode parent;
	
	private BinaryNode left;
	
	private BinaryNode right;
	
	private BinaryNode node;
	
	private Pixel value;
	
	/**
	 * Creates a binary node with the following properties
	 * 
	 * @param value the x,y coordinates and the color of the pixel
	 * @param left the left child of the binary node
	 * @param right the right child of the binary node
	 * @param parent the parent of the binary node
	 */
	public BinaryNode (Pixel value, BinaryNode left, BinaryNode right, BinaryNode parent)
	{
		this.value = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
		
	}
	
	//constructor which initializes a leaf node
	public BinaryNode()
	{
		this.value = null;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	/**
	 * @return returns the parent of the binary node
	 */
	public BinaryNode getParent()
	{
		return this.parent;
	}
	
	/**
	 * @param parent sets the parent of the binary node
	 */
	public void setParent(BinaryNode parent)
	{
		this.parent = parent;
	}
	
	/**
	 * sets the left child of a binary node to p
	 * 
	 * @param p is a binary node
	 */
	public void setLeft(BinaryNode p)
	{
		this.left = p;
	}
	
	/**
	 * sets the right child of a binary node to p
	 * 
	 * @param p is a binary node
	 */
	public void setRight(BinaryNode p)
	{
		this.right = p;
	}
	
	/**
	 * @return returns true if this binary node is a leaf or true if not
	 */
	public boolean isLeaf()
	{
		if(this.value == null)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * sets the data of this binary node to value
	 * 
	 * @param value the value of a pixel
	 */
	public void setData(Pixel value)
	{
		this.value = value;
	}
	
	/**
	 * @return returns the pixel stored in this binary node
	 */
	public Pixel getData()
	{
		return this.value;
	}
	
	/**
	 * @return returns the left child of this node
	 */
	public BinaryNode getLeft()
	{
		return this.left;
	}
	
	/**
	 * @return returns the right child of this node
	 */
	public BinaryNode getRight()
	{
		return this.right;
	}
	

}
