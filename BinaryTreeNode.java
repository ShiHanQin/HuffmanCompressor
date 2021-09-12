/* BinaryTreeNode.java
 * Purpose: Contans methods, constructors, and variables for binary tree nodes
 * Creator: Shi Han Qin 
 * Date: 2019-04-1
 */

class BinaryTreeNode<E>{
  //Variables
  private E item;
  private BinaryTreeNode<E> left;
  private BinaryTreeNode<E> right;
  private int priority;
  
  /** 
   * BinaryTreeNode
   * Construtor that makes a binary tree node 
   */  
  BinaryTreeNode(){
    this.item = null; 
    this.left = null;
    this.right = null;
  }
  
  /** 
   * BinaryTreeNode
   * Construtor that makes a binary tree node with an item and two child nodes
   * @param item, a generic item the node will store 
   */
  public BinaryTreeNode (E item){
    this.item = item;
    this.left = null;
    this.right = null;
  }
  
  /** 
   * BinaryTreeNode
   * Construtor that makes a binary tree node with an item 
   * @param item, a generic item the node will store 
   * @param left, the left child of the current node
   * @param right, the right child of the current node
   */   
  public BinaryTreeNode (E item, BinaryTreeNode<E> left, BinaryTreeNode<E> right){
    this.item = item;
    this.left = left;
    this.right = right;
  }
  
  /** 
   * BinaryTreeNode
   * Construtor that makes a binary tree node with an item and its priority
   * @param item, a generic item the node will store 
   * @param priority, the priority of the item
   */    
  public BinaryTreeNode(E item, int priority){
    this.item = item;
    this.left = null;
    this.right = null;
    this.priority = priority;
  }
  
  /** 
   * getLeft
   * Gets the left child of the current node
   * @return left, the left child binary tree node 
   */ 
  public BinaryTreeNode<E> getLeft(){
    return this.left;
  }
  
  /** 
   * getRight
   * Gets the right child of the current node
   * @return right, the right child binary tree node 
   */ 
  public BinaryTreeNode<E> getRight(){
    return this.right;
  }
  
  /** 
   * setLeft
   * Sets the left child of the current node
   */ 
  public void setLeft(BinaryTreeNode<E> left){
    this.left = left;
  }
  
  /** 
   * setRight
   * Sets the right child of the current node
   */
  public void setRight(BinaryTreeNode<E> right){
    this.right = right;
  }
  
  /** 
   * getItem
   * Gets the item stored in the node
   * @return item, the item stored in the node
   */
  public E getItem(){
    return this.item;
  }
  
  /** 
   * setItem
   * Sets the item in the node
   * @param item, an item of type E to be assigned into the node
   */
  public void setItem(E item){
    this.item = item;
  }
  
  /** 
   * setPriority
   * Sets the priority of the node
   * @param priority, an integer representing the object's priority
   */
  public void setPriority(int priority){
    this.priority = priority;
  }
  
  /** 
   * getPriority
   * Gets the priority of the node
   * @return priority, the integer priority of the node
   */
  public int getPriority(){
    return priority;
  }
  
  /** 
   * isLeaf
   * Determines whether or not the node is a leaf node
   * @return Boolean, true if it is a leaf node, false if it is not one
   */
  public boolean isLeaf(){
    if ((left == null) && (right == null)){
      return true;
    }
    else{
      return false;
    } 
  }
}//end of class BinaryTreeNode