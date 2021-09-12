/* Node.java
 * Purpose: Contans methods, constructors, and variables for nodes (used in PriorityQ)
 * Creator: Shi Han Qin 
 * Date: 2019-04-01
 */
class Node<E> { 
  //Variable declaration
  private E item;
  private Node<E> next;
  private Node<E> prev;
  private int priority;
  
  /** 
   * Node
   * Constructor that makes a node
   */
  public Node(){
    this.item = null;
    this.next = null;
    this.prev = null;
  }
  
  /** 
   * Node
   * Constructor that makes a node with an item in it
   * @param item, a generic item to be stored in the node
   */
  public Node(E item) {
    this.item = item;
    this.next = null;
    this.prev = null;
  }
  
  /** 
   * Node
   * Constructor that makes a node with an item and its corresponding priority
   * @param item, a generic item to be stored in the node
   * @param priority, the item's priority
   */
  public Node(E item, int priority) {
    this.item = item;
    this.priority = priority;
    this.next = null;
    this.prev = null;
  }
  
  /** 
   * Node
   * Constructor that makes a node with an item and its next reference node
   * @param item, a generic item to be stored in the node
   * @param next, the next node
   */
  public Node(E item, Node<E> next) {
    this.item = item;
    this.next = next;
  }
  
  /** 
   * getNext
   * Gets the next node
   * @return next, the next node
   */
  public Node<E> getNext(){
    return this.next;
  }
  
  /** 
   * getPrev
   * Gets the previous node
   * @return prev, the previous node
   */
  public Node<E> getPrev(){
    return this.prev;
  }
  
  /** 
   * setNext
   * Sets the next node
   * @param next, the next node
   */
  public void setNext(Node<E> next){
    this.next = next;
  }
  
  /** 
   * setPrev
   * Sets the previous node
   * @param prev, the previous node
   */
  public void setPrev(Node<E> prev){
    this.prev = prev;
  }
  
  /** 
   * getItem
   * Gets the item in the node
   * @return item, the item in the node
   */
  public E getItem(){
    return this.item;
  }
  
  /** 
   * getPriority
   * Gets the priority of the node
   * @return priority, the integer priority of the node
   */
  public int getPriority(){
    return this.priority;
  }
  
  /** 
   * setPriority
   * Sets the priority of the node
   * @param priority, the priority of the node
   */
  public void setPriority(int priority){
    this.priority = priority;
  }
} //end of Node class
