package assignment2;

import BasicIO.*; //Brock library
/*
 * @author Ahmed Mohamed (aa14zu)
 * Oct 18, 2018. COSC 2P03 A2.
 * The main Tic Tac Toe class
 */

public class TicTacToe {
    private BasicForm out;          //GUI Display
    private int button;             //GUI Button
    private Node root;        //root of tree
    private int turn;               //turn number
    private boolean won;            //game is won?
    private boolean youWon;         //human won (X)
    private boolean youLost;        //Computer won (O)
    private boolean running;        //program is running?
    private boolean isFull;         //gameboard is full of values?
    private int count;              //recursion counter
    private Tree tree;              //General tree
    char[] blankTable = new char[9];

    public TicTacToe (){
        out = new BasicForm("Next","Restart","Quit");//initialize form
        setUpGUI();                    
        turn = 0;
        won = false;
        youWon = false;
        youLost = false;
        running = true;
        out.writeString("won"," No");
        for (int k = 0 ; k < blankTable.length ; k++){
           blankTable[k] = 'e';        //e for empty
        }
        root = new Node(blankTable);
        count = 0;
        tree = new Tree();
        tree.root = root;
        root = tree.root;
        createLeftMostChildren(root,0);
        //tree.displayFarLeftMostChildren();
        createAllChildren( root );
        //tree.displaySiblings(root, root.leftMostChild);
        tree.displayChildren(root);
        
        while(running == true) {
            button = out.accept();
            
            if (button == 0 && won == false){
                removeEditable();       //removes editablility of used up spots
                turn++;
                out.writeInt("turn",turn);
                won = checkIfWon();     //someone won
                isFull = checkIfFull(); //gameboard is full of values
            }  //Game is ongoing and next is clicked
            
            if ( button == 0 && isFull && won==false ) {
                turn++; //turn counter
                out.writeInt("turn",turn); //write to output
                out.writeString("won","No Winner");
                out.writeString("msg","Play Again?");
            } //No winner: No one won, and board is full. 
            
            if (button == 0 && won == true){  //Next clicked and game won
                endGame();
                if (youWon == true) out.writeString("won","You Won");
                if (youLost == true) out.writeString("won","You Lost");
                out.writeString("msg","Play Again?");
            }
            
            if (button == 1){ //Restart (Play again) clicked
                reset();
            }
            
            if (button == 2){ //Quit clicked or game won
                out.close();
                running = false;
            }  
            
        }
    } //Constructor
    
    private void setUpGUI(){
        
      out.setTitle("Tic Tac Toe");
      out.addTextField("turn", "Turn: ", 3);
      out.setEditable("turn", false);
      out.newLine();
      out.addTextField("won","Won? :", 12);
      out.setEditable("won", false);
      out.addTextField("msg", "Message: ", 12);
      out.setEditable("msg", false);
      out.writeString("msg","Game Ongoing");
      out.addTextField("0",1,20,100);
      out.addTextField("1",1,40,100);
      out.addTextField("2",1,60,100);
      out.addTextField("3",1,20,120);
      out.addTextField("4",1,40,120);
      out.addTextField("5",1,60,120);
      out.addTextField("6",1,20,140);
      out.addTextField("7",1,40,140);
      out.addTextField("8",1,60,140);
      
    } //setUp
    
    private void removeEditable(){
        char temp;
        for ( int i = 0 ; i < 9 ; i++ ) {
            temp = out.readChar( Integer.toString(i) );
            if (temp == 'O' || temp == 'X' || temp == 'x' || temp == 'o' ){
                out.setEditable(Integer.toString(i), false);
            }
        }
    } //removeEditable
    //sets editability to false if a space on the board is taken up
    
    private boolean checkIfWon(){
        char v1, v2, v3;
        
        /* 1 and only diagonal check possible:          */
             v1 = out.readChar ( Integer.toString(0) );    //Diagonal check x
             v2 = out.readChar ( Integer.toString(4) );              //x 
             v3 = out.readChar ( Integer.toString(8) );   
             if ( v1 == v2 && v2 == v3 && v1!='e' && (v1=='X' || v1=='x')
                     && out.successful() ){ //X won
                 youWon = true;
                 return true;
             }
             else if ( v1 == v2 && v2 == v3 && v1!='e' && (v1=='O' || v1=='o')
                     && out.successful() ){ //O won
                 youLost = true;
                 return true;
             }
        
         for ( int i = 0 ; i < 7 ; i++ ) {
             
             v1 = out.readChar ( Integer.toString(i) );    //Horizontal check
             v2 = out.readChar ( Integer.toString(i + 1) );  //(xxx/ooo)
             try {
                 v3 = out.readChar ( Integer.toString(i + 2) );
             } catch (ComponentNotFoundException e) { };
             if ( v1 == v2 && v2 == v3 && v1!='e' && (v1=='X' || v1=='x')
                     && out.successful() ){ //X won
                 youWon = true;     //X won
                 return true;       //Someone won
             }
             else if ( v1 == v2 && v2 == v3 && v1!='e' && (v1=='O' || v1=='o')
                     && out.successful() ){ //O won
                 youLost = true;    //O won
                 return true;       //Someone won
             }             
             
             v1 = out.readChar ( Integer.toString(i) );    //Vertical check x/o
             try {                                                        //x/o
                v2 = out.readChar ( Integer.toString(i + 3) );            //x/o      
             } catch (ComponentNotFoundException e) { };
             if ( out.successful() ){
                try {
                    v3 = out.readChar ( Integer.toString(i + 6) );  
                } catch (ComponentNotFoundException e) { };
                if ( v1 == v2 && v2 == v3 && v1!='e' && (v1=='X' || v1=='x')
                         && out.successful() ){ //X won
                     youWon = true;
                     return true;
                }
                else if ( v1 == v2 && v2 == v3 && v1!='e' && (v1=='O' || v1=='o')
                         && out.successful() ){ //O won
                    youLost = true;
                    return true;
                }            
             } 
         }
         return false;  //no one won
    }
    
