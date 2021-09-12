/* PriorityQ.java
 * Purpose: Contans methods, constructors, and variables for priority queues
 * Creator: Shi Han Qin 
 * Date: 2019-04-01
 */
class PriorityQ <E>{
  //Variable declaration
  private Node<E> head;
  
  /** 
   * PriorityQ
   * Constructor that makes a priority queue
   */
  public PriorityQ(){
    head = null;
  }
  
  /** 
   * enqueue
   * Adds an object into the priority queue
   * @param toAdd, the item to be added to the queue
   * @param priority, the item to be added's corresponding priority
   */
  public void enqueue(E toAdd, int priority){
    Node<E> tempNode = head; //Initialize a temporary node to help traverse
    Node<E> newNode = new Node<E>(toAdd, priority); //Create the new node to be added to the queue
    
    if (head == null){ //If the queue is empty, automatically add the item into be head
      head = newNode;
      return;
    }
    
    if (priority <= tempNode.getPriority()) { //If the priority is smaller than the head, shift everything
      newNode.setNext(tempNode);
      head = newNode;
      return;
    }
    
    while ((tempNode.getNext() != null) && (tempNode.getNext().getPriority() <= priority)) { //Keep searching for right place 
      tempNode = tempNode.getNext();
    }
    newNode.setNext(tempNode.getNext()); //Replace with new node accordingly
    tempNode.setNext(newNode);
  } //end of enqueue method
  
  /** 
   * dequeue
   * Removes item from queue
   * @return toDequeue, the item removed from the queue
   */
  public Node<E> dequeue(){
    if (head!=null){
      Node<E> toDequeue = head; //Head is highest priority, so it needs to be dequeued
      head = head.getNext(); //Items are shifted up accordingly
      return toDequeue;
    } else {
      return null;
    }
  }
  
  /** 
   * size
   * Finds the numbers of items in the queue
   * @return size, the numbers of items in the queue
   */
  public int size(){
    int size = 0;
    Node<E> tempNode = head;
    
    while (tempNode!=null){
      size++; //Increment until empty
      tempNode = tempNode.getNext();
    }
    return size;
  }
  
  /** 
   * isEmpty
   * Checks if there is anything in the queue
   * @return Boolean, true if it is empty, false if it is not
   */
  public boolean isEmpty(){
    if (head == null){
      return true;
    }
    return false;
  }
}