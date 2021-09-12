/* Compression.java
 * Purpose: Compresses a file into an MZIP file using Huffman coding
 * Creators: Shi Han Qin 
 * Date: 2019-04-01
 */

//Imports
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Compression<E> {
  public static void main(String[] args) throws IOException {
    //Variable initialization
    Scanner input = new Scanner(System.in);
    String file, fileExtension, fileName;
    String tree;
    String encoded;  
    int extraBits;
    int [] frequencyArray = new int[256];
    PriorityQ<BinaryTreeNode<Byte>> queue = new PriorityQ<BinaryTreeNode<Byte>>();
    BinaryTreeNode<Byte> huffTree = new BinaryTreeNode<Byte>();
    Compression<String> compression = new Compression<String>();
    boolean valid = true;
    
    while(valid){
      try{
        //Ask for the file user wants to compress
        System.out.println("Please enter the name of the text file, without its extension");
        file = input.nextLine();
        System.out.println("Please enter the file's extension (e.g .txt)");
        fileExtension = input.nextLine();
        fileName = file + fileExtension;
        
        //Assign frequency array      
        frequencyArray = compression.makeFrequencyArray(fileName);
        
        //Make priority queue
        queue = compression.makeQueue(frequencyArray);
        
        //Build the tree
        huffTree = compression.buildTree(queue);
        
        //Print tree
        tree = compression.printTree(frequencyArray);
        
        //Print encoded
        encoded = compression.printEncoded(huffTree, fileName);
        
        //Find bits leftover
        extraBits = compression.extraBits(encoded);
        
        //Add 0s to make whole byte
        encoded = compression.makeWholeByte(encoded, extraBits);
        
        //Print all necessary info to MZIP file
        compression.outToFile(file, fileExtension, tree, encoded, extraBits); 
        
        valid = false;
      } catch (FileNotFoundException e){
        System.out.println("The file name you entered was not valid");
        
      } 
    }
  }//end of main
  
  
  /** 
   * makeFrequencyArray
   * Creates an int array of 0 to 255; the frequency of the character is stored in corresponding to the index of the array 
   * @param fileName, the name of the file to compress
   * @return frequency, an integer array with frequency values of the different characters 
   */
  int[] makeFrequencyArray(String fileName) throws IOException{
    InputStream inStream = null;
    BufferedInputStream in = null; 
    //Array of 0 to 255; each index corresponds to a different character
    int [] frequency = new int[256];
    try {
      inStream = new FileInputStream(fileName); //Read the file
      in = new BufferedInputStream(inStream);
      int c;
      
      //Initialize all frequencies as 0s
      for (int i = 0; i<256; i++){
        frequency[i] = 0;
      }
      
      //Find frequencies of the data
      while ((c = in.available()) >0) {
        c = in.read();
        for (int i = 0; i<256; i++){
          if (c == i){
            frequency[i]++;
          }
        }
      }
    } finally {
      if (inStream != null) { //Close the stream
        inStream.close();
      } 
      if (in !=null){
        in.close();
      }
    }
    return frequency;
  }
  
  /** 
   * makeQueue
   * Creates a priority queue of BinaryTreeNode<Byte>, they are all leaf nodes 
   * @param array, an integer array that has the frequecy of each character stored
   * @return queue, a priority queue of BinaryTreeNodes of type Byte, the lowest frequencies have highest priority 
   */
  PriorityQ<BinaryTreeNode<Byte>> makeQueue(int[] array){
    PriorityQ<BinaryTreeNode<Byte>> queue = new PriorityQ<BinaryTreeNode<Byte>>();
    
    for (int i=0;i<256;i++){
      if (array[i] != 0){ //Make sure the character actually exists in the data
        BinaryTreeNode<Byte> newNode = new BinaryTreeNode<Byte>((byte)(i),array[i]); //Create a new BinaryTreeNode<Byte> that stores the character code
        queue.enqueue(newNode,array[i]); //Add to queue                              //and the frequency of it as the priority
      }
    }
    return queue;
  }
  
  /** 
   * buildTree
   * Creates a huffman tree of the data by removing two nodes of lowest frequency from the queue and joining them until nothing is left
   * @param queue, the priority queue of BinaryTreeNodes that will form the tree
   * @return joined, a BinaryTreeNode that is the root of the huffman tree 
   */
  BinaryTreeNode<Byte> buildTree(PriorityQ<BinaryTreeNode<Byte>> queue){
    //joined is a new node that is created every time two items are dequeued
    BinaryTreeNode<Byte> joined = new BinaryTreeNode<Byte>();
    while(!queue.isEmpty()){
      BinaryTreeNode<Byte> temp1 = new BinaryTreeNode<Byte>(); //First dequeued item
      BinaryTreeNode<Byte> temp2 = new BinaryTreeNode<Byte>(); //Second dequeued item
      temp1 = queue.dequeue().getItem();
      if(queue.isEmpty()){ //if there was only one node left in the queue then it is already the root of the tree
        return temp1;
      } else { 
        temp2 = queue.dequeue().getItem();
        joined = merge(temp1,temp2); //merge method called to join the two dequeued items
        joined.setPriority(temp1.getPriority() + temp2.getPriority()); //Reset to add the new priority of the node
        queue.enqueue(joined,temp1.getPriority()+temp2.getPriority()); //Enqueue the joined item again with the added frequencies
      }
    }
    return joined;
  }
  
  /** 
   * merge
   * Creates a parent BinaryTreeNode and sets the inputs into the methods as its two children
   * @param left, the first BinaryTreeNode to be merged together
   * @param right, the second BinaryTreeNode to be merged together
   * @return merged, a BinaryTreeNode that has merged the two inputs to the method 
   */
  BinaryTreeNode<Byte> merge(BinaryTreeNode<Byte> left, BinaryTreeNode<Byte> right){
    BinaryTreeNode<Byte> merged = new BinaryTreeNode<Byte>(null,left,right); //item in node is null, left and right children are set
    return merged;
  }
  
  /** 
   * printEncoded
   * Print the encoded data, now a series of 1s and 0s 
   * @param root, the root of the huffman tree created
   * @param fileName, the name of the file to be encoded
   * @return encoded, the data in a series of 1s and 0s
   */  
  String printEncoded(BinaryTreeNode<Byte> root, String fileName) throws IOException{
    FileInputStream in = null;
    StringBuffer sb = new StringBuffer();
    String encoded;
    
    try {
      in = new FileInputStream(fileName); //read the original file
      int c;
      
      if (root == null){ //check if there is anything in the file
        return null;
      }
      
      while ((c = in.read()) != -1) {
        sb.append(encode(root,(byte)(c))) ;
      }  
    } finally {
      if (in != null) {
        in.close();
      } 
    }
    encoded = sb.toString();
    return encoded;
  }
  
  /** 
   * encode
   * Adds the corresponding 1s and 0s
   * @param root, the root of the huffman tree created 
   * @param item, the byte items in the node are being compared to
   * @return returnCode, a string of the encoded data
   */  
  public String encode(BinaryTreeNode<Byte> root, byte item) {
    String returnCode;
    if ((root == null) || (root.getItem() != null)){ //Check whether or not there is data
      return null;
    }
    if ((root.getLeft() != null) || (root.getRight() != null)){ //Runs as long as there is a next item
      if ((root.getLeft().getItem() != null) && (root.getLeft().getItem() == item)) { //If the left leaf node is reached return 0
        return "0";
      } else if ((root.getRight().getItem() != null) && (root.getRight().getItem() == item)) { //If the left leaf node is reached return 0
        return "1";
      } else if ((root.getItem()== null) && (root.getLeft() != null)) {
        returnCode = encode(root.getLeft(), item); //Keep checking for item match down left branch
        if (returnCode != null) {
          return "0" + returnCode;
        } else if ((root.getItem()== null) && (root.getRight() != null)) {
          returnCode = encode(root.getRight(), item); //Keep checking for item match down right branch
          if (returnCode != null){
            return "1" + returnCode;
          }
        }
      }
    }
    return null;
  }
  
  /** 
   * printTree
   * Print the huffman tree on one line 
   * @param array, the original array of frequencies corresponding to each character
   * @return tree, a String with the huffman tree printed on one line
   */    
  String printTree(int[] array){
    String tree = " ";
    PriorityQ<String> queue = new PriorityQ<String>();
    for (int i=0;i<256;i++){
      if (array[i] != 0){ //Make sure the character actually exists in the data
        queue.enqueue(Integer.toString(i),array[i]);                           
      }
    }
    while(!queue.isEmpty()){
      Node<String> node1 = queue.dequeue(); //Take first item out of node
      if(queue.isEmpty()){ //If there is nothing left in queue return the first item taken out
        tree = node1.getItem(); //Turn into string
        return tree;
      }
      else{
        Node<String> node2 = queue.dequeue(); //Take second
        tree = "(" + node1.getItem() + " " + node2.getItem() + ")";
        queue.enqueue(tree, node1.getPriority() + node2.getPriority());
      }
    }
    return tree.substring(1);
  }
  
  /** 
   * extraBits
   * Finds the number of extra bits needed to make the data whole bytes
   * @param encoded, the encoded data that may need extra bits
   * @return extra, the number of extra bits needed 
   */
  int extraBits(String encoded){
    int extra = 8 - encoded.length() % 8;
    if (extra == 8){
      return 0;
    }
    return extra;
  }
  
  /** 
   * makeWholeByte
   * Makes the encoded data to take up a whole number of bytes
   * @param encoded, the encoded data that may need extra bits
   * @param extraBits, the number of extra bits needed
   * @return encoded, the new data with added 0s to make a whole byte; 
   */
  String makeWholeByte(String encoded, int extraBits){
    for (int i=0; i<extraBits; i++){
      encoded = encoded + "0";
    }
    return encoded;
  }
  
  /** 
   * outToFile
   * Makes the MZIP file with the compressed data
   * @param file, the original file name of what was to be compressed
   * @param fileExtension, the file extension of the original file
   * @param tree, the huffman tree on a single line
   * @param encoded, the encoded compressed data
   * @param extraBits, the number of extra bits that was added onto the end of the data
   */
  void outToFile(String file, String fileExtension, String tree, String encoded, int extraBits) throws IOException{ 
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file+".MZIP"));
    
    try{
      
      //Write the file name
      for (int i =0; i<file.length(); i++){
        stream.write(file.charAt(i));
      }
      fileExtension = fileExtension.toUpperCase();
      for (int i = 0; i<fileExtension.length(); i++){
        stream.write(fileExtension.charAt(i));
      }
      if(encoded != null){ //If file has data, proceed to print the lines out
        //New line
        stream.write(13);
        stream.write(10);
        //Write the huffman tree on one line
        for (int i = 0; i<tree.length(); i++){
          stream.write(tree.charAt(i));
        }
        //New line
        stream.write(13);
        stream.write(10);
        //Write the leftover bits
        String extra = " " + extraBits;
        for(int i=1; i<extra.length();i++){
          stream.write(extra.charAt(i));
        }
        //New line
        stream.write(13);
        stream.write(10);
        //Write the encoded data
        int times;
        times = encoded.length()/8;
        for (int i=0;i<times;i++){
          stream.write(Integer.parseInt(encoded.substring(0,8),2));
          encoded = encoded.substring(8);
        }
      }
    } finally{
      if (stream != null) {
        stream.close();
      }
    }
  }
}//end of Compression class