/**
 * WordCounter.java
 * Written by Paul Claudel Izabayo & David Toledo
 * May 29, 2022
 */

import java.util.*;
import java.io.*;
public class WordCounter {
    List<String> stopWordsList = readStopWordsText();// array list of stop words
    WordCountMap obj = new WordCountMap(); // The BST object. 

    /**
     * FileRead, this method helps read a file
     * @param fileName the name of the file
     */
    private void FileRead (String fileName){
        File inputFile = new File (fileName);
        Scanner scanner = null;
        try{
            scanner = new Scanner(inputFile);
        }
        catch(FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }

        while (scanner.hasNext()){
            String word = scanner.next();
            if (word.contains("--")){
                String[] separateWord = word.split("--");
                for (int t=0;t<separateWord.length;t++){
                    if(stopWordsList.contains(word)){}
                    else{
                        obj.incrementCount(separateWord[t].replaceAll("[^a-zA-Z]",""));
                    }
                }
            }
            else{
                word = word.replaceAll ("[^a-zA-Z]","");
                word = word.toLowerCase();
                if(stopWordsList.contains(word)){}
                else{
                obj.incrementCount(word);
                }
            }

        }
        scanner.close();
    }

    /**
     * alphabetical, a method that creates and reads a arraylist of pairs (word,count);
     * sorted alphabetically
     * @param fileName the name of the file that contains the words
     */
    public void alphabetical (String fileName){
        FileRead(fileName); 
        ArrayList<WordCount> wordCountObj = obj.getWordCountsByWord();
        for (int i = 0;i<wordCountObj.size();i++){
           WordCount elem = wordCountObj.get(i);
            System.out.println(elem.word+":"+elem.count);
        }
    }

    /**
     * frequency, a method that creates and reads an array list containing pairs (word, count);
     * sorted by count.
     * @param fileName the name of the file containing the words
     */
    public void frequency (String fileName){
        FileRead(fileName);
        ArrayList<WordCount> wordCountObj1 = obj.getWordCountsByCount();
        for (int i = 0;i<wordCountObj1.size();i++){
           WordCount elem = wordCountObj1.get(i);
            System.out.println(elem.word+":"+elem.count);
        }
    }

    /**
     * readStopWordsText, a method that read the stopWords.txt file to retrieve all the stop words
     * @return a list of all the words found in the stopWords.txt file
     */
    private List<String> readStopWordsText() {
        List<String> stopWordsArray = new ArrayList<>();
        File stopWordFile = new File ("StopWords.txt");
        Scanner stopFileScanner = null;
        try{
            stopFileScanner = new Scanner(stopWordFile);
        }
        catch(FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }
    while(stopFileScanner.hasNextLine()){
        stopWordsArray.add(stopFileScanner.nextLine());
    }
    return stopWordsArray;
    }
    
    /**
     * cloud, a method that, with the help of WordCloudMaker.java, creates a html file
     * representing a word cloud for the given file
     * @param FileName the name of the file
     * @param limit the number of words we want the word cloud to contain
     */
    public void cloud (String FileName, int limit){
        FileRead(FileName);
        ArrayList<WordCount> wordCountObj2 = obj.getWordCountsByCount();
        ArrayList<WordCount> mostCommon = new ArrayList<WordCount>();
        if (wordCountObj2.size()>limit){
            for (int i=0;i<limit;i++){
                mostCommon.add(wordCountObj2.get(i));
            }
        }
        else{
            mostCommon = wordCountObj2;
        }
        String title = FileName.replaceAll(".txt", "");
        String htmlFile = FileName.replaceAll("txt", "html");
        WordCloudMaker cloudObj = new WordCloudMaker();
        cloudObj.createWordCloudHTML(title, mostCommon, htmlFile);
        cloudObj.getWordCloudHTML(title, mostCommon);
    }

    /**
     * a method for debugging and passing command line arguments
     * @param args a list of the command line arguments we want to pass to our code
     */
    public static void main (String[]args){
        WordCounter newObj = new WordCounter();
        String fileName = args[1];
        // get the code to read the alphabetical method
        if (args[0].equals ("alphabetical")){
            newObj.alphabetical(fileName);
        }
        // get the code to read the frequency method
        else if (args[0].equals ("frequency")){
            newObj.frequency(fileName);
        }
        // read the cloud method
        else if (args[0].equals ("cloud")){
            int limit = Integer.parseInt(args[2]);
            newObj.cloud(fileName,limit);
        }
    }
}
