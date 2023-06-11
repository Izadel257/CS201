/**
 * WordCount.java
 * By Paul Claudel & David Toledo
 * May 29, 2022
 */
public class WordCount implements Comparable<WordCount>{
    public String word;
    public int count;

    // create a constructor
    public WordCount(String key, int value){
        word = key; // the word 
        count = value; // the count associated with the word
    }
    /**
     * compareTo, a method that allows to comparison between wordCount objects, based on their count. 
     * @param comparedWordCount the word that we will be comparing with the other ones.
     * @return 1 if the object is less than the argument (because we want to sort in reverse order)
     * @return -1 if the object is greater than the argument
     * @return 0 if the two objects are equal. 
     */
    public int compareTo(WordCount comparedWordCount) {
        if(this.count > comparedWordCount.count){
            return -1;
        }
        else if (this.count < comparedWordCount.count){
            return 1;
        }
        else{
            return 0;
        }
    }
}
