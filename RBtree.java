package program2cs232;


//got most of the methods from the book

public class RBtree<Key extends Comparable<Key>, Value> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	private Node root;  //root of the BST
	private class Node{
		private Key key;  //key of the tree
		private Value val;  //value of the tree
		private Node left, right;
		private boolean color; //color of the nodes
		private int size;
		
		public Node(Key key, Value val, boolean color, int size) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = size;
		}
	}
	
	public RBtree() {  //Initializes an empty symbol table
	}
	
	private boolean isRed(Node x) {  //checks if a Node is red
		if(x == null) {
			return false;
		}
		return x.color == RED;
	}
	
	public int size() {  //gives the size of the tree
		return size(root);
	}
	private int size(Node x) {
		if(x == null) {
			return 0;
		}
		return x.size;
	}
	
	public boolean isEmpty() { //checks if the tree is empty
		return root == null;
	}
	
	public void put(Key key, Value val) {  //puts in a key and value into the tree
		if(key == null) {
			throw new IllegalArgumentException("The first argument to put() is null");
		}
		if(val == null) {
			return;
		}
		
		root = put(root, key, val);
		root.color = BLACK;  //always makes sure the root is black
	}
	private Node put(Node x, Key key, Value val) {
		if(x == null) {
			return new Node(key,val, RED, 1);
		}
		int cmp = key.compareTo(x.key);  //determining where to put the new node
		if(cmp < 0) {
			x.left = put(x.left, key, val);
		}
		else if(cmp > 0) {
			x.right = put(x.right, key, val);
		}
		else {
			x.val = val;
		}
		//makes sure that the tree is left leaning explaining below
		if(isRed(x.right) && !isRed(x.left)) {
			x = rotateLeft(x);
		}
		if(isRed(x.left) && isRed(x.left.left)) {
			x = rotateRight(x);
		}
		if(isRed(x.left) && isRed(x.right)) {
			flipColors(x);
		}
		
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public Value get(Key key) {  //gets the value of the key in the parameter
		if(key == null) {
			throw new IllegalArgumentException("The argument to get() is null");
		}
		return get(root, key);
	}
	private Value get(Node x, Key key) {  //returns the value for the key in the parameter
		while(x != null) {
			int cmp = key.compareTo(x.key);
			if(cmp < 0) {
				x = x.left;
			}
			else if(cmp > 0) {
				x = x.right;
			}
			else {
				return x.val;
			}
		}
		return null;
	}
	
	public int height() {  //gives the height of the tree
		return height(root);
	}
	private int height(Node x) {
		if(x == null) {
			return -1;
		}
		return 1 + Math.max(height(x.left), height(x.right));
	}
	
	//***************************************************88
	//makes sure the red black tree is formatted correctly
	private Node rotateRight(Node x) {
		Node y = x.left;  //creates a new node to be the original node needing moved
      x.left = y.right;  //changes the original node to be what was on the right side of the parent
      y.right = x;   //makes the original spot into what was on the right of the parent
      y.color = x.color;  //changes the color for the y node 
      x.color = RED;  //assigns the color red to the x node
      y.size = x.size;  //gives the y node the size that x had
      x.size = size(x.left) + size(x.right) + 1; //gives the x node a new size
      return y;
	}
	private Node rotateLeft(Node x) {  //pretty much the rotateRight just swapping sides
		 Node y = x.right;
	     x.right = y.left;
	     y.left = x;
	     y.color = x.color;
	     x.color = RED;
	     y.size = x.size;
	     x.size = size(x.left) + size(x.right) + 1;
	     return y;
	}
	private void flipColors(Node x) {
		x.color = !x.color; //switches the parents color
		x.left.color = !x.left.color; //switches the left child's color
		x.right.color = !x.right.color; //switches the right child's color
	}
	//********************************************
	
	public Iterable<Key> keys() {  //returns all the keys in the tree
      Queue<Key> queue = new Queue<Key>();
      keys(root, queue);
      return queue;
  }
  private void keys(Node x, Queue<Key> queue) {
      if (x == null) return;
      keys(x.left, queue);
      (queue).enqueue(x.key);
      keys(x.right, queue);
  }
  
  //returns smallest key in the red black tree
  public Key min() {
      return min(root).key;
  }
  // the smallest key in subtree rooted at x; null if no such key
  private Node min(Node x) {
      if (x.left == null) {
    	  return x;
      }
      else {
    	  return min(x.left);
      }
  }
  
//returns largest key in the red black tree
  public Key max() {
      return max(root).key;
  }
  // the largest key in the subtree rooted at x; null if no such key
  private Node max(Node x) {
      if (x.right == null) {
    	  return x;
      }
      else {
    	  return max(x.right);
      }
  }
}
