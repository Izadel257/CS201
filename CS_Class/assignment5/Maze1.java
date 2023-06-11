import java.io.*;
import java.util.*;

/**
 * Maze.java
 * Solution to the first Maze Assignment (HW3).
 * CS 201: Data Structures - Winter 2018
 *
 * @author Eric Alexander
 */
public class Maze {
    private ArrayList<ArrayList<MazeSquare>> rowList;
    private int w, h;
    private int startRow, startCol, endRow, endCol;
    private Maze.MazeSquare toreturn;
    // I am including MazeSquare as an inner class
    // to simplify the file structure a little bit.
    private class MazeSquare {
        private int r, c;
        private boolean top, bottom, left, right,
                start, end, visited;

        private MazeSquare(int r, int c,
                           boolean top, boolean bottom, boolean left, boolean right,
                           boolean start, boolean end) {
            this.r = r;
            this.c = c;
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
            this.start = start;
            this.end = end;
            this.visited = false;

        }
        void visit(){
            this.visited = true;
        }
        boolean isVisited(){
            return visited;
        }

        boolean hasTopWall() {
            return top;
        }
        boolean hasBottomWall() {
            return bottom;
        }
        boolean hasLeftWall() {
            return left;
        }
        boolean hasRightWall() {
            return right;
        }
        boolean isStart() {
            return start;
        }
        boolean isEnd() {
            return end;
        }
        int getRow() {
            return r;
        }
        int getCol() {
            return c;
        }
    }


    /**
     * Construct a new Maze
     */
    public Maze() {
        rowList = new ArrayList<ArrayList<MazeSquare>>();
    }

    /**
     * Load Maze in from given file
     *
     * @param fileName the name of the file containing the Maze structure
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

        // First line of file is "w h"
        String[] lineParams = scanner.nextLine().split(" ");
        w = Integer.parseInt(lineParams[0]);
        h = Integer.parseInt(lineParams[1]);

        // Second line of file is "startCol startRow"
        lineParams = scanner.nextLine().split(" ");
        startCol = Integer.parseInt(lineParams[0]);
        startRow = Integer.parseInt(lineParams[1]);

        // Third line of file is "endCol endRow"
        lineParams = scanner.nextLine().split(" ");
        endCol = Integer.parseInt(lineParams[0]);
        endRow = Integer.parseInt(lineParams[1]);

        // Read the rest of the lines (L or | or _ or -)
        String line;
        int rowNum = 0;
        boolean top, bottom, left, right;
        boolean start, end;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            rowList.add(new ArrayList<MazeSquare>());

            // Loop through each cell, creating MazeSquares
            for (int i = 0; i < line.length(); i++) {
                // For top, check row above, if there is one
                if (rowNum > 0) {
                    top = rowList.get(rowNum-1).get(i).hasBottomWall();
                } else {
                    top = true;
                }

                // For right, check cell to the right, if there is one
                if (i < line.length() - 1 ) {
                    char nextCell = line.charAt(i+1);
                    if (nextCell == 'L' || nextCell == '|') {
                        right = true;
                    } else {
                        right = false;
                    }
                } else {
                    right = true;
                }

                // For left and bottom, switch on the current character
                switch (line.charAt(i)) {
                    case 'L':
                        left = true;
                        bottom = true;
                        break;
                    case '_':
                        left = false;
                        bottom = true;
                        break;
                    case '|':
                        left = true;
                        bottom = false;
                        break;
                    case '-':
                        left = false;
                        bottom = false;
                        break;
                    default:
                        left = false;
                        bottom = false;
                }

                // Check to see if this is the start or end spot
                start = startCol == i && startRow == rowNum;
                end = endCol == i && endRow == rowNum;

                // Add a new MazeSquare
                rowList.get(rowNum).add(new MazeSquare(rowNum, i, top, bottom, left, right, start, end));
            }

            rowNum++;
        }
    }

    // create the getNeighbour.
    /**
     * A helper method to the maze class.Used to find a MazeSquare close (left,right,top,bottom) to a particular maze.
     * @param Sq the particular maze we want to find the maze adjecent to it.
     * @param direction left,right,top or bottom.
     * @return the maze square adjecent to the particular maze we are getting the neighbour of. 
     */
    public MazeSquare getNeighbour(MazeSquare Sq,String direction){
                    int rowNum = Sq.getRow();
                    int colNum = Sq.getCol();

                    if (direction == "left"){
                        toreturn = rowList.get (rowNum).get(colNum-1);
                    }
                    else if (direction == "right"){
                        toreturn = rowList.get (rowNum).get(colNum+1);
                    }
                    else if (direction == "top"){
                        toreturn = rowList.get (rowNum-1).get(colNum);
                    }
                    else if (direction == "bottom"){
                        toreturn = rowList.get (rowNum+1).get(colNum);
                    }
                    return toreturn;
                }
        
