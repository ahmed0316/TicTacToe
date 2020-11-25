package assignment1;

import BasicIO.*;
/*
 * @author Ahmed Mohamed (aa14zu)
 * September 21, 2018. COSC 2P03 A1.
 * Ciphers plain text and outputs it
 */

public class Cipher {
    private Node list;          //head of list
    private Node tailTemp;      //temporary tail reference
    private ASCIIOutputFile output;

    public Cipher ( char[] plainText, int permutation ){
        
        /**               Create linked list               **/
        list = new Node( plainText[plainText.length-1] , null );//Make last node
        tailTemp = list;                                   //Temp tail reference
        
        output = new ASCIIOutputFile();                     //Set up output file
        output.writeString("Your new ciphered text is: ");  //Description
        
        /**        Add nodes to the list          **/
        for ( int i = 1 ; i<plainText.length ; i++ ){ //Add nodes(reverse order)
            list = new Node(plainText[plainText.length - i - 1], list); 
        } //list now exists
         
        tailTemp.next = list;                              //Make list circular
        tailTemp = null;                                   //Garbage
        
        /**Cipher using i (permutation)**/
        Node q, s;
        q = list;
        s = null;
        
        while (list != null){
            for ( int j = 1 ; j < permutation ; j++ ){
                s = q;
                q = q.next;
            }
            output.writeC(q.item);
            if (q.next != s.next){ //If not last node(1 node pointing to itself)
                q = q.next;
                s.next = q;        //Then remove node
            }
            else{
                list = null;       //Otherwise make the list null
            }
        }
        
       output.close();             //Close and save the ASCII Output File   
       
    } //Constructor
    
} //Cipher