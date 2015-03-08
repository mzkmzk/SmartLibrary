package gabriel.luoyer.promonkey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gabriel.luoyer.promonkey.data.RegionData;
import gabriel.luoyer.promonkey.utils.Utils;
import android.app.Application;
import android.content.Context;

public class MainApp extends Application {
	private static MainApp mainApp = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		mainApp = this;		
	}
	
	public static MainApp getInstance() {
		return mainApp;
	}
	
	private ArrayList<RegionData> regionData = null;
	public ArrayList<RegionData> getRegionData() {
		if(null == regionData) {
			initRegionData(this);
		}
		return regionData;
	}
	
	private void initRegionData(Context context) {
		regionData = new ArrayList<RegionData>();
		InputStream is = null;
		try {
			is = context.getAssets().open(Utils.getFilePathName("cfg", "region"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			JSONObject jo = new JSONObject(transferIs2String(is));
			JSONObject infoObj = jo.getJSONObject("info");
			JSONArray states = infoObj.getJSONArray("states");
			for(int i=0, len=states.length(); i<len; i++) {
				JSONObject obj = states.getJSONObject(i);
				String key = obj.getString("dictKey");
				// 获取该省级下，城市映射
				HashMap<String, String> children = null; 
				JSONArray cities = obj.optJSONArray("cities");
				if(null != cities) {
					children = new HashMap<String, String>();
					String cityKey;
					for(int j=0, length=cities.length(); j<length; j++) {
						JSONObject city = cities.getJSONObject(j);
						cityKey = city.getString("dictKey");
						children.put(cityKey, city.getString("name"));
					}
				}
				// 添加到省列表
				regionData.add(new RegionData(obj.getString("name"), key, children));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getRegionName(String key) {
		if(null == regionData) {
			initRegionData(this);
		}
		// 省份
		for(int p=0, size=regionData.size(); p<size; p++) {
			RegionData province = regionData.get(p);
			if(province.dictKey.equals(key)) {
				return province.name;
			}
			// 城市
			if(null != province.children && province.children.containsKey(key)) {
				return province.children.get(key);
			}
		}
		return "";
	}
	
	private String transferIs2String(InputStream is) {
		String str = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			str = new String(outStream.toByteArray(), HTTP.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
