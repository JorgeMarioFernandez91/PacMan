/**
 * CS-2210 Assignment 4
 * BinarySearchTree.java
 * The purpose of this class is to store pixel by using a data structure known as a binary search tree.
 * In the tree at each node, the left child is less in value than the current node, and the right child is greater than the value
 * of the current node. This allows for fast run times because the tree simply compares values to each other, so every time
 * the location of a pixel is narrowed we can ignore nodes where we know the location is not. So essentially we only have to search
 * a small portion of the tree rather than having to search the whole tree in the average case
 * 
 * @author Jorge Fernandez
 * @version 1.0 2017-11-19
 */

public class BinarySearchTree implements BinarySearchTreeADT
{
	private BinaryNode root;
	
	private BinaryNode parent;
	
	/**
	 * creates an empty binary search tree
	 */
	public BinarySearchTree()
	{
		this.root = new BinaryNode();
	}

	/**
	 * @return returns the root of the binary tree
	 */
	public BinaryNode getRoot() 
	{
		return this.root;
	}

	/**
	 * recursively call get until the correct pixel is found or until a leaf is reached
	 * 
	 * @param r a binary node
	 * @param key the value of the pixel
	 * 
	 * @return returns the pixel which contains this key or returns null if 
	 * the key has no value
	 */
	public Pixel get(BinaryNode r, Location key) 
	{
		//this is the base case, if r is a leaf then return null
		if (r.isLeaf())
		{
			return r.getData();
		}
		else
		{
			if (key.compareTo(r.getData().getLocation()) == 0) //if key == r
			{
				return r.getData();
			}
			else if (key.compareTo(r.getData().getLocation()) == -1 ) //if key < r
			{
				return get(r.getLeft(), key);
			}
			else if (key.compareTo(r.getData().getLocation()) == 1)	//if key > r												
			{
				return get(r.getRight(), key);
			}
		}
		return null;
	}
	
	/**
	 * recursively call getNode to get the binary node where the pixel is stored
	 * 
	 * @param r a binary node
	 * @param key is the key of the pixel
	 * 
	 * @returns the binary node which would contain the pixel with this key
	 */
	private BinaryNode getNode(BinaryNode r, Location key)
	{
		if (r.isLeaf())
		{
			return r;
		}
		else
		{
			if (key.compareTo(r.getData().getLocation()) == 0) //if key == r
			{
				return r;
			}
			else if (key.compareTo(r.getData().getLocation()) == -1 ) //if key < r
			{
				return getNode(r.getLeft(), key);
			}
			else if (key.compareTo(r.getData().getLocation()) == 1)	//if key > r											
			{
				return getNode(r.getRight(), key);
			}
		}
		return r;
	}
	
	/**
	 * recursively call insert by checking the value of the pixel and comparing it to the current pixel
	 * once the proper spot is found then insert the pixel
	 * 
	 * @param r a binary node
	 * @param p the value of pixel
	 */
	private void insert(BinaryNode r, Pixel p)
	{
		if (r.isLeaf())
		{
			BinaryNode left = new BinaryNode();
			BinaryNode right = new BinaryNode();
			
			r.setData(p);

			r.setLeft(left = new BinaryNode());
			
			r.setRight(right = new BinaryNode());
		}
		else
		{
			if (p.getLocation().compareTo(r.getData().getLocation()) == -1)
			{
				insert(r.getLeft(), p);
			}
			else if (p.getLocation().compareTo(r.getData().getLocation()) == 1)
			{
				insert(r.getRight(), p);
			}
		}
	}
	
	/**
	 * recursively call findParent to get the parent of the node in question
	 * 
	 * @param r a binary node
	 * @param key the position of the pixel
	 * @return returns the parent of the node r
	 */
	private BinaryNode findParent(BinaryNode r, Location key)
	{
		//checks if left or right are internal nodes, if so then check if the data in those nodes match the key of the key we're looking for
		//a match means that r is the parent of of the node with the key, so we return r
		if ((!r.getLeft().isLeaf() && (r.getLeft().getData().getLocation().xCoord() == key.xCoord()) && (r.getLeft().getData().getLocation().yCoord() == key.yCoord())) 
			|| (!r.getRight().isLeaf() && (r.getRight().getData().getLocation().xCoord() == key.xCoord()) && (r.getRight().getData().getLocation().yCoord() == key.yCoord())))
		{
			return r;
		}
		else
		{
			if (key.compareTo(r.getData().getLocation()) == 0) //if key == r
			{
				return r;
			}
			else if (key.compareTo(r.getData().getLocation()) == -1 ) //if key < r
			{
				return findParent(r.getLeft(), key);
			}
			else if (key.compareTo(r.getData().getLocation()) == 1)	//if key > r		
			{
				return findParent(r.getRight(), key);
			}
		}
		return r;
	}
	
