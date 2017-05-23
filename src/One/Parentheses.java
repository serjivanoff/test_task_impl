package One;

import java.io.*;

public class Parentheses {

    public static void main(String[] args)throws IOException{
        int numberOfPairs = consoleHelper();
        System.out.println("Number of correct expressions: " + numberOfCorrectExpressions(numberOfPairs));
      }

    //Obtains user's console input, returns parsed int value

    private static int consoleHelper() throws IOException{
        int result=0;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(System.in))){
                while(result<=0){
                System.out.println("Enter the number of parentheses pairs, please. Or type \"q\" to exit.");
                    System.out.print("Number of pairs: ");
                String line = reader.readLine();
                if("q".equals(line.toLowerCase())){
                    System.out.println("Have a good day!");
                    System.exit(0);}
                try{
                     result = Integer.parseInt(line);
                    }
                catch (NumberFormatException ex) {
                    System.out.println("Invalid input!Try again");
                   }
                }
            return result;
        }
    }

    /*
    The "core" is here. Returns the number of correct expressions with open "("
    and close ")" brackets for given number of pairs of them.
     */
   private static int numberOfCorrectExpressions(int numberOfPairs) {

       int result=0;
       int numberOfBrackets=2*numberOfPairs;
       /*
       Each element of bitArray is integer and can be "0" or  "1" representing the element of bracket expression
       of  "(" or ")" respectively.
       0101 is ()()
        */
       int [] bitArray = new int[numberOfBrackets];
/*
    Number of iterations is only half of 2^(2*numberOfPairs) because combinations over that are not needed to check
    as they're knowingly incorrect (like ")..." combinations)

 */
       int numberOfIterations = (int)Math.pow(2, numberOfBrackets)/ 2;
       /*
       Loop for imitation binary representation of value incrementing :
       0 0 0 0 -> 0 0 0 1 -> 0 0 1 0 -> 0 0 1 1 -> ... -> 0 1 1 1 for full brute forcing all possible
       combinations.
        */
       for (int i = 0; i < numberOfIterations-1; i++) {
           if (bitArray[numberOfBrackets-1] == 0){
               bitArray[numberOfBrackets - 1] = 1;
           } else{
               int k = numberOfBrackets - 2;
               while ( bitArray[k] == 1){
                   k--;
                }
               bitArray[k] = 1;
               for (int m = k+1; m < numberOfBrackets; m++){
                   bitArray[m] = 0;
               }
           }
//        Checks if the current combination is correct and increments result if so
           if(check(bitArray))result++;
       }
       return result;
   }

    private static boolean check(int[]array){
        int length=array.length;
//incorrect if expression begins with ")" or ends  with"("
        if(array[length-1] == 0 || array[0] == 1)return false;
        int leftBracketCounter = 0;
        int rightBracketCounter = 0;
//Loop for iterating among array's elements one by one

        for (int i=0; i < array.length; i++){
//        There's no sense to check more if quantity of ")" once becomes greater than "("
            if (rightBracketCounter>leftBracketCounter) return false;
//       Counting "(" and ")" quantity respectively
            if (array[i] == 0) {
                leftBracketCounter++;
            } else {
                rightBracketCounter++;
            }
        }
//     And the expression is correct if counters are equal
        return rightBracketCounter == leftBracketCounter;
    }
 }
