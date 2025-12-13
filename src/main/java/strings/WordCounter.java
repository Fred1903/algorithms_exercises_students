package strings;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {
    private TreeMap<String,Integer> wordsMap;

    public WordCounter() {
        wordsMap=new TreeMap<String,Integer>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        if(wordsMap.containsKey(word))wordsMap.put(word,wordsMap.get(word)+1);
        else wordsMap.put(word,1); //valeur au début apres un ajout=1
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        if(word==null)return -2;
        if(wordsMap.containsKey(word))return wordsMap.get(word);
        return 0;
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
        return wordsMap.keySet().iterator();
    }
}