    private boolean checkIfFull(){
            
            char temp;
            for ( int i = 0 ; i < 9 ; i++ ) {
            temp = out.readChar( Integer.toString(i) );
            if (temp != 'O' && temp != 'X' && temp != 'x' && temp != 'o' ){
                return false;
            }   //There is an empty square then return false (not end of game)
        }
            return true;
            
    } //checkIfFull. Checks if the board is full of values.
            
    
    private void reset(){       
        out.close();
        TicTacToe ttt = new TicTacToe();  
    } //reset. Restarts the game to a new game.
    
    
    private void endGame(){
        turn++;
        out.writeInt("turn",turn);
        char temp;
        for ( int i = 0 ; i < 9 ; i++ ) {
            out.setEditable(Integer.toString(i), false);
        }
    } //endGame.
    //Sets whole board to non-editable and tells user that the game is over.

    
    /*private void createLeftMostChildren( Node parent, int childNum ){
        
        if (childNum < 9){
            char[] childData = new char[9];
        
            for ( int j = 0 ; j < 9 ; j++){ //create empty board data
                childData[j] = 'e';
            }
            
            for (int i = 0 ; i <= childNum ; i++){
                if (i % 2 == 0 ){
                    childData[i] = 'X';
                }//add X to board    
                else if ( i % 2 != 0 ) {
                    childData[i] = 'O';
                }//add O to board
            }
            
            parent.leftMostChild = new Node( childData );//create leftmost child
            parent.children[0] = parent.leftMostChild;   //add to child array
            parent = parent.leftMostChild; 
            childNum++;      //increment where 'X' is and move towards base case
            createLeftMostChildren( parent, childNum ); //recursion
        }
        
    } //createFarLeftMostChildren (far left children to get started)*/
    
    private void createLeftMostChildren( Node parent, int childNum ){
        
        int location = 10;   //location to add an X or O
        
        if ( childNum < 9 && parent != null){
            char[] childData = new char[9];
        
            for ( int j = 0 ; j < 9 ; j++){ //copy parent's board
                childData[j] = parent.data[j];
            }
            
            for ( int i = childNum ; i < 9 ; i++ ){
             if ( childData[i] == 'e' ) {
                location = i;                    
                break;
             }
            }/*Finds where the next available empty spot is.
            Starts looking from the childNum to the end. */
                if (location < 10) {
                    if ( childNum % 2 == 0 ){
                        childData[location] = 'X';
                    }//add X to board    
                    else if ( childNum % 2 != 0 ) {
                        childData[location] = 'O';
                    }//add O to board
                
            
            
            parent.leftMostChild = new Node( childData );//create leftmost child
            parent.children[0] = parent.leftMostChild;   //add to child array
            parent = parent.leftMostChild; 
            childNum++;      //increment where 'X' is and move towards base case
            createLeftMostChildren( parent, childNum ); //recursion
                }
        }
        
    } //createFarLeftMostChildren (far left children to get started)    
    
    
    /*Create children of a parent. Start w/ leftmost node as first sibling.*/
    private void createChildren( Node parent, Node aSibling, int count ){
        
        Node child;
        boolean X = false;       //true if moving an X. false if moving O.
        int newValIndex = 10;         //left most child's new val index (from parent)
        int nextEIndex = 10;     //the next available empty('e') spot in the sibling
        char[] childData = new char[9];
        
        for ( int k = 0 ; k < 9 ; k++ ){
            /** leftMost child spot is different that parent data **/
            if ( aSibling.data[k] != parent.data[k] && parent.data[k] == 'e' ){
                newValIndex = k;
                if ( aSibling.data[k] == 'X' ) X = true;
                if ( aSibling.data[k] == 'O' ) X = false;
            }
            childData[k] = aSibling.data[k];    //childData = to its sibling's
        } //find where the new X or O was from parent to child's sibling
        //Child data set to equal its sibling's data here to save space.
        
        for ( int m = count ; m < 9 ; m++ ){
            if ( aSibling.data[m] == 'e' ) {
                nextEIndex = m;                    
                break;
           }
       }/*Finds where the next available empty spot is.
        Starts looking from the new val to the end. */
        
        if ( newValIndex < 10 && nextEIndex < 10 ) {
       /* Check that not at end of tree */
            
            childData[newValIndex] = 'e';
            if (X == true) childData[nextEIndex] = 'X';
            if (X == false) childData[nextEIndex] = 'O';
            child = new Node(childData);
            int siblingChildNum = tree.returnChildNumber(parent, aSibling);
            parent.children[siblingChildNum + 1] = child;
            aSibling.sibling = child;
            count++;
            createChildren( parent, child, count);         //recursion      
        }
    } //createChildren
    
    
    public void createAllChildren( Node parent){
            
        Node child;
        
        if ( parent != null ){
        
            createLeftMostChildren(parent, 0);  //parent's leftmost children
            Node firstSibling = parent.leftMostChild; 
            createChildren( parent, firstSibling, 0 );//rest of parent's children
            int numChildren = tree.returnNumberChildren(parent);
            
            for ( int i = 0 ; i < numChildren ; i++ ){//for each parent's child
                child = parent.children[0];
                createLeftMostChildren(child, 0);       //create leftmost children
                firstSibling = parent.leftMostChild;
                createChildren ( child, firstSibling , 0); //create children
               }
            }
        }
           
    //} //createAllChildren
    
  
    public static void main (String args[]) {
        TicTacToe ttt = new TicTacToe();
    } //main
    
} //TicTacToe