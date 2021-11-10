package stat;

import java.util.HashMap;
import java.util.Set;

public class HistogramTable {

    private HashMap<Character, Integer> map;

    public HistogramTable() {
        this.map = new HashMap<>();
    }

    public void add(Character key){
        map.merge(key, 1, Integer::sum);
    }

    public void merge(HistogramTable histogramTable) {
        Set<Character> keys = histogramTable.getMap().keySet();
        keys.forEach(key -> {
            if (map.get(key) == null) {
                map.put(key, histogramTable.map.get(key));
            } else {
                map.put(key, map.get(key) + histogramTable.map.get(key));
            }
        });
    }

    public HashMap<Character, Integer> getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Histogram Table");
        builder.append("\n");

        Set<Character> keys = map.keySet();
        keys.forEach(key -> {
            builder.append(key);
            builder.append("  =>  ");
            builder.append(map.get(key));
            builder.append("\n");
        });

        return builder.toString();
    }
}
