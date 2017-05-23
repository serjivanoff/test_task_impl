package Three;

import java.util.*;
/*I hope the expected solution should not use BigInteger, so I've decided
to implement "long multiplication" method with storing each digit of number as
an element of LinkedList<Integer>.
* */
public class Factorial {
    public static void main(String[] args) {
        go();
    }

    private static void go(){
        LinkedList<Integer>result = parser(1);
            for(int i = 1; i<= 100; i++){
                result = multiplier(result, parser(i));
            }
        resultWriter(result);
    }

/*Converts int number to LinkedList<Integer>, for example
int 123 -> LinkedList{1, 2, 3}
 */
    private static LinkedList<Integer> parser(int number){
        LinkedList<Integer> result = new LinkedList<>();
        if(number < 10){
            result.addFirst(number);
            return result;}
        int temp = number;
        while(temp != 0){
            result.addFirst(temp % 10);
            temp=(temp - result.getFirst())/10;
        }
        return result;
    }
/*
Outputs to the console resulting number 100! and sum of the digits in it
 */
    private static void resultWriter(LinkedList<Integer> list){
        int sum=0;
        for(Integer i:list){
            System.out.print(i);
            sum+=i;
        }
        System.out.println();
        System.out.println("And the sum is: "+sum);
    }
/*
 Executes summing with given shifting of two numbers , represented with to lists of Integer. Example:
 addendum1 =  {1, 2, 3, 4, 5 }(representing 12345), addendum2 = {1, 2, 3, 6, 5, 4 } (representing 123654), shift = 1
    1 2 3 4 5
1 2 3 6 5 4
1 2 4 8 8 8 5
 */
    private static LinkedList<Integer> shiftAndAdd(LinkedList<Integer> addendum1, LinkedList<Integer> addendum2, int shift){
        LinkedList<Integer> result = new LinkedList<>();
// Shifting on given number of places
        for(int i=0; i < shift; i++){
            result.addFirst(addendum1.pollLast());
        }
 //decTransfer cares about unit transfer to next decimal place
    int decTransfer = 0;
        while(!addendum1.isEmpty()){
            int temp = addendum1.pollLast() + addendum2.pollLast() + decTransfer;
            if(temp < 10){
                result.addFirst(temp);
                decTransfer = 0;
            }else {

// If sum of two digits is exceed 9 than resulting list stores only
// remainder of division sum by 10 and decTransfer stores difference between sum and remainder but
//  divided by 10. Example: if 5+8 = 13, than list will store 13%10=3, and decTransfer variable will
//  transfer (13-3)/10 = 1, and the 1 value will be added to next decimal place
                result.addFirst(temp % 10);
                decTransfer = (temp - temp % 10)/10;
            }
        }
//After loop decTransfer may contain not null value which has to be taken into account
        if(decTransfer!=0){
            if(addendum2.isEmpty()){result.addFirst(decTransfer);}else{
            result.addFirst(addendum2.pollLast() + decTransfer);}
        }
//All remaining digits of addendum2 should to be taken into account too
        while(!addendum2.isEmpty()){
            result.addFirst(addendum2.pollLast());
        }
        return result;
}
/*
Executes multiplying of two lists factor1 and factor2, which are representing int values. Principle is
almost the same as in shiftAndAdd() method  - taking digits one by one from factors, then multiplying one to another,
and carrying about transfer decimal unit to next decimal place if necessary.
* */
    private static LinkedList<Integer> multiplier(LinkedList<Integer> factor1, LinkedList<Integer> factor2){
        LinkedList<Integer> result = new LinkedList<>();
        for(int i= factor2.size() - 1; i >= 0; i--){
            LinkedList<Integer> lineOfMultiplication = new LinkedList<>();
            int decTransfer = 0;
            int number1 = factor2.get(i);
            for(int j = factor1.size() - 1; j >= 0; j--){
                int number2 = factor1.get(j);
                int temp = number1 * number2 + decTransfer;
                if(temp < 10){lineOfMultiplication.addFirst(temp);
                    decTransfer = 0;}
                        else{
                    lineOfMultiplication.addFirst(temp % 10);
                    decTransfer = (temp - temp % 10)/10;
                }
            }
            if(decTransfer != 0){lineOfMultiplication.addFirst(decTransfer);}
            result = shiftAndAdd(result, lineOfMultiplication,factor2.size() - i - 1);
        }
        return result;
    }

}