    /**
     * A helper class for the maze class
     * @return toreturn a square maze containing the solution.
     */
    public LLStack<MazeSquare> getSolution(){
        // get the Start Maze, push it and mark it as visited.
        LLStack<MazeSquare> theStack = new LLStack<MazeSquare>();
        ArrayList<MazeSquare> StartRow = rowList.get(startRow);
        MazeSquare FirstMaze = StartRow.get (startCol);
        theStack.push (FirstMaze);
        FirstMaze.visit();
        MazeSquare T = theStack.peek();

        // get the rest of the maze squares that are part of the solution.
        // loop until the stack is empty or is we reach the End maze square. 
        while (!theStack.isEmpty()){
            if (T.isEnd()){
                return theStack;
            }
            // pop the maze square if it is blocked on all sides    
            if ((                    
            T.hasRightWall() || 
            getNeighbour(T, "right").isVisited()) && 
            (
            T.hasLeftWall() ||
            getNeighbour(T, "left").isVisited()) && 
            ( 
            T.hasTopWall()||
            getNeighbour(T, "top").isVisited()) &&
            (
            T.hasBottomWall() || 
            getNeighbour(T, "bottom").isVisited())) {
                theStack.pop();
            }
            // push the maze square to the stack if it is not blocked on all ends. 
            else{
                if (
                !T.hasRightWall() &&
                !getNeighbour(T, "right").isVisited()){
                    theStack.push(getNeighbour(T, "right"));
                    getNeighbour(T, "right").visit();   
                }
                else if (
                !T.hasLeftWall() &&
                !getNeighbour(T, "left").isVisited()){
                    theStack.push(getNeighbour(T, "left"));
                    getNeighbour(T, "left").visit();
                    
                }
                else if ( 
                !T.hasTopWall() &&
                !getNeighbour(T, "top").isVisited()){
                    theStack.push(getNeighbour(T, "top"));
                    getNeighbour(T, "top").visit();
                }
                else if ( 
                !T.hasBottomWall() &&
                !getNeighbour(T, "bottom").isVisited()){
                    theStack.push(getNeighbour(T, "bottom"));
                    getNeighbour(T, "bottom").visit();
                }
            }
                if (!theStack.isEmpty()){
                    T = theStack.peek();
                }
                else{
                    if (theStack.isEmpty()){
                        System.out.println("The maze is unsolvable");
                    }
                }
        }
        return theStack;
    }
    /**
     * Print the Maze to the Console
     */
    public void print() {
        // create a variable for the stack that contains the solution for the maze. 
        LLStack<MazeSquare> Solution = getSolution();
        ArrayList<MazeSquare> currRow;
        MazeSquare currSquare;

        // Print each row of text based on top and left
        for (int r = 0; r < rowList.size(); r++) {
            currRow = rowList.get(r);

            // First line of text: top wall
            for (int c = 0; c < currRow.size(); c++) {
                System.out.print("+");
                if (currRow.get(c).hasTopWall()) {
                    System.out.print("-----");
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println("+");

            // Second line of text: left wall then space
            for (int c = 0; c < currRow.size(); c++) {
                if (currRow.get(c).hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("     ");
            }
            System.out.println("|");

            // Third line of text: left wall, then space, then start/end/sol, then space
            for (int c = 0; c < currRow.size(); c++) {
                currSquare = currRow.get(c);

                if (currSquare.hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("  ");
                if (currSquare.isStart() && currSquare.isEnd()) {
                    System.out.print("SE ");
                } else if (currSquare.isStart() && !currSquare.isEnd()) {
                    System.out.print("S  ");
                } else if (!currSquare.isStart() && currSquare.isEnd()) {
                    System.out.print("E  ");
                // mark the maze square with a * if it is part of the solution
                }else {
                    if (Solution.contains(currSquare)){
                        System.out.print("*  ");
                    }
                    else{
                    System.out.print("   ");
                    }
                }
            }
            System.out.println("|");
            // Fourth line of text: same as second
            for (int c = 0; c < currRow.size(); c++) {
                if (currRow.get(c).hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("     ");
            }
            System.out.println("|");
        }

        // Print last row of text as straight wall
        for (int c = 0; c < rowList.get(0).size(); c++) {
            System.out.print("+-----");
        }
        System.out.println( );
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
