package WordPressXML;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class DatabaseXML {
	
	public static Document loadXml(String url) {
		try{
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(url);
	        return document;
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return null;
	}

}
