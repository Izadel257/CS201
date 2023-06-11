/**
 * Maze.java
 * A class for loading and printing mazes from files.
 *
 * @author Paul Claudel Izabayo
 */

import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class Maze {
    private ArrayList<ArrayList<MazeSquare>> rowList;
    private int w;   
    private int h;
    private int StartCol;
    private int StartRow;
    private int FinishCol;
    private int FinishRow;

    /**
     * Constructor for the Maze class
     */
    public Maze() {
        rowList = new ArrayList<ArrayList<MazeSquare>>();
    }

    /**
     * Load in a Maze from a given file
     *
     * @param fileName the name of the file containing the maze
     */
    public void load(String fileName) {
        // Create a scanner for the given file
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        // lists containing the three first lines. 
        String[] lineParams = scanner.nextLine().split(" ");
        String [] StartParams = scanner.nextLine().split(" ");
        String [] FinParams = scanner.nextLine().split(" ");

        // getting the elements fromt the intiger from the lists.
        w = Integer.parseInt(lineParams[0]);
        h = Integer.parseInt(lineParams[1]);
        StartCol = Integer.parseInt (StartParams[0]);
        StartRow = Integer.parseInt (StartParams[1]);
        FinishCol = Integer.parseInt (FinParams[0]);
        FinishRow = Integer.parseInt (FinParams[1]);

        // load the remaining lines of the file
        while (scanner.hasNext()){
            String OtherLines = scanner.nextLine();
            ArrayList<MazeSquare> myMazes = new ArrayList<>();
            for (int i = 0 ; i < OtherLines.length(); i ++){
                MazeSquare mazeSquare= new MazeSquare(OtherLines.charAt(i)); 
                myMazes.add(mazeSquare);
            }
            rowList.add(myMazes);
        }
    }

    /**
     * Print the Maze to System.out
     */
    public void print() {
    for (int len = 0 ; len < w ; len++){ 
        System.out.print ("+-----"); // create the uppermost line. 
    }
    System.out.println("+");

    for (int i = 0 ; i < h; i++){
        ArrayList <MazeSquare> row = rowList.get(i);
        //create the left vertical line, column by column, one line at a time.  
        for (int m = 0 ; m <3 ; m++ ){
            for (int t = 0 ; t < w ; t++ ){
                if (row.get(t).hasLeftWall()){                          // decides whether the left wall exists. 
                    if ((t == StartCol)& (m==1) & (i == StartRow)){     // decides whether the column and the row make the start square. 
                        System.out.print("|  S  ");
                    } 
                    else if ((t == FinishCol)& (m==1) & (i == FinishRow)){ // decides whether the square is the finish square. 
                        System.out.print("|  F  ");
                    } 
                    else{
                        System.out.print("|     ");
                    }
                }
                else{                                                   // in the case the square does not have the vertical left line. 
                    if ((t == StartCol)& (m==1) & (i == StartRow)){     // whether the square is the start square. 
                        System.out.print("   S  ");
                    }
                    else if ((t == FinishCol)& (m==1) & (i == FinishRow)){ // whether the square is a finish square. 
                        System.out.print ("   F  ");
                    }
                    else{
                        System.out.print("      ");
                    
                    }
                }
            }
            
            System.out.println("|");
        }
        // Create the bottom line when needed. 
        for (int t = 0 ; t < w ; t++ ){
            if (row.get(t).hasBottomWall()){ // decides whether we need the bottom line. 
                System.out.print("+-----");
            } else{
                System.out.print("+     ");
            }
        }
        System.out.println("+");
        
    }
}

    // This main program acts as a simple unit test for the
    // load-from-file and print-to-System.out Maze capabilities.
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Maze mazeFile");
            System.exit(1);
        }

        Maze maze = new Maze();
        maze.load(args[0]);
        maze.print();
    }
}