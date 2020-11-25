package assignment2;

/*
 * @author Ahmed Mohamed (aa14zu)
 * Oct 18, 2018 COSC 2P03 A2
 * Node wrapper class for a node of the general tree
 */

public class Node {
    
    public Node leftMostChild;                     //left most child (1)
    public Node[] children;                        //rest of children (up to 9)
    public char[] data;                            //tic tac toe state
    public Node sibling;
    public int childCount = 0;
    
    public Node( char[] info ){
     data = info;
     leftMostChild = null;
     sibling = null;
     children = new Node[9];
    } //Constructor
    
    public Node nextChild(){
        if (childCount > 9) childCount = 0;
        return children[childCount];
    }
    
    public void resetChildCount(){
        childCount = 0;
    }
    
        
    
    
    /*public void setChildren( Node[] theChildren ){
        int count = 0;
            for ( Node child : theChildren ){
                children[count] = child;
                count++;
        }
    } //setChildren*/
    
/*    public Node getSibling(){
        return sibling;
    } //getSibling
    
    public void setSibling(Node sibling){
        this.sibling = sibling;
    } //setSibling
    */
} //Node