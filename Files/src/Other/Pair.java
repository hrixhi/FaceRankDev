package Other;
import java.util.Map;
import java.util.TreeMap;

public class Pair implements Comparable<Pair>{
	
	private String username;
	private Map<String, Double> pair = new TreeMap<String, Double>();
	
	public Pair(String key, Double value) {
		this.username = key;
        pair.put(key, value);
    }

	public Double getValue() {
		return pair.get(username);
	}

	public String getKey() {
		return username;
	}

	
	@Override
	public int compareTo(Pair o) {
		// TODO Auto-generated method stub
		return Double.compare(this.getValue(), o.getValue());
	}
	
}
