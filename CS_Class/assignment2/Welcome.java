/**
 * Welcome.java
 * Paul claudel Izabayo
 * CS 201, Spring 2022
 * This code asks for the user name and an integer as inputs
 * It welcomes the user; reverses their name and
 * creates kind of a triangle with side length equal to the number input. 
 */

import java.util.Scanner;
public class Welcome {
    public static void main (String [] args){

        // Create a scanner object to be able to get user inputs
        Scanner scan = new Scanner (System.in);
        System.out.println ("Welcome to CS 201: Data Structures!");
        System.out.print ("What is your name: ");
        String name = scan.nextLine ();
        System.out.print ("Enter an integer: ");
        String numString = scan.nextLine ();
        scan.close();
        
        System.out.println ();
        
        System.out.println ("Welcome " + name);

        // Reverse the name by looping through its character components and writing them backwards.
        String revname = "";
        for (int i = (name.length ()) - 1 ; i >= 0 ; i = i - 1){
            char newchar = name.charAt (i);
            revname = revname + newchar;
            }
        System.out.println ("Your name backwards is " + revname); 

        // Error catch: tests whether the user input is an integer or not. 
        try{
            int number = Integer.parseInt (numString);

            /*When the  number input is a positive integer, 
            *loop through the number, create a string builder and append the numbers to the the string builder,
            as many times as how the big the numbers are as we the code loops. 
            */
            if (number > 0){
                for (int i = 1; i <= number; i++){
                    StringBuilder length = new StringBuilder();  
                    length.append (i);
                    for (int t = 2; t <= i; t = t + 1 ){
                        length.append (",");
                        length.append (i);
                    }
                    String stringLen = length.toString();
                    System.out.println(stringLen);
                }
                
            }

            // In the case where the user input is a negative integer, turn the number into a posive number.
            // do the same thing as for when the number is positive, but at the time of appending, add a negative sign (-) to the numbers.
            else if (number < 0){
                number = number * ( -1);
                for (int i = 1; i <= number; i++){
                    StringBuilder length = new StringBuilder();
                    length.append (-i);
                    for (int t = 2; t <= i; t = t + 1 ){
                        length.append (",");
                        length.append (-i);
                    }
                    String stringLen = length.toString();
                    System.out.println(stringLen);
                }
    
            }

            // When the user input integer is zero, just output that there is not triangle of height zero. 
            else if (number == 0){
                System.out.println ("Cannot print a triangle of height 0");
            }
        }

        // In the case when the input is not an integer. 
        catch (Exception e){
            System.out.println ("You must enter an integer");
            System.exit (1);
        }
        
    }
}
