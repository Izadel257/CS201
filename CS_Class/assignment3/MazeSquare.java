/**
 * MazeSquare.java
 * A helper class for maze solving assignment.
 * Represents a single square within a rectangular maze.
 *
 * @author Paul Claudel Izabayo
 */
public class MazeSquare {
   private char  indicator;

    /**
     * A constructor for the maze square
     * @param indicator the input from the file showing how to drow the column/row. 
     * */ 

    public MazeSquare(char indicator ) {
        this.indicator = indicator;
    }

    /**
     * A method for maze square.
     * @return true when the square maze has a left wall and false otherwise. 
     */
    public boolean hasLeftWall(){
        if (indicator == '|' || indicator == 'L'){
        return true;
        }
        return false;
    }
    /**
     * A method for maze square
     * @return true if the square maze has a bottom wall and false otherwise
     */
    public boolean hasBottomWall(){
        if (indicator == '_' || indicator == 'L'){
        return true;
        }
        return false;
    }
} 