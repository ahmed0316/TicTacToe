/*
 * @author Ahmed Mohamed (aa14zu)
 * Oct 18, 2018 COSC 2P03 A2
 * General Tree Class
 */
package assignment2;

import BasicIO.*;

public class Tree {
    
    Node root;

public char[] visit (Node treeNode){
    return treeNode.data;
} //visit

public void setData( char[] info, Node treeNode ){
    treeNode.data = info;
} //setData

public void preOrderRemoval (Node root){ //called root b/c we start w/ root
    
    char[] info;                    //Nodes data
    boolean win;                    //if node is a Win
    
    if (root != null){              //If not at end of tree
        info = visit(root);         //Get node's data
        win = checkIfWon(info);     //Check if node is a Win
        if ( !win ) {               //if a loss
            removeSubtree( root );  //Remove the node's subtree
        }
        preOrderRemoval( root.nextChild() ); //Traverse to next child
        while ( root.sibling!= null ){ 
            preOrderRemoval(root.sibling);   //Checks all siblngs
        }
    }
    
} //remove unneccsary nodes

 private boolean checkIfWon( char[] d ){ //Node's data is parameter
        
     boolean win;
     char v1, v2, v3;
     
        /* 1 and only diagonal check possible:          */
             v1 = d[0];    //Diagonal check x
             v2 = d[4];                     //x 
             v3 = d[8];                       //x
             if ( v1 == v2 && v2 == v3 && v1!='e'){ //Someone won
                 return true;
             }
             
             for (int i = 0 ; i < 9 ; i = i + 3){ //Horizontal check
             v1 = d[i];    
             v2 = d[i+1];
             v3 = d[i+2];
             if ( v1 == v2 && v2 == v3 && v1!='e'){
                 return true;       //Someone won
              }
             }
                 
             for ( int i = 0 ; i < 3 ; i++ ){ //Vertical Check
             v1 = d[i];
             v2 = d[i+3];
             v3 = d[i+6];
             if ( v1 == v2 && v2 == v3 && v1!='e'){
                 return true;       //Someone won
              }
             }

         return false;  //No one won
    } //checkIfWon
 
 public void removeSubtree( Node aNode ){
     
     Node[] empty = new Node[9]; //Sets children to null.
     aNode.children = empty;     //Children no longer being pointed to.
     
 } //Removes the subtree of a node, by setting children to null.
 
    
 public int returnChildNumber ( Node parent, Node child ){
     
     for ( int i = 0 ; i < 9 ; i++ ){
         if (parent.children[i] == child) return i;
     }
     
     return 10;
     
 } //returnChildNumber (ie first child (0) of parent, 2nd (1), etc)
 
 public int returnNumberChildren ( Node aParent ){
     int sum = 0;
     for ( int i = 0 ; i < 9 ; i++ ){
         if (aParent.children[i] != null ) sum++;
     }
     return sum;
 } //returnNumberChildren
 
public void displayFarLeftMostChildren(){
      Node temp;
      temp = root.leftMostChild;
      for ( int i = 0 ; i < 9 ; i++ ){
        System.out.println(this.visit(temp));
        temp = temp.leftMostChild;
      }
    } //displayLeftMostChildren

public void displayChildren (Node parent){
    int num = this.returnNumberChildren(parent);
    for ( int i = 0 ; i < num ; i++){
        System.out.println(parent.children[i].data);

   }
} //displayChildren

public void displaySiblings (Node parent, Node node){
      //  Node temp = parent.leftMostChild;
        int num = this.returnNumberChildren(parent);
        for ( int i = 0 ; i < num ; i++){
            System.out.println(node.data);
            node = node.sibling;    
        }
} //displaySiblings


}


