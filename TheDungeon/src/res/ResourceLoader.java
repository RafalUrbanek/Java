package res;

import java.io.InputStream;

public class ResourceLoader {
	
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/" + path);
		} if (input == null) {
			System.out.println("no input under path: " + path);
		}
		return input;
	}
}
