package assignment1;

/*
 * @author Ahmed Mohamed (aa14zu)
 * Sept 21, 2018 COSC 2P03 A1
 * Node wrapper class for linked list
 */

public class Node {
    
    public char item;
    public Node next;
    
    public Node ( char item, Node node ){
       
        this.item = item;
        next = node;
        
    }  //constructor
    
} //Node