	/**
	 * stores a pixel into the binary tree by calling the insert method
	 * 
	 * @param r a binary node
	 * @param data the data stored in the pixel
	 */
	public void put(BinaryNode r, Pixel data) throws DuplicatedKeyException {
		
		//if p exists in the tree it will return error, else it will be null
		Pixel p = get(r, data.getLocation());
		
		BinaryNode node = getNode(r, data.getLocation());
		
		if (!node.isLeaf()) //if data already exists in the tree
		{
			throw new DuplicatedKeyException("Key Already Exists"); //if node exists and it is an internal node
		}
		else
		{
			p = data;
			
			insert(r, p);
		}
	}

	/**
	 * @param r is a binary node
	 * @param key the position of the pixel
	 */
	public void remove(BinaryNode r, Location key) throws InexistentKeyException 
	{
		BinaryNode node = getNode(r, key);
		
		BinaryNode parent = new BinaryNode();
		
		//if node is a leaf throw exception
		if (node.isLeaf()) 
		{
			throw new InexistentKeyException("Key Does Not Exist!");
		}
		//finding parent of node as long as node is not the root
		if (node.getData().getLocation() != this.root.getData().getLocation())
		{
			parent = findParent(r, node.getData().getLocation());
		}
		//if node is the root and it has an internal node and a leaf
		if (node.getData().getLocation() == this.root.getData().getLocation() && (node.getLeft().isLeaf() && !node.getRight().isLeaf()) 
			|| (node.getRight().isLeaf() && !node.getLeft().isLeaf()))
		{
			try 
			{
				//if right node of the root is not a leaf then get smallest node from the right side of the tree
				if (!node.getRight().isLeaf())
				{
					//smallest node on the right side to change values
					Pixel smallestPixel = smallest(node.getRight());
					//change the data of current node to the data of s
					node.setData(smallestPixel);
					//node which contains smallest key
					BinaryNode smallestNode = getNode(r, smallestPixel.getLocation());
					//set parent of S to right child of S
					BinaryNode parentSmallNode = findParent(r, smallestPixel.getLocation());
					//if there is only the root and its replacement in the tree
					if (parentSmallNode.getData().getLocation() == this.root.getData().getLocation())
					{
						parentSmallNode.setRight(smallestNode.getRight());
					}
					else
					{
						//if smallest node has a right child, in other words it is not a leaf
						parentSmallNode.setLeft(smallestNode.getRight());
					}
				}
				//if left node of the root is not a leaf and then get the largest node from the left side of the tree 
				else if (!node.getLeft().isLeaf())
				{
					//smallest node on the right side to change values
					Pixel largestPixel = largest(node.getLeft());
					//change the data of current node to the data of s
					node.setData(largestPixel);
					//node which contains smallest key
					BinaryNode largestNode = getNode(r, largestPixel.getLocation());
					//set parent of S to right child of S
					BinaryNode parentLargeNode = findParent(r, largestPixel.getLocation());
					//if there is only the root and its replacement in the tree
					if (parentLargeNode.getData().getLocation() == this.root.getData().getLocation())
					{
						parentLargeNode.setLeft(largestNode.getLeft());
					}
					else
					{
						//if smallest node has a right child, in other words it is not a leaf
						parentLargeNode.setRight(largestNode.getLeft());
					}
				}
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
		//if root has internal nodes as children
		else if (node.getData().getLocation() == this.root.getData().getLocation() && (!node.getLeft().isLeaf() && !node.getRight().isLeaf()))
		{
			try 
			{
				//smallest node on the right side to change values
				Pixel smallestPixel = smallest(node.getRight());
				//change the data of current node to the data of s
				node.setData(smallestPixel);
				//node which contains smallest key
				BinaryNode smallestNode = getNode(r, smallestPixel.getLocation());
				//set parent of S to right child of S
				BinaryNode parentSmallNode = findParent(r, smallestPixel.getLocation());
				//if there is only the root and its replacement in the tree
				if (parentSmallNode.getData().getLocation() == this.root.getData().getLocation())
				{
					parentSmallNode.setRight(smallestNode.getRight());
				}
				else
				{
					//if smallest node has a right child, in other words it is not a leaf
					parentSmallNode.setLeft(smallestNode.getRight());
				}
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
		//if root only has leaves and no internal children
		else if (node.getData().getLocation() == this.root.getData().getLocation() && (node.getLeft().isLeaf() && node.getRight().isLeaf()))
		{
			node.setData(null);
		}
		//if node is not the root and has an internal node and a leaf
		else if (node.getData().getLocation() != this.root.getData().getLocation() && ((node.getLeft().isLeaf() && !node.getRight().isLeaf()) 
			|| (node.getRight().isLeaf() && !node.getLeft().isLeaf())))
		{
			//if the right child is an internal node
			if (!node.getRight().isLeaf()) 
			{
				//make the parent of this node point to the right child of this node
				if (parent.getLeft().getData().getLocation() == node.getData().getLocation())
				{
					parent.setLeft(node.getRight());
				}
				//if smallest node has no children
				else if (parent.getRight().getData().getLocation() == node.getData().getLocation())
				{
					parent.setRight(node.getRight());
				} 
			}
			//if the left child is an internal node
			else if (!node.getLeft().isLeaf())
			{
				//make the parent of this node point to the right child of this node
				if (parent.getLeft().getData().getLocation() == node.getData().getLocation())
				{
					parent.setLeft(node.getLeft());
				}
				//if smallest node has no children
				else if (parent.getRight().getData().getLocation() == node.getData().getLocation())
				{
					parent.setRight(node.getLeft());
				}
			}
		}
		//if node has only leaves as children
		else if (node.getLeft().isLeaf() && node.getRight().isLeaf()) 
		{
			//if the node is to the left of the parent get the left of the parent to point to the left node of node
			if (!parent.getLeft().isLeaf() && parent.getLeft().getData().getLocation() == node.getData().getLocation())
			{
				parent.setLeft(node.getLeft());
			}
			//if the node is to the right of the parent then get the right of the parent to point to the right of the node
			else if (!parent.getRight().isLeaf() && parent.getRight().getData().getLocation() == node.getData().getLocation())
			{
				parent.setRight(node.getLeft());
			}
		}
		//if both children are internal nodes
		else if (!node.getLeft().isLeaf() && !node.getRight().isLeaf())	
		{  
			try 
			{
				//smallest node on the right side to change values
				Pixel smallestPixel = smallest(node.getRight());
				//node which contains smallest key
				BinaryNode smallestNode = getNode(r, smallestPixel.getLocation());
				//find parent of smallestNode
				BinaryNode parentSmallNode = findParent(r, smallestPixel.getLocation());
				//change the data of current node to the data of smallestPixel
				node.setData(smallestPixel);
				//set parent of smallestNode to right child of smallestNode
				parentSmallNode.setLeft(smallestNode.getRight());
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
	}
	
	/**
	 * @param r a binary node
	 * @param key the position of the pixel
	 * 
	 * @return returns the successor to the node r
	 */
	public Pixel successor(BinaryNode r, Location key) 
	{
		BinaryNode node = getNode(r, key);
		
		BinaryNode parent = new BinaryNode();
		
		BinaryNode parentParent = new BinaryNode();
		
		BinaryNode succ = new BinaryNode();
		/*
		 * check if node is a leaf, if it is than means that it is not in the tree
		 * so we insert it, find it's parent, then remove the pixel with the value key and return the successor
		 */
		if (node.isLeaf()) 
		{
			Pixel p = new Pixel(key, 0);
			
			try 
			{
				//put the pixel into the tree because it doesn't exist in it
				put(r, p);
				//find parent of key
				parent = findParent(r, node.getData().getLocation());
				//keep finding parents until a parent is greater than key
				parentParent = findParent(r, parent.getData().getLocation());
				//use successor here to recursively get successor
				succ = getNode(this.root, successor(r, key).getLocation());
				//remove the pixel 
				remove(r, key);
			} 
			catch (InexistentKeyException | DuplicatedKeyException e) 
			{
				System.out.println(e);
			}
			return succ.getData();
		}
		
		//if r is an internal node and r.right is an internal node
		if (!node.isLeaf() && !node.getRight().isLeaf())
		{
			try 
			{
				//return the smallest node on the right, that is the success or this pixel
				return smallest(node.getRight());
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
		//if node is not the root it's right child, which could have been the successor, is a leaf
		else if (!node.isLeaf() && node.getRight().isLeaf() && node.getData().getLocation() != this.root.getData().getLocation())
		{
			//get parent of node
			parent = findParent(r, node.getData().getLocation());
			
			//get parent of parent node
			BinaryNode parentParent1 = findParent(r, parent.getData().getLocation());
			
			//if node is not the root
			if (node.getData() != this.root.getData() && node.getData() != parent.getData())
			{
				//since the right node is a leaf, find the parent of this pixel which is the successor
				while (parent.getData() != null && parent.getData().getLocation().compareTo(node.getData().getLocation()) != 1)
				{
					node =  parent;
					parent = findParent(r, parent.getData().getLocation());
				}
				//return the successor
				return parent.getData();
			}
		}
		//node is the root
		else if (node.getData().getLocation() == this.root.getData().getLocation())	
		{
			try 
			{
				//find smallest pixel on the right side of the root to switch with
				Pixel newRoot = smallest(r.getRight());
				//set root equal to value of smallest pixel
				node.setData(newRoot);
				//clear node containing smallest pixel
				BinaryNode smallestParent = findParent(r, newRoot.getLocation());
				//set data of the parent of the smallest pixel to null
				//the left is where the smallest pixel would have been
				smallestParent.getLeft().setData(null);
				
				return node.getData();
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
		//if node is an internal node
		return null;
	}

	/**
	 * @param r is a binary node
	 * @param key the location of the pixel
	 * 
	 * @return returns the predecessor of the node r
	 */
	public Pixel predecessor(BinaryNode r, Location key) 
	{
		BinaryNode node = getNode(r, key);
		
		//if r is an internal node and r.left is an internal node
		if (!node.isLeaf() && !node.getLeft().isLeaf())
		{
			try 
			{
				return largest(node.getLeft());
			} 
			catch (EmptyTreeException e) 
			{
				System.out.println(e);
			}
		}
		else
		{
			//get parent of node which contains key
			BinaryNode parent = findParent(r, key);
			//get parent of parent node
			BinaryNode parentParent = findParent(r, parent.getData().getLocation());
			//keep searching through parents until a predecessor is found
			while (parent != null && parent.getLeft() == node)
			{
				node =  parent;
				parent = parentParent;
			}
			
			if(parent == null)
			{
				return null;
			}
			else
			{
				return parent.getData();
			}
		}
		return null;
	}

	/**
	 * @param r is a binary node
	 * 
	 * @return returns the smallest pixel in relation to r
	 */
	public Pixel smallest(BinaryNode r) throws EmptyTreeException 
	{
		if (r.isLeaf())
		{
			throw new EmptyTreeException();
		}
		
		//if left child has no data (is a leaf) then return the data that's in this node
		if (r.isLeaf())
		{
			return null;
		}
		else
		{
			BinaryNode p = r;
			//non recursive way of traversing left until the smallest pixel is found
			while (!p.getLeft().isLeaf())
			{
				p = p.getLeft();
			}
			
			return p.getData();
		}
	}
	
	/**
	 * @param r is a binary node
	 * 
	 * @return returns the largest pixel in relation to r
	 */
	public Pixel largest(BinaryNode r) throws EmptyTreeException 
	{
		if (r.isLeaf())
		{
			throw new EmptyTreeException();
		}
		//if left child has no data (is a leaf) then return the data that's in this node
		if (r.isLeaf())
		{
			return null;
		}
		else
		{
			BinaryNode p = r;
			//non recursive way of traversing right until the largest pixel is found
			while (!p.getRight().isLeaf())
			{
				p = p.getRight();
			}
			
			return p.getData();
		}
	}
}
