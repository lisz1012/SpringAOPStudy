import java.util.*;

public class LanguageTagMap {
	private Map<String, Locale> map1 = new HashMap<>();
	private Map<String, String> map2 = new HashMap<>();
	private Locale locale;
	private String marketPlaceId;

	public <T> T get(String key, Class<T> clazz) {
		if (clazz == Locale.class){
			return (T)map1.get(key);
		} else if (clazz == String.class) {
			return (T)map2.get(key);
		}
		return null;
	}

	public <T> void put(String key, Object value, Class<T> clazz) {
		if (clazz == Locale.class){
			map1.put(key, (Locale) value);
		} else if (clazz == String.class) {
			map2.put(key, (String) value);
		}
	}

//	public Locale get(String key) {
//		if (key.equals("languageTag1")){
//			return Locale.forLanguageTag("en-US"); //(T)map1.get(key);
//		} else if (key.equals("languageTag2")) {
//			return "En_US";
//		}
//		return null;
//	}
//
//	public String get(String key) {
//		return null;
//	}

}
