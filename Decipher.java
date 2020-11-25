package assignment1;

import BasicIO.*;
/*
 * @author Ahmed Mohamed (aa14zu)
 * Sept 21, 2018 COSC 2P03 A1
 * Deciphers encrypted text and outputs it. Also main method.
 */
public class Decipher {
    
    private final ASCIIDataFile input;         //IO
    private ASCIIOutputFile output;
    private int cipherLength, firstWordLength; //cipher and 1st word length
    private int i;                             //int i for re-cipher(remove ith)
    private String cipherText, knownWord;      //strings for initial read
    private int[] permutation;                 //array to track permutations
    private char[] plainText;                  //the whole plain text
    private Cipher cipher;                     //instance of cipher
    
    public Decipher() {
        
        input = new ASCIIDataFile();            //gets an input
        
        if ( input.isEOF() == false ){          //ensure file is not empty
            
            cipherLength = input.readInt();     //read file (input)
            firstWordLength = input.readInt();
            i = input.readInt();
            cipherText = input.readString();
            knownWord = input.readString();

            plainText = new char[cipherLength];     //initialize full plaintext
            permutation = new int[firstWordLength]; //initialize permutation
            
            for (int j = 0; j<firstWordLength ; j++){ /**puts known word in  **/
                plainText[j] = knownWord.charAt(j);   /**plaintext array     **/
            }
            
           
            
            permute (firstWordLength);//finds permutation - now known & in array
            
            /*******      Apply permutation to rest of cipher text      *******/
            int permCount = 0;   //keeps track of which permutation we are doing
            
            for (int m = firstWordLength ; m < cipherLength ; m++){ 

                plainText[m] = cipherText.charAt( ( permutation[permCount] ) + (firstWordLength-1) );
                //puts cipher vals in order in a different array
                
                permCount++;       //onto the next permutation
                if( permCount > permutation.length-1 ) {
                    permCount = 0; //resets permutationCounter if too high
                }  
                
            }
            
             
            /**Output plaintext**/
            output = new ASCIIOutputFile();
            output.writeString("Your unciphered text is: ");
            for (int z = 0 ; z < plainText.length ; z++ ){
                output.writeC(plainText[z] );
            }
            /**Close and save**/
            output.close();
            input.close();
            
             /**Recipher with 'i' -- remove every ith from list**/  
             cipher = new Cipher ( plainText, i );
        }
        
        else {
            System.out.println("Invalid Text File");  //invalid text file warning if file is empty
        }
        
    }  //constructor
    
    
    private void permute( int k ){           //returns int array of permutations
           
        if ( k > 1 ){                        //stop case. only 1 letter left.    
            permute ( k-1 );                 //recursive call    
        }  //base case
        
        /**Find each letter, and keep track of where it was found. Stop case.**/
        for ( int n = 0 ; n < firstWordLength ; n++ ){   //check each letter for a match
            if ( knownWord.charAt((k-1)) == cipherText.charAt(n) ){        //if known letter is found
                 permutation[(k-1)] = n+1;              //remember permutation
              }
        }
    }  //permute -- find permutation
    
    
    public static void main(String[] args) {
      Decipher d = new Decipher();
    } //main method
    
} //Decipher
