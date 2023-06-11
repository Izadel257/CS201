/**
 * wordCount.java
 * Written by Paul Claudel Izabayo & David Toledo
 * May 29, 2022
 */

import java.util.ArrayList;
import java.util.Collections;

public class WordCountMap {
    private Node root; // the root of the BST containing the (word,count) pairs
    private  ArrayList<WordCount> byWordList = new ArrayList<WordCount>();// an array list containing WordCount objects sorted by word, alphabetically. 
    
    // create a node that would contain word,count as key,value pairs. 
    private class Node {
        private String word;
        private int count;
        private Node left;
        private Node right;
        public Node (String key, int value){
            word = key; // the word that the node contains
            count = value;// the count associated with the word
            left = null; // the left pointer
            right = null; // the right pointer
        }
    }
    // create a constructor
    public WordCountMap(){
        root = null;    
    }
    
    /**
     * incrementCount, a method that uses the node class to create a node containing the 
     * the word and its associated count. The account is incremented by one as the word occurs. 
     * @param word the word to be added to the binary search tree. 
     */
    public void incrementCount(String word) {
        root = incrementCounterHelper(root, word);
    }
    private Node incrementCounterHelper(Node currentWord, String value){
        if(currentWord == null){
            return new Node(value, 1);
            
        }
        if (currentWord.word.equals(value)){
            currentWord.count++;
            return currentWord;
        }    
        else{
            if(currentWord.word.compareTo(value) < 0){
                currentWord.right = incrementCounterHelper(currentWord.right, value);
                return currentWord;
            } 
            else{
                currentWord.left = incrementCounterHelper(currentWord.left, value);
                return currentWord;
            }
        }
    }
    /**
     * getWordCountsByCounts, a method that creates an array of wordCount objects, sorted by their counts in the reverse order.
     * @return an array list containing the wordCount objects
     */
    public ArrayList<WordCount> getWordCountsByCount() {
        inOrderTraversal(root);
        ArrayList<WordCount> byCountList = byWordList;
        Collections.sort(byCountList);
        return byCountList;
    }

    /**
   * getWordCountsByCounts, a method that creates an array list containing WordCount objects sorted alphabetically by word
   * @return the array list containing the WordCount objects
   */
    public ArrayList<WordCount> getWordCountsByWord() {
        inOrderTraversal(root);
        return byWordList;
    }
    /**
     * inOrderTraversal, a helper method for the getWordCountsByCounts and getWordCountsByWord method
     * does an inorder traversal of the BST. 
     * @param currNode, the current node that the function is looking at. 
     */
    private void inOrderTraversal (Node currNode){
        if (currNode==null){
            return ;
        }
        else{
            inOrderTraversal(currNode.left);
            byWordList.add (new WordCount(currNode.word,currNode.count));
            inOrderTraversal(currNode.right);
        }    
    }    
}
