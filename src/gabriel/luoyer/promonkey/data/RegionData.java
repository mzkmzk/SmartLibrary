package gabriel.luoyer.promonkey.data;

import java.util.HashMap;

public class RegionData {
	public String name;
	public String dictKey;
	// 只要key，name
	public HashMap<String, String> children;
	public RegionData(String name, String dictKey) {
		this(name, dictKey, null);
	}
	public RegionData(String name, String dictKey, HashMap<String, String> children) {
		this.name = name;
		this.dictKey = dictKey;
		this.children = children;
	}
}
