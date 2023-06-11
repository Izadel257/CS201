/**
 * An implementation of the List ADT using
 * a linked list.  Specifically, this implementation
 * only allows a List to contain Comparable items.
 *
 * @author Layla Oesper 
 * @author Eric Alexander
 * @author Paul Claudel Izabayo
*/

/* Note <E extends Comparable<E> means this container
 * can only old objects of type E that are Comparable.
 */
public class RecursiveLinkedList<E extends Comparable<E>>{ 
    
    /* Internal Node class used for creating linked objects.
    */
    private class Node<E> {
        private E data;
        private Node<E> next;
    
        private Node(E dataItem) {
            data = dataItem;
            next = null;
        }
        
        private Node(E dataItem, Node<E> nextNode) {
            data = dataItem;
            next = nextNode;
        }    
    } // End Node class

    //Instance variables for RecursiveLinkedList
    private Node<E> head;
    private int numItems;
    
    /**
     * Creates an empty RecursiveLinkedList
     */
    public RecursiveLinkedList() {
        head = null;
        numItems = 0;
    }
    
    /**
     * Returns the data stored at positon index.
     * @param index
     * @return The data stored at position index. 
     */
    public E get(int index) {
        if (index < 0 || index >= numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } 
        Node<E> node = getNode(index);
        return node.data;
    }
    
    /*
     * Helper method that retrieves the Node<E> stored at 
     * the specified index.
     */
    private Node<E> getNode(int index) {
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }
    
