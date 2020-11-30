import java.util.Locale;

public class T1 {
	public static void main(String[] args) {
		LanguageTagMap map = new LanguageTagMap();
		map.put("languageTag1", "marketplaceId1", String.class);
		map.put("languageTag2", Locale.forLanguageTag("en-US"), Locale.class);

		String s = map.get("languageTag1", String.class);
		Locale locale = map.get("languageTag2", Locale.class);

		//错误的类型
		String s2 = map.get("languageTag2", String.class);

		System.out.println(s);
		System.out.println(locale);

		System.out.println(s2);
	}
}
