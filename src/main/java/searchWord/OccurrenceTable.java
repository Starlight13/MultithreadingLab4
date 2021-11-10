package searchWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OccurrenceTable {

    private HashMap<String, List<String>> map;

    public OccurrenceTable() {
        this.map = new HashMap<>();
    }

    public void add(String fileName, String occurrence) {
        List<String> occurringList = map.get(fileName);
        if (occurringList == null) {
            occurringList = new ArrayList<>();
            occurringList.add(occurrence);
            map.put(fileName, occurringList);
        } else if (!occurringList.contains(occurrence)) {
            occurringList.add(occurrence);
        }
    }

    public void merge(OccurrenceTable table) {
        table.map.keySet().forEach( word -> {
            List<String> occurringList = this.map.get(word);
            if (occurringList == null) {
                occurringList = table.map.get(word);
                this.map.put(word, occurringList);
            } else {
                occurringList.addAll(table.map.get(word));
                this.map.put(word, occurringList.stream().distinct().collect(Collectors.toList()));
            }
        });
    }

    public List<String> get(String key) {
        return this.map.get(key);
    }

    public Set<String> getKeys() {
        return map.keySet();
    }
}