    /**
     * Removes and returns the data stored at the specified index.
     * @param index The position of the data to remove.
     * @return The data previously stored at index position.
     */
    public E remove(int index) {
        if (index < 0 || index >= numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        
        if (index == 0){
            return removeFirst();
        } else {
            Node<E> before = getNode(index - 1);
            return removeAfter(before);
        }
    }
    
    /*
     * Helper method that removes the Node<E> after the
     * specified Node<E>. Returns the data that was
     * stored in the removed node.
     */
    private E removeAfter(Node<E> node) {
        Node<E> temp = node.next;
        if (temp != null) {
            node.next = temp.next;
            numItems--;
            return temp.data;
        } else {
            return null;
        }
    }
    
    /*
     * Helper method that removes the first Node<E> in
     * the Linked List.  Returns the data that was
     * stored in the removed node.
     */
    private E removeFirst() {
        Node<E> temp = head;
        if (head != null) {
            head = head.next;
        }
        
        if (temp != null) {
            numItems--;
            return temp.data;
        } else {
            return null;
        }
    }
    
    /**
     * Adds the data to the list at the specified index.
     * @param index The position to add the data.
     * @param anEntry The particular data to add to the list.
     */
    public void add(int index, E anEntry) {
        if (index < 0 || index > numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(anEntry);
        } else {
            Node<E> node = getNode(index - 1);
            addAfter(node, anEntry);
        }
    }
    
    /*
     * Helper method that adds anEntry to the first
     * position in the list.
     */
    private void addFirst(E anEntry) {
        head = new Node<>(anEntry, head);
        numItems++;
    }
    
    /*
     * Helper method that adds anEntry after the
     * specified Node<E> in the linked list.
     */
    private void addAfter(Node<E> before, E anEntry) {
        before.next = new Node<>(anEntry, before.next);
        numItems++;
    }
    
    /**
     * Add the specified data to the end of the list.
     * @param anEntry The data to add to this list.
     */
    public boolean add(E anEntry) {
        add(numItems, anEntry);
        return true;
    }
    
    /**
     * Returns the size of the list in terms of items stored.
     * @returns the number of items in the list.
     */
    public int size() {
        return numItems;
    }
    
    /**
     * Modifies the list so the specified index now 
     * contains newValue (overwriting the old data).
     * @param index The position int he list to add data.
     * @param newValue The data to place in the list.
     * @return The previous data value stored at index.
     */
    public E set(int index, E newValue) {
        if (index < 0 || index >= numItems) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        E result = node.data;
        node.data = newValue;
        return result;
    }
    
    /**
     * A string representation of the List.
     * @returns A string representation of the list.
     */
    public String toString() {
        String s = "[";
        Node<E> temp = head;
        for (int i = 0; i < numItems; i++) {
            s = s + temp.data.toString();
            if (i < numItems - 1) {
                s = s + ", ";
            }
            temp = temp.next;
        }
        s = s + "]";
        return s;
    }
    /**
     * Return the maximum element in the list using
     * compareTo() method of Comparable.
     * Returns null if the list is empty. 
     * @return maximum element of the list
     **/
    public E max(){
        // when the list is empty
        if (head==null){
            return null;
        }
        // when the lists has elements in it. 
        else{
            Node<E> maxNode= helperMax(head,head.next);
            return maxNode.data;
        }
    }
    /**
     * A helper method to the max method. 
     * @param currMax keeps track of what is currently the max. 
     * @param potentialMax keeps track of what might replace the  current max. 
     * @return the maximum element of the linked list. 
     */
    public Node<E> helperMax(Node<E> currMax, Node<E> potentialMax){
        // Base case: When there is only one element. 
        if (potentialMax==null){
            return currMax;
        }
        // general case: when the list has more than just one element to do comparison.
        else{
            // if the element after the first is greater than the first one, update the search,
            // start from the greatest. 
            if (potentialMax.data.compareTo (currMax.data)>0){
                return helperMax(potentialMax, potentialMax.next);
            }
            else{
            // in case the current element is the greatest, update the elements you compare it with. 
            return helperMax(currMax,potentialMax.next);
            }
        }
    }
     

    /**
     * Remove all elements that match element using the 
     * equals() operator to determine a match. 
     * (Don't use ==).
     *
     * @param element The element that should be removed
     **/
    public void removeAll(E element){
        Node <E> ref = new Node<E>(element);
        // when the list is empty, do nothing.
        if (head == null){

        }
        // proceed with operations in case there is something in the list. 
        else{
            helperRemoveAll(ref,head,head);
        }
    }
    /**
     * a helper method for the removeAll method
     * @param elemNode the node that stores the element to be removed
     * @param temp , stores a temporary node, which is potentially matches elemNode;
     * @param tracer , which traces the previous node to the potential node to remove. 
     */
    public void helperRemoveAll(Node<E> elemNode, Node<E> temp, Node<E> tracer){
        // base case: when the list has no  element in it, just do nothing
        if (temp==null){
        }
        // recursive case: when the list has elements to search through. 
        else{
            if (temp.data.equals(elemNode.data)){
                // in case we need to remove the head
                if (temp==head){
                    head=temp.next;
                    numItems--;
                }
                else{
                    // in case the node to remove is not the head. 
                    // remove the node, and update temp(which updates tracer too)
                    tracer.next=temp.next;
                    temp=tracer;
                    numItems--;
                }
            }
            helperRemoveAll(elemNode,temp.next,temp);
        }
        
    }

    /**
     * Duplicate each element of the list
     *
     * For example, the list [ 0 1 2 ] duplicated becomes 
     * [ 0 0 1 1 2 2 ]
     **/
    public void duplicate(){
        // if the list is empty, we are not duplicating anything. 
        if (head==null){
        }
        // in case the list has elements to duplicate. 
        else{
            duplicateHelper(head);
        }
    }
    /**
     * a helper method for the duplicate method
     * @param toDup
     * takes in a node and duplicates it
     */
    public void duplicateHelper(Node<E> toDup){
        // Base case: when the next element in the linked is null, 
        // duplicat the element and end the circuit. 
        if (toDup.next==null){
            addAfter(toDup,toDup.data);
        }
        // Recursive case: keep duplicating elements as long as the next node is not null. 
        else{
            Node<E> temp = toDup.next;
            addAfter(toDup, toDup.data);
            duplicateHelper(temp);
        }
    }
 
    /**
     * Here are a couple short tests. You should 
     * should make sure to thoroughly test your code.
     */
    public static void main(String[] args) {
        RecursiveLinkedList<String> l = new RecursiveLinkedList<String>();
        l.add("hello");
        l.add("world");
        l.add("paul");
        l.add("zanda");
        l.add("zzzzzz");
        l.add("zanda");
        l.add ("zanda");
        l.duplicate();
        l.add("ziiim");
        l.removeAll("zanda");
        l.removeAll("ziiim");
        l.removeAll ("a");
        System.out.println("List is: " + l + " " + l.max());
    }
    
}