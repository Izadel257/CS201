/**
 * This class creates a Tour of Points using a 
 * Linked List implementation.  The points can
 * be inserted into the list using two heuristics.
 *
 * @author Paul Claudel Izabayo
 * @author Eric Alexander, modified code 01-12-2018
 * @author Layla Oesper, modified code 09-22-2017
 */

public class Tour {

    /** 
     * A helper class that defines a single node for use in a tour.
     * A node consists of a Point, representing the location of that
     * city in the tour, and a pointer to the next Node in the tour.
     */
    private class Node {
        private Point p;
        private Node next;
	
        /** 
         * Constructor creates a new Node at the given Point newP
         * with an initial next value of null.
         * 
         * @param newP the point to associate with the Node.
         */
        public Node(Point newP) {
            p = newP;
            next = null;
        }

        /** 
         * Constructor creates a new Node at the given Point newP
         * with the specified next node.
         *
         * @param newP the point to associate with the Node.
         * @param nextNode the nextNode this node should point to.
         */
        public Node(Point newP, Node nextNode) {
            p = newP;
            next = nextNode;
        }
          
    } 
    
    // Tour class Instance variables
    private Node head;
    private int size; //number of nodes
    
    
    /**
     * Constructor for the Tour class.  By default sets head to null.
     */
    public Tour() {
        head = null;
        size = 0;
    }
    
    /**  
     * toString, a method that creates a description of the linked list and the points it contains as a string.
    * @return pointDescript, which is a description of the points in the different nodes.
    */
    public String toString(){ 
        String pointDescript = "";
        Node CurrentPointNode = head;
        if (CurrentPointNode == null){
            return pointDescript;
        }
        else{
            for (int i =0;i < size; i++){
                pointDescript = pointDescript + CurrentPointNode.p;
                CurrentPointNode = CurrentPointNode.next;
            }
            return pointDescript;
        }
    }
    /**
     * draw, a method that draws the points corresponding to different nodes in the linked list. 
     */
    public void draw(){
        Node PointNode = head;
        for (int m = 1; m<size;m++){ // draw every point and connect the different lines.
            PointNode.p.draw();
            PointNode.p.drawTo(PointNode.next.p);
            PointNode = PointNode.next;
        }
            PointNode.p.draw();      // draw the last line and connect it to the head. 
            PointNode.p.drawTo (head.p);
    }


    /**
     * This methods keeps track of the different nodes, and computes their size.
     * @return lenght, which is the size of tour (the linked list).
     */
    public int size(){
        Node current = head;
        if (current == null){ // in case the linked list is empty.
            return 0;
        } 
        else{
            int length = 1; // otherwise we start off with one element. 
            while (current.next != null){
                length++;
                current = current.next;
            }
            return length;
        }
    }
    /**
     * this method iterate through the different points and finds the total distance of the tour.
     * @return dist, which is the total distance of the tour.
     */
    public double distance(){
        double dist = 0;
        Node current = head;
            while (current.next != null){                  // iterate through the different nodes and calculate the total distance between them.
                    dist = dist + current.p.distanceTo(current.next.p);
                    current = current.next;
                }
            dist = dist + current.p.distanceTo( head.p); // add the distance from the last to the first node(point)
                
            return dist;
        }
    /**
     * insertNearest creates the linked list corresponding to the tour, by inserting nodes (points) 
     * after points they are closest to.
     * @param p, which is the point (node for that matter) that we are inserting. 
     */
    public void insertNearest (Point p){
        double minDist = Double.MAX_VALUE;
        Node Insert = new Node (p);
        Node refNode = head;

        // Iterate through the different nodes, find the distance and compare it to miniDist
        // store the node after which inserting Insert be the closest in terms of distance.
        if (head != null)  {    // starts off when the tour has some points in it.
			Node temp = head;
        refNode = head;
			for (int i = 0;i<size;i++) {
                if (temp.p.distanceTo(p) < minDist){
                    minDist = temp.p.distanceTo(p);
                    refNode = temp;
                }
                temp = temp.next;
            }

            // these conditions are meant for insertion.
            if (refNode.next != null){
                Insert.next = refNode.next;      // in case we are inserting in the middle of the list.
                refNode.next = Insert;
            }
            else{                           // in the case we insert at the end of the list
                refNode.next = Insert;
            }
            
            size++;
        }
		else {                              // when the tour has no points in it yet, add the first one. 
			head = new Node (p);
			size++;
		
    }
    }
    /**
     * this method creates a linked list representing the tour by inserting nodes in slots where
     * they would have the smallest increase in the total distance of the tour.
     * @param p, the point (the node) to insert.
     */
    public void insertSmallest (Point p){

        double Initial_diff;         // keeps track of the impact of inserting a point at a given slot. 
        Node Insert = new Node (p);  // creates the node to insert.
        Node refNode = head;         // creates the node of reference, where Insert will potentially be inserted
        if (head != null)  {         // start of when the tour have at least one point.
            if (head.next != null){
                Initial_diff =(head.p.distanceTo(p) + p.distanceTo(head.next.p) - head.p.distanceTo(head.next.p));
            }
            else{
                Initial_diff = 0;
            }
		Node temp = head;      // keeps track of the different nodes as they get created.
        Node nodeCopy = temp;        // allows me to make a copy of temp nodes as they get created. 
        refNode = head;
            for (int i = 1;i<size;i++) {

                // check whether I need to insert the node in the middle of the tour
                if (temp.p.distanceTo(p) + p.distanceTo(temp.next.p) - temp.p.distanceTo(temp.next.p) < Initial_diff){
                    Initial_diff =  (temp.p.distanceTo(p) + p.distanceTo(temp.next.p) - temp.p.distanceTo(temp.next.p));
                    refNode = temp;
                }
                //check also whether the node would not fit better at the end of the tour.
                while (nodeCopy.next != null){
                    nodeCopy = nodeCopy.next;
                }
                if (head.p.distanceTo(p) + p.distanceTo(nodeCopy.p) - head.p.distanceTo(nodeCopy.p) < Initial_diff){
                    refNode = nodeCopy;
                }
                temp = temp.next;
            }
            // this does the insertion 
            if (refNode.next != null){
                Insert.next = refNode.next;
                refNode.next = Insert;
            }
            else{
                refNode.next = new Node (p);
            }
            size++;
        }
		else {                 // create the point if need be. 
			head = new Node (p);
			size++;
		}
       
    }
    public static void main(String[] args) {
        /**
         * A method to test the implementation of the other methods.  
        */  
        Tour tour = new Tour();
        Point p = new Point (0,0);
        tour.insertSmallest(p);
        p = new Point(0,100);
        tour.insertSmallest(p);
        p = new Point(100, 100);
        tour.insertSmallest(p);
        System.out.println("Tour distance =  "+tour.distance());
        System.out.println("Number of points = "+tour.size());
        System.out.println(tour);
        int w = 100 ; //Set this value to the max that x can take on
        int h = 100 ; //Set this value to the max that y can take on
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.setPenRadius(.005);
        tour.draw();     
    }
   
}