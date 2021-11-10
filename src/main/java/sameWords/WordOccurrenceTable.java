package sameWords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WordOccurrenceTable {

    private HashMap<String, List<Occurrence>> map;

    public WordOccurrenceTable() {
        this.map = new HashMap<>();
    }

    public void add(String word, Occurrence occurring) {
        List<Occurrence> occurringList = map.get(word);
        if (occurringList == null) {
            occurringList = new ArrayList<>();
            occurringList.add(occurring);
            map.put(word, occurringList);
        } else {
            occurringList.add(occurring);
        }
    }

    public void merge(WordOccurrenceTable table) {
        table.map.keySet().forEach( word -> {
            List<Occurrence> occurringList = this.map.get(word);
            if (occurringList == null) {
                occurringList = table.map.get(word);
                this.map.put(word, occurringList);
            } else {
                occurringList.addAll(table.map.get(word));
            }
        });
    }

    public List<Occurrence> get(String key) {
        return this.map.get(key);
    }

    public Set<String> getKeys() {
        return map.keySet();
    }
}
